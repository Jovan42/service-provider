package service.sharedlib.events;

import lombok.*;
import service.sharedlib.events.pojo.OrderItem;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemsApprovedEvent extends BaseEvent{
    private Long orderId;
    private Boolean manualApprovalRequired;
    private Map<Long, OrderItem> orderItems;
}
