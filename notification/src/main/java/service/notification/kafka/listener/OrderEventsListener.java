package service.notification.kafka.listener;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.notification.dto.Message;
import service.notification.service.NotificationMessageService;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;

@Component
@KafkaListener(topics = "orderTopic", groupId = "notification-service")
public class OrderEventsListener {

    private final NotificationMessageService notificationMessageService;

    public OrderEventsListener(NotificationMessageService notificationMessageService) {
        this.notificationMessageService = notificationMessageService;
    }

    @KafkaHandler()
    public void listenOrderDeliveredEvent(
            @Payload OrderRequestDeclinedEvent orderRequestDeclinedEvent) {
        notificationMessageService.sendNotification(Message.of(orderRequestDeclinedEvent));
    }

    @KafkaHandler()
    public void listenOrderDeliveredEvent(@Payload OrderCreatedEvent orderCreatedEvent) {
        Message message = Message.of(orderCreatedEvent);
        notificationMessageService.sendNotification(message);
        notificationMessageService.sendMail(message);
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload BaseEvent baseEvent) {
        System.out.println("Received object: " + baseEvent);
    }
}
