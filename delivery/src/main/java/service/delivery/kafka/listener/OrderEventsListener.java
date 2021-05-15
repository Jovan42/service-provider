package service.delivery.kafka.listener;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.sharedlib.events.BaseEvent;

@Component
@KafkaListener(topics = "orderTopic", groupId = "ordering-service")
public class OrderEventsListener {

    @KafkaHandler(isDefault = true)
    public void listenObject(@Payload BaseEvent baseEvent) {
        System.out.println("Received object: " + baseEvent);
    }
}
