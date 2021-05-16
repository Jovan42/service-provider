package service.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.delivery.domain.Delivery;
import service.delivery.domain.enums.DeliveryStatus;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByStatusAndDeliveryManIsNull(DeliveryStatus status);
    Optional<Delivery> findByOrderId(Long orderId);

}
