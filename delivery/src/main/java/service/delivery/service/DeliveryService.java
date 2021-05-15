package service.delivery.service;

import org.springframework.stereotype.Service;

@Service
public interface DeliveryService {
    void createDelivery(Long orderId, Long preparationTimeInMinutes, Long serviceProviderId);
}
