package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPreparationFinishedEvent extends BaseEvent{
    private Long orderId;
}
