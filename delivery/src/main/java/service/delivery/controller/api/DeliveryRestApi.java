package service.delivery.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.delivery.domain.enums.DeliveryStatus;
import service.delivery.dto.DeliveryResponse;

import java.util.List;

@Api
@RequestMapping("deliveries")
public interface DeliveryRestApi {

    @PutMapping("/{deliveryId}/pickUp")
    @ApiOperation(value = "Pick Up Delivery")
    ResponseEntity<DeliveryResponse> pickUp(@PathVariable Long deliveryId);

    @GetMapping("/getByOrderId/{orderId}")
    @ApiOperation(value = "Get delivery by order id")
    ResponseEntity<DeliveryResponse> getByOrderId(@PathVariable Long orderId);

    @PutMapping("/{deliveryId}/delivery")
    @ApiOperation(value = "Finish delivery")
    ResponseEntity<DeliveryResponse> deliver(@PathVariable Long deliveryId);

    @GetMapping("")
    @ApiOperation(value = "Get delivery by status")
    ResponseEntity<List<DeliveryResponse>> getAllByStatus(@RequestParam DeliveryStatus status);
}
