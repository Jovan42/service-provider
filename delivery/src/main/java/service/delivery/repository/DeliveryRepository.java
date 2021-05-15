package service.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.delivery.domain.Delivery;
import service.delivery.domain.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByStatusAndDeliveryManIsNull(DeliveryStatus status);
}
