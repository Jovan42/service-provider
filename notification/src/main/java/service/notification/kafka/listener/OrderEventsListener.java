package service.notification.kafka.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.notification.dto.MessageBody;
import service.notification.service.NotificationMessageService;
import service.sharedlib.events.*;

import java.util.List;

@Component
@KafkaListener(topics = "orderTopic", groupId = "notification-service")
public class OrderEventsListener {

    private final NotificationMessageService notificationMessageService;

    @Value(value = "${websockets.deliveryManDestination}")
    private String deliveryManDestination;

    @Value(value = "${websockets.userDestination}")
    private String user;

    @Value(value = "${websockets.serviceProviderDestination}")
    private String serviceProviderDestination;

    public OrderEventsListener(NotificationMessageService notificationMessageService) {
        this.notificationMessageService = notificationMessageService;
    }

    @KafkaHandler()
    public void listenOrderDeliveredEvent(
            @Payload OrderRequestDeclinedEvent orderRequestDeclinedEvent) {
        notificationMessageService.sendNotification(
                "/topic/order", MessageBody.of(orderRequestDeclinedEvent));
    }

    @KafkaHandler()
    public void listenPendingManualApprovalEvent(
            @Payload PendingManualApprovalEvent pendingManualApprovalEvent) {
        notificationMessageService.sendNotification(
                deliveryManDestination, MessageBody.of(pendingManualApprovalEvent));
    }

    @KafkaHandler()
    public void listenOrderAssignedToDeliveryManEvent(
            @Payload OrderAssignedToDeliveryManEvent orderAssignedToDeliveryManEvent) {
        notificationMessageService.sendNotification(
                deliveryManDestination, MessageBody.of(orderAssignedToDeliveryManEvent));
    }

    @KafkaHandler()
    public void listenOrderInPreparationEvent(
            @Payload OrderInPreparationEvent orderRequestDeclinedEvent) {
        notificationMessageService.sendNotification(
                user, MessageBody.of(orderRequestDeclinedEvent));

    }

    @KafkaHandler()
    public void listenOrderDeliveredEvent(@Payload OrderCreatedEvent orderCreatedEvent) {
        MessageBody messageBody = MessageBody.of(orderCreatedEvent);
        notificationMessageService.sendNotifications(
                List.of(deliveryManDestination, user, serviceProviderDestination), messageBody);
        notificationMessageService.sendMail(messageBody);
        notificationMessageService.sendSMS(messageBody);
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload BaseEvent baseEvent) {
        System.out.println("Received object: " + baseEvent);
    }
}
