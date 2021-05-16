package service.delivery.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.delivery.dto.DeliveryResponse;

@Api
@RequestMapping("orders")
public interface DeliveryRestApi {

    @PutMapping("/{deliveryId}/pickUp")
    @ApiOperation(value = "Pick Up Delivery")
    ResponseEntity<DeliveryResponse> pickUp(@PathVariable Long deliveryId);

}
