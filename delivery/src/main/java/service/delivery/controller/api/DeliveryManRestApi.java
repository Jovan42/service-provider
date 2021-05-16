package service.delivery.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.delivery.dto.DeliveryManRequest;
import service.delivery.dto.DeliveryManResponse;

@Api
@RequestMapping("deliveryPeople")
public interface DeliveryManRestApi {

    @PostMapping("")
    @ApiOperation(value = "Create Delivery Man")
    ResponseEntity<DeliveryManResponse> createDeliveryMan(
            @RequestBody DeliveryManRequest deliveryManRequest);
}
