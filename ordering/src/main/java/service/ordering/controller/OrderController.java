package service.ordering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.ordering.controller.api.OrderRestApi;
import service.ordering.domain.enums.OrderStatus;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.ordering.dto.StartOrderPreparationRequest;
import service.ordering.service.OrderService;
import service.ordering.service.OrderServiceImpl;

import java.util.List;

@Controller
public class OrderController implements OrderRestApi {
    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderById(Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getAllByStatus( OrderStatus status) {
        return new ResponseEntity<>(orderService.getAllByStatus(status), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> create(OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.create(orderRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderResponse> manuallyApprove(Long orderId) {
        return new ResponseEntity<>(orderService.manuallyApprove(orderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> startPreparation(
            Long orderId, StartOrderPreparationRequest startOrderPreparationRequest) {
        return new ResponseEntity<>(
                orderService.startPreparation(
                        orderId, startOrderPreparationRequest.getPreparationTimeInMinutes()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> finishPreparation(Long orderId) {
        return new ResponseEntity<>(orderService.finishPreparation(orderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> abort(Long orderId) {
        return new ResponseEntity<>(orderService.abort(orderId), HttpStatus.OK);
    }


}
