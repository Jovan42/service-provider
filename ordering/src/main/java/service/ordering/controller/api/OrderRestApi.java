package service.ordering.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;

@Api
@RequestMapping("orders")
public interface OrderRestApi {

    @PostMapping("")
    @ApiOperation(value = "Create new Order")
    ResponseEntity<OrderResponse> create(@Validated @RequestBody OrderRequest orderRequest);
}
