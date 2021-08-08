package service.ordering.repostiroy;

import org.springframework.data.jpa.repository.JpaRepository;
import service.ordering.domain.Order;
import service.ordering.domain.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByStatus(OrderStatus status);
}
