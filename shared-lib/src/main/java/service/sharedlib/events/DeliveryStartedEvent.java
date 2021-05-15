package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStartedEvent extends BaseEvent {
    private Long orderId;

}
