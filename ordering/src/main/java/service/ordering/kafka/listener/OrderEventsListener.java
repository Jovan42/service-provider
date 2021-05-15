package service.ordering.kafka.listener;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.ordering.service.OrderService;
import service.sharedlib.events.AccountValidationFinishedEvent;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderItemsApprovedEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;

@Component
@KafkaListener(topics = "orderTopic", groupId = "ordering-service")
public class OrderEventsListener {
    private final OrderService orderService;

    public OrderEventsListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaHandler()
    public void listenOrderCreatedEvent(@Payload OrderRequestDeclinedEvent orderRequestDeclinedEvent) {
        orderService.invalidateRequest(orderRequestDeclinedEvent.getOrderId(), orderRequestDeclinedEvent.getReason());
    }

    @KafkaHandler()
    public void listenOrderCreatedEvent(@Payload OrderItemsApprovedEvent orderItemsApprovedEvent) {
        orderService.orderItemsApproved(orderItemsApprovedEvent.getOrderId(),
                orderItemsApprovedEvent.getManualApprovalRequired(),
                orderItemsApprovedEvent.getOrderItems());
    }

    @KafkaHandler()
    public void listenOrderCreatedEvent(@Payload AccountValidationFinishedEvent accountValidationFinishedEvent) {
        orderService.accountApproved(accountValidationFinishedEvent.getOrderId());
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload BaseEvent baseEvent) {
        System.out.println("Received object: " + baseEvent);
    }

}
