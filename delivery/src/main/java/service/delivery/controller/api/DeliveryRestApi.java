package service.delivery.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.delivery.dto.DeliveryResponse;

@Api
@RequestMapping("deliveries")
public interface DeliveryRestApi {

    @PutMapping("/{deliveryId}/pickUp")
    @ApiOperation(value = "Pick Up Delivery")
    ResponseEntity<DeliveryResponse> pickUp(@PathVariable Long deliveryId);

    @GetMapping("/getByOrderId/{orderId}")
    @ApiOperation(value = "Get delivery by order id")
    ResponseEntity<DeliveryResponse> getByOrderId(@PathVariable Long orderId);

    @PutMapping("/{deliveryId}/deliver")
    @ApiOperation(value = "Finish delivery")
    ResponseEntity<DeliveryResponse> deliver(@PathVariable Long deliveryId);
}
