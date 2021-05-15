package service.accounting.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import service.accounting.domain.Account;
import service.accounting.domain.enums.AccountStatus;
import service.accounting.repository.AccountRepository;
import service.sharedlib.events.AccountValidationFinishedEvent;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.exceptions.NotFoundException;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public AccountServiceImpl(AccountRepository accountRepository, KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.accountRepository = accountRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void validateOrder(Long userId, Long accountId, Double price, Long orderId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if(accountOptional.isEmpty() || !accountOptional.get().getUser().getId().equals(userId)) {
            sentInvalidateEvent(orderId, OrderInvalidReason.USER_AND_ACCOUNT_DOES_NOT_MATCH);
            return;
        }

        if(accountOptional.get().getStatus() != AccountStatus.ACTIVE) {
            sentInvalidateEvent(orderId, OrderInvalidReason.ACCOUNT_IN_INCORRECT_STATUS);
            return;
        }

        if(accountOptional.get().getBalance() < price) {
            sentInvalidateEvent(orderId, OrderInvalidReason.INSUFFICIENT_FUNDS);
            return;
        }

        approveOrder(userId, accountOptional.get(), price, orderId);
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
