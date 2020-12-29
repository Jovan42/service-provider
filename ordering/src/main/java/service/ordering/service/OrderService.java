package service.ordering.service;

import org.springframework.stereotype.Service;
import service.ordering.domain.Order;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;

@Service
public interface OrderService {
    OrderResponse create(OrderRequest orderRequest);
    Order invalidateRequest(Long orderId);
}
