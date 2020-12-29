package service.sharedlib.events;

import lombok.*;
import service.sharedlib.events.pojo.OrderCreatedItem;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent extends BaseEvent {
    private Long orderId;
    private Long serviceProviderId;
    private List<OrderCreatedItem> orderCreatedItems;
}
