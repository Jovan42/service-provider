package service.delivery.kafka.listener;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.delivery.service.DeliveryService;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderInPreparationEvent;
import service.sharedlib.events.OrderPreparationFinishedEvent;

@Component
@KafkaListener(topics = "orderTopic", groupId = "delivery-service")
public class OrderEventsListener {

    private final DeliveryService deliveryService;

    public OrderEventsListener(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload BaseEvent baseEvent) {
        System.out.println("Received object: " + baseEvent);
    }

    @KafkaHandler()
    public void listenOrderInPreparationEvent(
            @Payload OrderInPreparationEvent orderInPreparationEvent) {
        deliveryService.createDelivery(
                orderInPreparationEvent.getOrderId(),
                orderInPreparationEvent.getPreparationTimeInMinutes(),
                orderInPreparationEvent.getServiceProviderId());
    }

    @KafkaHandler()
    public void listenOrderPreparationFinished(
            @Payload OrderPreparationFinishedEvent orderInPreparationEvent) {
        deliveryService.readyToPickUp(orderInPreparationEvent.getOrderId());
    }
}
