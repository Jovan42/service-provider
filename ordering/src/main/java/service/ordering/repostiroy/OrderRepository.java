package service.ordering.repostiroy;

import org.springframework.data.jpa.repository.JpaRepository;
import service.ordering.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
