package service.delivery.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import service.delivery.domain.Delivery;
import service.delivery.domain.DeliveryMan;
import service.delivery.domain.enums.DeliveryManStatus;
import service.delivery.domain.enums.DeliveryStatus;
import service.delivery.dto.DeliveryResponse;
import service.delivery.repository.DeliveryManRepository;
import service.delivery.repository.DeliveryRepository;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderPickedUpEvent;
import service.sharedlib.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    private final ModelMapper modelMapper;

    public DeliveryServiceImpl(
            DeliveryRepository deliveryRepository,
            DeliveryManRepository deliveryManRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate,
            ModelMapper modelMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
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
        delivery.setStatus(DeliveryStatus.ON_THE_WAY);
        delivery.setLastModified(LocalDateTime.now());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderPickedUpEvent.builder().orderId(savedDelivery.getOrderId()).build());
        return modelMapper.map(savedDelivery, DeliveryResponse.class);
    }

    // Checking if there is any pending delivery and assign available deliveryman to them.
    @Scheduled(fixedRate = 1000)
    private void checkPendingDeliveries() {
        deliveryRepository
                .findAllByStatusAndDeliveryManIsNull(DeliveryStatus.PENDING_DELIVERY_MAN)
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
                                                }));
    }

    private Optional<DeliveryMan> findAvailableDeliveryMan(Long serviceProviderId) {
        List<DeliveryMan> availableForDelivery = deliveryManRepository
                .findAvailableForDelivery(serviceProviderId);
        return availableForDelivery
                .stream()
                .findAny();
    }
}
