package service.sharedlib.events;

import lombok.*;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDeclinedEvent extends BaseEvent {
    private Long orderId;
    private OrderInvalidReason reason;
}
