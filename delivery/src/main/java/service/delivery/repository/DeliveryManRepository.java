package service.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.delivery.domain.DeliveryMan;

import java.util.List;

public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {
    @Query(
            "SELECT dm from DeliveryMan dm left join dm.deliveries deliveries where dm.status = 'ACTIVE' and" +
                    " dm.serviceProviderId = :serviceProviderId and ( (deliveries.status <> 'PENDING_PREPARATION' AND " +
                    "deliveries.status <> 'READY_TO_PICK_UP' AND deliveries.status <> 'ON_THE_WAY' ) or deliveries.size = 0) ")
    List<DeliveryMan> findAvailableForDelivery(Long serviceProviderId);
}
