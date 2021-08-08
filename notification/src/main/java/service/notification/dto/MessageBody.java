package service.notification.dto;

import lombok.*;
import service.sharedlib.events.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBody {

    private String message;
    private String eventName;
    private Long orderId;
    private String additionalInfo;

    public static MessageBody of(OrderCreatedEvent orderCreatedEvent) {
        return MessageBody.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .eventName(orderCreatedEvent.getClass().getSimpleName().replace("Event", ""))
                .message("Your order is received please wait")
                .build();
    }

    public static MessageBody of(OrderRequestDeclinedEvent orderRequestDeclinedEvent) {
        return MessageBody.builder()
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

    public static MessageBody of(PendingManualApprovalEvent orderRequestDeclinedEvent) {
        return MessageBody.builder()
                .orderId(orderRequestDeclinedEvent.getOrderId())
                .eventName(
                        orderRequestDeclinedEvent.getClass().getSimpleName().replace("Event", ""))
                .message("New order requires manual approval")
                .build();
    }

    public static MessageBody of(OrderInPreparationEvent orderRequestDeclinedEvent) {
        return MessageBody.builder()
                .orderId(orderRequestDeclinedEvent.getOrderId())
                .eventName(
                        orderRequestDeclinedEvent.getClass().getSimpleName().replace("Event", ""))
                .message("Your order in successfully  placed, our order is in preparation")
                .build();
    }

    public static MessageBody of(OrderAssignedToDeliveryManEvent orderAssignedToDeliveryManEvent) {
        return MessageBody.builder()
                .orderId(orderAssignedToDeliveryManEvent.getOrderId())
                .eventName(
                        orderAssignedToDeliveryManEvent
                                .getClass()
                                .getSimpleName()
                                .replace("Event", ""))
                .message(
                        "New order is assigned to you, preparation started and will be finished in approximately in"
                                + timeConvert(
                                        orderAssignedToDeliveryManEvent
                                                .getPreparationTimeInMinutes()))
                .build();
    }

    private static String timeConvert(Long time) {
        return time / 24 / 60 + ":" + time / 60 % 24 + ':' + time % 60;
    }
}
