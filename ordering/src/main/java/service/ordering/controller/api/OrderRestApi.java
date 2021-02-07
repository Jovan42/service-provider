package service.ordering.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;

@Api
@RequestMapping("orders")
public interface OrderRestApi {

    @PostMapping("")
    @ApiOperation(value = "Create new Order")
    ResponseEntity<OrderResponse> create(@Validated @RequestBody OrderRequest orderRequest);

    @PutMapping("/{orderId}/manuallyApprove")
    @ApiOperation(value = "Manually Approve Order")
    ResponseEntity<OrderResponse> manuallyApprove(@PathVariable Long orderId);
}
