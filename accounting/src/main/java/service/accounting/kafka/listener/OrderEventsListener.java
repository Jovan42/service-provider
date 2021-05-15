package service.accounting.kafka.listener;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.accounting.service.AccountService;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.ServiceProviderValidationFinishedEvent;

@Component
@KafkaListener(topics = "orderTopic", groupId = "accounting-service")
public class OrderEventsListener {

    private final AccountService accountService;

    public OrderEventsListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @KafkaHandler()
    public void listenOrderCreatedEvent(
            @Payload
                    ServiceProviderValidationFinishedEvent serviceProviderValidationFinishedEvent) {
        System.out.println("Received object: " + serviceProviderValidationFinishedEvent);
        accountService.validateOrder(
                serviceProviderValidationFinishedEvent.getUserId(),
                serviceProviderValidationFinishedEvent.getAccountId(),
                serviceProviderValidationFinishedEvent.getPrice(),
                serviceProviderValidationFinishedEvent.getOrderId());
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload BaseEvent baseEvent) {
        System.out.println("Received object: " + baseEvent);
    }
}
