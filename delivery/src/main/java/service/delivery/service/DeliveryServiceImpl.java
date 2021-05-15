package service.delivery.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import service.delivery.domain.Delivery;
import service.delivery.domain.DeliveryMan;
import service.delivery.domain.enums.DeliveryManStatus;
import service.delivery.domain.enums.DeliveryStatus;
import service.delivery.repository.DeliveryManRepository;
import service.delivery.repository.DeliveryRepository;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.DeliveryStartedEvent;
import service.sharedlib.events.OrderItemsApprovedEvent;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public DeliveryServiceImpl(
            DeliveryRepository deliveryRepository,
            DeliveryManRepository deliveryManRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createDelivery(
            Long orderId, Long preparationTimeInMinutes, Long serviceProviderId) {
        Delivery delivery = new Delivery();
        findAvailableDeliveryMan(serviceProviderId)
                .ifPresentOrElse(
                        deliveryMan -> {
                            delivery.setDeliveryMan(deliveryMan);
                            delivery.setStatus(DeliveryStatus.ACTIVE);
                        },
                        () -> delivery.setStatus(DeliveryStatus.PENDING));
        delivery.setOrderId(orderId);
        delivery.setServiceProviderId(serviceProviderId);
        delivery.setCreatedTime(LocalDateTime.now());
        delivery.setPreparationTimeInMinutes(preparationTimeInMinutes);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        publishDeliveryStartedEvent(savedDelivery.getOrderId());
    }

    // Checking if there is any pending delivery and assign available deliveryman to them.
    @Scheduled(fixedRate = 60000)
    private void checkPendingDeliveries() {
        deliveryRepository
                .findAllByStatusAndDeliveryManIsNull(DeliveryStatus.PENDING)
                .forEach(
                        delivery -> {
                            findAvailableDeliveryMan(delivery.getServiceProviderId())
                                    .ifPresent(
                                            deliveryMan -> {
                                                delivery.setDeliveryMan(deliveryMan);
                                                delivery.setStatus(DeliveryStatus.ACTIVE);
                                                delivery.setLastModified(LocalDateTime.now());
                                                Delivery savedDelivery = deliveryRepository.save(delivery);
                                                publishDeliveryStartedEvent(savedDelivery.getOrderId());
                                            });
                        });
    }

    private Optional<DeliveryMan> findAvailableDeliveryMan(Long serviceProviderId) {
        return deliveryManRepository
                .findAvailableForDelivery(serviceProviderId, DeliveryManStatus.ACTIVE)
                .stream()
                .findAny();
    }

    private void publishDeliveryStartedEvent (Long orderId){
        kafkaTemplate.send(
                "orderTopic",
                "",
                DeliveryStartedEvent.builder()
                        .orderId(orderId)
                        .build());
    }
}
