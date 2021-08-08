package service.accounting.service;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import service.accounting.domain.Account;
import service.accounting.domain.enums.AccountStatus;
import service.accounting.dto.AccountRequest;
import service.accounting.dto.AccountResponse;
import service.accounting.repository.AccountRepository;
import service.sharedlib.events.AccountValidationFinishedEvent;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate,
            ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public void validateOrder(String userId, Long accountId, Double price, Long orderId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.get().getStatus() != AccountStatus.ACTIVE) {
            sentInvalidateEvent(orderId, OrderInvalidReason.ACCOUNT_IN_INCORRECT_STATUS);
            return;
        }

        if (accountOptional.get().getBalance() < price) {
            sentInvalidateEvent(orderId, OrderInvalidReason.INSUFFICIENT_FUNDS);
            return;
        }

        approveOrder(userId, accountOptional.get(), price, orderId);
    }

    @Override
    public AccountResponse addAccount(String userId, AccountRequest accountRequest) {
        Account account = modelMapper.map(accountRequest, Account.class);
        account.setUserId(userId);
        return modelMapper.map(accountRepository.save(account), AccountResponse.class);
    }

    @Override
    public List<AccountResponse> getAccountsForCurrentUser() {
        KeycloakAuthenticationToken keycloakAuthenticationToken =
                (KeycloakAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();
        return accountRepository.findAllByUserId(keycloakAuthenticationToken.getName()).stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    private void approveOrder(String userId, Account account, Double price, Long orderId) {
        account.setBalance(account.getBalance() - price);
        accountRepository.save(account);

        kafkaTemplate.send(
                "orderTopic",
                "",
                AccountValidationFinishedEvent.builder()
                        .orderId(orderId)
                        .price(price)
                        .accountId(account.getId())
                        .userId(userId)
                        .build());
    }

    private void sentInvalidateEvent(Long orderId, OrderInvalidReason orderInvalidReason) {
        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderRequestDeclinedEvent.builder()
                        .orderId(orderId)
                        .reason(orderInvalidReason)
                        .build());
    }
}
