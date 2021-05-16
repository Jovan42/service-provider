package service.delivery.service;

import org.springframework.stereotype.Service;
import service.delivery.dto.DeliveryManRequest;
import service.delivery.dto.DeliveryManResponse;

@Service
public interface DeliveryManService {
    DeliveryManResponse create(DeliveryManRequest deliveryManRequest);
}
