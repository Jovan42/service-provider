package service.ordering.service;

import org.springframework.stereotype.Service;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.sharedlib.events.pojo.OrderItem;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public interface OrderService {
    OrderResponse create(OrderRequest orderRequest);

    void invalidateRequest(Long orderId, OrderInvalidReason reason);

    void orderItemsApproved(
            Long orderId, Boolean manualApprovalRequired, Map<Long, OrderItem> orderItems);

    OrderResponse manuallyApprove(Long orderId);

    void accountApproved(Long orderId);

    OrderResponse startPreparation(Long orderId, Long preparationTimeInMinutes);

    OrderResponse finishPreparation(Long orderId);

    void inDelivery(Long orderId);

    void delivered(Long orderId, LocalDateTime deliveredTime);
}
