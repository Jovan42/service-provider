package service.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.delivery.domain.DeliveryMan;
import service.delivery.domain.enums.DeliveryManStatus;

import java.util.List;

public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {
    @Query(
            "SELECT dm from DeliveryMan dm left join dm.deliveries deliveries where dm.status = :status and" +
                    " dm.serviceProviderId = :serviceProviderId and (not deliveries.status = 1 or deliveries.size = 0) ")
    List<DeliveryMan> findAvailableForDelivery(Long serviceProviderId, DeliveryManStatus status);
}
