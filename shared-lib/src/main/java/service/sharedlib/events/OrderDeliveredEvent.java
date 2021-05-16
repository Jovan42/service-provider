package service.sharedlib.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeliveredEvent extends BaseEvent {
    private Long orderId;
    private LocalDateTime deliveredTime;
}
