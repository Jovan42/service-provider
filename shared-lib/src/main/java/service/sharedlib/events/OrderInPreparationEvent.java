package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInPreparationEvent extends BaseEvent {
    private Long orderId;
    private Long preparationTimeInMinutes;

}
