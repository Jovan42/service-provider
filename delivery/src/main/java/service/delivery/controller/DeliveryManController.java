package service.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.delivery.controller.api.DeliveryManRestApi;
import service.delivery.dto.DeliveryManRequest;
import service.delivery.dto.DeliveryManResponse;
import service.delivery.service.DeliveryManService;

import java.util.List;

@Controller
public class DeliveryManController implements DeliveryManRestApi {

    private final DeliveryManService deliveryManService;

    public DeliveryManController(DeliveryManService deliveryManService) {
        this.deliveryManService = deliveryManService;
    }

    @Override
    public ResponseEntity<DeliveryManResponse> createDeliveryMan(
            DeliveryManRequest deliveryManRequest) {
        return new ResponseEntity<>(
                deliveryManService.create(deliveryManRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<DeliveryManResponse>> getAll() {
        return new ResponseEntity<>(
                deliveryManService.getAll(), HttpStatus.OK);
    }
}
