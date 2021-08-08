package service.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.delivery.controller.api.DeliveryRestApi;
import service.delivery.domain.enums.DeliveryStatus;
import service.delivery.dto.DeliveryResponse;
import service.delivery.service.DeliveryService;

import java.util.List;

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

    @Override
    public ResponseEntity<DeliveryResponse> deliver(Long deliveryId) {
        return new ResponseEntity<>(deliveryService.deliver(deliveryId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DeliveryResponse>> getAllByStatus(DeliveryStatus status) {
        return new ResponseEntity<>(deliveryService.getAllByStatus(status), HttpStatus.OK);

    }
}
