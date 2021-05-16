package service.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import service.accounting.domain.Account;
import service.accounting.domain.enums.AccountStatus;
import service.accounting.dto.AccountRequest;
import service.accounting.dto.AccountResponse;
import service.accounting.repository.AccountRepository;
import service.accounting.repository.UserRepository;
import service.sharedlib.events.AccountValidationFinishedEvent;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.exceptions.NotFoundException;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            UserRepository userRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate,
            ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public void validateOrder(Long userId, Long accountId, Double price, Long orderId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty() || !accountOptional.get().getUser().getId().equals(userId)) {
            sentInvalidateEvent(orderId, OrderInvalidReason.USER_AND_ACCOUNT_DOES_NOT_MATCH);
            return;
        }

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
    public AccountResponse addAccount(Long userId, AccountRequest accountRequest) {
        Account account = modelMapper.map(accountRequest, Account.class);
        account.setUser(
                userRepository.findById(userId).orElseThrow(NotFoundException::new));
        return modelMapper.map(accountRepository.save(account), AccountResponse.class);
    }

    private void approveOrder(Long userId, Account account, Double price, Long orderId) {
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
