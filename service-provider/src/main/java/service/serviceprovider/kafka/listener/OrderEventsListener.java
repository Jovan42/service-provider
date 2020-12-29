package service.serviceprovider.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.serviceprovider.services.ServiceProviderService;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;

@Component
@KafkaListener(topics = "orderTopic", groupId = "service-provider-service")
public class OrderEventsListener {
    private final ServiceProviderService serviceProviderService;

    @Autowired
    public OrderEventsListener(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @KafkaHandler()
    public void listenOrderCreatedEvent(@Payload OrderCreatedEvent orderCreatedEvent) {
        serviceProviderService.validateOrder(orderCreatedEvent);
    }

    @KafkaHandler()
    public void listenOrderCreatedEvent(
            @Payload OrderRequestDeclinedEvent orderRequestDeclinedEvent) {
        System.out.println("Received object: " + orderRequestDeclinedEvent);
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload Object object) {
        System.out.println("Received object: " + object);
    }
}
