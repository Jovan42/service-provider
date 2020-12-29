package service.serviceprovider.exception.handler;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.exceptions.InvalidOrderException;

@EnableWebMvc
@ControllerAdvice
public class EventsExceptionHandler {
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public EventsExceptionHandler(KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @ExceptionHandler(InvalidOrderException.class)
    public void invalidOrderExceptionHandler(InvalidOrderException exception) {

        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderRequestDeclinedEvent.builder()
                        .orderId(exception.getOrderId())
                        .reason(exception.getReason())
                        .build());
    }
}
