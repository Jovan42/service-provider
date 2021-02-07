package service.ordering.service;

import org.springframework.stereotype.Service;
import service.ordering.domain.Order;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.sharedlib.events.pojo.OrderItem;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import java.util.Map;

@Service
public interface OrderService {
    OrderResponse create(OrderRequest orderRequest);
    Order invalidateRequest(Long orderId, OrderInvalidReason reason);
    Order orderItemsApproved(Long orderId, Boolean manualApprovalRequired, Map<Long, OrderItem> orderItems);
}
