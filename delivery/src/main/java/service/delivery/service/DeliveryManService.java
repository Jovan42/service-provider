package service.delivery.service;

import org.springframework.stereotype.Service;
import service.delivery.dto.DeliveryManRequest;
import service.delivery.dto.DeliveryManResponse;

import java.util.List;

@Service
public interface DeliveryManService {
    DeliveryManResponse create(DeliveryManRequest deliveryManRequest);

    List<DeliveryManResponse> getAll();
}
