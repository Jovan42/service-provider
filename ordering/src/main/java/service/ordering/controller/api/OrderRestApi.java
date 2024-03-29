package service.ordering.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.ordering.domain.enums.OrderStatus;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.ordering.dto.StartOrderPreparationRequest;

import java.util.List;

@Api
@RequestMapping("orders")
public interface OrderRestApi {

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Get order by id")
    ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId);

    @GetMapping("/status")
    @ApiOperation(value = "Get all pending orders")
    ResponseEntity<List<OrderResponse>> getAllByStatus(@RequestParam OrderStatus status);

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

    @PutMapping("/{orderId}/finishPreparation")
    @ApiOperation(value = "Finish Order Preparation")
    ResponseEntity<OrderResponse> finishPreparation(@PathVariable Long orderId);

    @PutMapping("/{orderId}/abort")
    @ApiOperation(value = "Finish Order Preparation")
    ResponseEntity<OrderResponse> abort(@PathVariable Long orderId);

}
