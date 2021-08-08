package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAssignedToDeliveryManEvent extends BaseEvent {
    private Long orderId;
    private Long deliveryManId;
    private Long preparationTimeInMinutes;

}
