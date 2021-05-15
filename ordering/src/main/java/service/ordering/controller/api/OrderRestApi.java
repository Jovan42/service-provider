package service.ordering.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.ordering.dto.StartOrderPreparationRequest;

@Api
@RequestMapping("orders")
public interface OrderRestApi {

    @PostMapping("")
    @ApiOperation(value = "Create new Order")
    ResponseEntity<OrderResponse> create(@Validated @RequestBody OrderRequest orderRequest);

    @PutMapping("/{orderId}/manuallyApprove")
    @ApiOperation(value = "Manually Approve Order")
    ResponseEntity<OrderResponse> manuallyApprove(@PathVariable Long orderId);

    @PutMapping("/{orderId}/startPreparation")
    @ApiOperation(value = "Start Order Preparation")
    ResponseEntity<OrderResponse> startPreparation(
            @PathVariable Long orderId,
            @RequestBody StartOrderPreparationRequest startOrderPreparationRequest);
}
