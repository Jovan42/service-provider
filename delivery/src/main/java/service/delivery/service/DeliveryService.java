package service.delivery.service;

import org.springframework.stereotype.Service;
import service.delivery.domain.enums.DeliveryStatus;
import service.delivery.dto.DeliveryResponse;

import java.util.List;

@Service
public interface DeliveryService {
    void createDelivery(Long orderId, Long preparationTimeInMinutes, Long serviceProviderId);

    void readyToPickUp(Long orderId);

    DeliveryResponse pickUp(Long deliveryId);

    DeliveryResponse getByOrderId(Long orderId);

    DeliveryResponse deliver(Long deliveryId);

    List<DeliveryResponse> getAllByStatus(DeliveryStatus status);
}
