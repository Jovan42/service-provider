package service.ordering.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import service.ordering.domain.BoughtItem;
import service.ordering.domain.Order;
import service.ordering.domain.enums.OrderStatus;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.ordering.repostiroy.OrderRepository;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.ServiceProviderValidationFinishedEvent;
import service.sharedlib.events.pojo.OrderItem;
import service.sharedlib.exceptions.BadRequestException;
import service.sharedlib.exceptions.NotFoundException;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public OrderServiceImpl(
            ModelMapper modelMapper,
            OrderRepository orderRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.getBoughtItems().forEach(boughtItem -> boughtItem.setOrder(order));
        order.setCreatedTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING_SERVICE_PROVIDER_VALIDATION);
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent =
                OrderCreatedEvent.builder()
                        .orderId(order.getId())
                        .serviceProviderId(order.getServiceProviderId())
                        .orderItems(
                                order.getBoughtItems().stream()
                                        .map(
                                                boughtItem ->
                                                        modelMapper.map(
                                                                boughtItem, OrderItem.class))
                                        .collect(Collectors.toList()))
                        .build();
        kafkaTemplate.send("orderTopic", "", orderCreatedEvent);
        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    @Override
    public void invalidateRequest(Long orderId, OrderInvalidReason reason) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        order.setStatus(OrderStatus.CANCELED);
        order.setCancelReason(reason.toString());
        order.setLastModified(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public void orderItemsApproved(
            Long orderId, Boolean manualApprovalRequired, Map<Long, OrderItem> orderItems) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        order.setStatus(
                manualApprovalRequired
                        ? OrderStatus.PENDING_MANUAL_SERVICE_PROVIDER_VALIDATION
                        : OrderStatus.PENDING_ACCOUNT_VALIDATION);
        order.setLastModified(LocalDateTime.now());
        order.getBoughtItems()
                .forEach(
                        item ->
                                item.setCurrentPricePerUnit(
                                        orderItems
                                                .get(item.getMenuItemId())
                                                .getCurrentPricePerUnit()));
        order = orderRepository.save(order);

        if (!manualApprovalRequired) {
            kafkaTemplate.send(
                    "orderTopic",
                    "",
                    ServiceProviderValidationFinishedEvent.builder()
                            .orderId(orderId)
                            .userId(order.getUserId())
                            .accountId(order.getAccountId())
                            .price(calculatePrice(order.getBoughtItems()))
                            .build());
        }
    }



    @Override
    public OrderResponse manuallyApprove(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (order.getStatus() == OrderStatus.PENDING_MANUAL_SERVICE_PROVIDER_VALIDATION) {
            order.setStatus(OrderStatus.PENDING_ACCOUNT_VALIDATION);
            order.setLastModified(LocalDateTime.now());
            Order savedOrder = orderRepository.save(order);
            kafkaTemplate.send(
                    "orderTopic",
                    "",
                    ServiceProviderValidationFinishedEvent.builder()
                            .orderId(orderId)
                            .userId(order.getUserId())
                            .accountId(order.getAccountId())
                            .price(calculatePrice(order.getBoughtItems()))
                            .build());
            return modelMapper.map(savedOrder, OrderResponse.class);
        } else {
            throw new BadRequestException(
                    String.format(
                            "Order [%d] is not in correct status and can not be manually approved",
                            orderId));
        }
    }

    private Double calculatePrice(List<BoughtItem> boughtItems) {
        return boughtItems.stream()
                .mapToDouble(
                        boughtItem -> boughtItem.getCurrentPricePerUnit() * boughtItem.getAmount())
                .sum();
    }
}
