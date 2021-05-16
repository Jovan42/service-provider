package service.delivery.domain;

import lombok.Getter;
import lombok.Setter;
import service.delivery.domain.enums.DeliveryStatus;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Delivery extends BaseEntity {
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private Long serviceProviderId;
    private Long preparationTimeInMinutes;
    @ManyToOne
    private DeliveryMan deliveryMan;
}
