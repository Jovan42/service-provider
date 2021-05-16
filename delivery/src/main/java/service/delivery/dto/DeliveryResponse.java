package service.delivery.dto;

import service.delivery.domain.DeliveryMan;
import service.delivery.domain.enums.DeliveryStatus;

public class DeliveryResponse {
    private Long orderId;
    private DeliveryStatus status;
    private Long serviceProviderId;
    private Long preparationTimeInMinutes;
    private DeliveryMan deliveryMan;
}
