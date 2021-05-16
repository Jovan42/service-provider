package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPickedUpEvent extends BaseEvent{
    private Long orderId;
}
