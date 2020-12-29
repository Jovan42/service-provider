package service.ordering.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import service.ordering.domain.Order;
import service.ordering.domain.enums.OrderStatus;
import service.ordering.dto.OrderRequest;
import service.ordering.dto.OrderResponse;
import service.ordering.repostiroy.OrderRepository;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.pojo.OrderCreatedItem;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
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
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent =
                OrderCreatedEvent.builder()
                        .orderId(order.getId())
                        .serviceProviderId(order.getServiceProviderId())
                        .orderCreatedItems(
                                order.getBoughtItems().stream()
                                        .map(
                                                boughtItem ->
                                                        modelMapper.map(
                                                                boughtItem, OrderCreatedItem.class))
                                        .collect(Collectors.toList()))
                        .build();
        kafkaTemplate.send("orderTopic", "", orderCreatedEvent);
        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    @Override
    public Order invalidateRequest(Long orderId) {
         Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
         order.setStatus(OrderStatus.CANCELED);
         order.setLastModified(LocalDateTime.now());
         return orderRepository.save(order);
    }

}
