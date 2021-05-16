package service.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.delivery.controller.api.DeliveryRestApi;
import service.delivery.dto.DeliveryResponse;
import service.delivery.service.DeliveryService;

@Controller
public class DeliveryController implements DeliveryRestApi {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public ResponseEntity<DeliveryResponse> pickUp(Long deliveryId) {
        return new ResponseEntity<>(deliveryService.pickUp(deliveryId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeliveryResponse> getByOrderId(Long orderId) {
        return new ResponseEntity<>(deliveryService.getByOrderId(orderId), HttpStatus.OK);
    }
}
