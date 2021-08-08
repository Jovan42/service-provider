package service.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.delivery.domain.Delivery;
import service.delivery.domain.DeliveryMan;
import service.delivery.domain.enums.DeliveryStatus;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByStatusOrStatusAndDeliveryManIsNull(DeliveryStatus status, DeliveryStatus status2);

    List<Delivery> findAllByStatus(DeliveryStatus status);


    Optional<Delivery> findByOrderId(Long orderId);
}
