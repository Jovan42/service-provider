package service.notification.dto;

import lombok.*;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String message;
    private String eventName;
    private Long orderId;
    private String additionalInfo;

    public static Message of(OrderCreatedEvent orderCreatedEvent) {
        return Message.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .eventName(orderCreatedEvent.getClass().getSimpleName().replace("Event", ""))
                .message("Your order is received please wait")
                .build();
    }

    public static Message of(OrderRequestDeclinedEvent orderRequestDeclinedEvent) {
        return Message.builder()
                .orderId(orderRequestDeclinedEvent.getOrderId())
                .eventName(
                        orderRequestDeclinedEvent.getClass().getSimpleName().replace("Event", ""))
                .message(
                        orderRequestDeclinedEvent
                                .getReason()
                                .toString()
                                .toLowerCase()
                                .replace("_", ""))
                .build();
    }
}
