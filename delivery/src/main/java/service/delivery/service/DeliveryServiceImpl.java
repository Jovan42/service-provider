package service.delivery.service;

import org.modelmapper.TypeMap;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import service.delivery.domain.Delivery;
import service.delivery.domain.DeliveryMan;
import service.delivery.domain.enums.DeliveryStatus;
import service.delivery.dto.DeliveryResponse;
import service.delivery.repository.DeliveryManRepository;
import service.delivery.repository.DeliveryRepository;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderAssignedToDeliveryManEvent;
import service.sharedlib.events.OrderDeliveredEvent;
import service.sharedlib.events.OrderPickedUpEvent;
import service.sharedlib.exceptions.BadRequestException;
import service.sharedlib.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    private final TypeMap<Delivery, DeliveryResponse> deliveryMapper;

    public DeliveryServiceImpl(
            DeliveryRepository deliveryRepository,
            DeliveryManRepository deliveryManRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate,
            TypeMap<Delivery, DeliveryResponse> deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public void createDelivery(
            Long orderId, Long preparationTimeInMinutes, Long serviceProviderId) {
        Delivery delivery = new Delivery();
        findAvailableDeliveryMan(serviceProviderId)
                .ifPresentOrElse(
                        deliveryMan -> {
                            delivery.setDeliveryMan(deliveryMan);
                            delivery.setStatus(DeliveryStatus.PENDING_PREPARATION);
                            kafkaTemplate.send(
                                    "orderTopic",
                                    "",
                                    OrderAssignedToDeliveryManEvent.builder()
                                            .orderId(delivery.getOrderId())
                                            .preparationTimeInMinutes(delivery.getPreparationTimeInMinutes())
                                            .deliveryManId(deliveryMan.getId())
                                    .build());
                        },
                        () -> delivery.setStatus(DeliveryStatus.PENDING_DELIVERY_MAN));
        delivery.setOrderId(orderId);
        delivery.setServiceProviderId(serviceProviderId);
        delivery.setCreatedTime(LocalDateTime.now());
        delivery.setPreparationTimeInMinutes(preparationTimeInMinutes);
        deliveryRepository.save(delivery);
    }

    @Override
    public void readyToPickUp(Long orderId) {
        Delivery delivery =
                deliveryRepository.findByOrderId(orderId).orElseThrow(NotFoundException::new);
        delivery.setStatus(DeliveryStatus.READY_TO_PICK_UP);
        delivery.setLastModified(LocalDateTime.now());
        deliveryRepository.save(delivery);
    }

    @Override
    public DeliveryResponse pickUp(Long deliveryId) {
        Delivery delivery =
                deliveryRepository.findById(deliveryId).orElseThrow(NotFoundException::new);

        checkPreconditionsFroPickUp(delivery);
        delivery.setStatus(DeliveryStatus.ON_THE_WAY);
        delivery.setLastModified(LocalDateTime.now());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderPickedUpEvent.builder().orderId(savedDelivery.getOrderId()).build());

        return deliveryMapper.map(savedDelivery);
    }

    @Override
    public DeliveryResponse getByOrderId(Long orderId) {
        return deliveryMapper.map(
                deliveryRepository.findByOrderId(orderId).orElseThrow(NotFoundException::new));
    }

    @Override
    public DeliveryResponse deliver(Long deliveryId) {
        Delivery delivery =
                deliveryRepository.findById(deliveryId).orElseThrow(NotFoundException::new);

        checkPreconditionsFroDeliver(delivery);
        delivery.setStatus(DeliveryStatus.FINISHED);
        delivery.setLastModified(LocalDateTime.now());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderDeliveredEvent.builder()
                        .orderId(savedDelivery.getOrderId())
                        .deliveredTime(LocalDateTime.now())
                        .build());

        return deliveryMapper.map(savedDelivery);
    }

    @Override
    public List<DeliveryResponse> getAllByStatus(DeliveryStatus status) {
        return deliveryRepository.findAllByStatus(status)
                .stream().map(deliveryMapper::map).collect(Collectors.toList());
    }

    private void checkPreconditionsFroPickUp(Delivery delivery) {
        if (delivery.getDeliveryMan() == null) {
            throw new BadRequestException(
                    String.format(
                            "Delivery [%d] does not have assigned delivery man and can not be picked up",
                            delivery.getId()));
        } else if (delivery.getStatus() != DeliveryStatus.READY_TO_PICK_UP) {
            throw new BadRequestException(
                    String.format(
                            "Delivery [%d] is not in correct status and can not be picked up",
                            delivery.getId()));
        }
    }

    private void checkPreconditionsFroDeliver(Delivery delivery) {
        if (delivery.getStatus() != DeliveryStatus.ON_THE_WAY) {
            throw new BadRequestException(
                    String.format(
                            "Delivery [%d] is not in correct status and can not be picked up",
                            delivery.getId()));
        }
    }

    // Checking if there is any pending delivery and assign available deliveryman to them.
    @Scheduled(fixedRate = 1000)
    private void checkPendingDeliveries() {
        deliveryRepository
                .findAllByStatusOrStatusAndDeliveryManIsNull(DeliveryStatus.PENDING_DELIVERY_MAN, DeliveryStatus.READY_TO_PICK_UP)
                .forEach(
                        delivery ->
                                findAvailableDeliveryMan(delivery.getServiceProviderId())
                                        .ifPresent(
                                                deliveryMan -> {
                                                    delivery.setDeliveryMan(deliveryMan);
                                                    delivery.setStatus(
                                                            DeliveryStatus.PENDING_PREPARATION);
                                                    delivery.setLastModified(LocalDateTime.now());
                                                    deliveryRepository.save(delivery);
                                                    kafkaTemplate.send(
                                                            "orderTopic",
                                                            "",
                                                            OrderAssignedToDeliveryManEvent.builder()
                                                                    .orderId(delivery.getOrderId())
                                                                    .preparationTimeInMinutes(delivery.getPreparationTimeInMinutes())
                                                                    .deliveryManId(deliveryMan.getId())
                                                                    .build());
                                                }));
    }

    private Optional<DeliveryMan> findAvailableDeliveryMan(Long serviceProviderId) {
        List<DeliveryMan> availableForDelivery =
                deliveryManRepository.findAvailableForDelivery(serviceProviderId);
        return availableForDelivery.stream().findAny();
    }
}
