package service.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import service.delivery.domain.enums.DeliveryStatus;

@Getter
@Setter
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private DeliveryStatus status;
    private Long serviceProviderId;
    private Long preparationTimeInMinutes;
    private Long deliveryManId;
}
