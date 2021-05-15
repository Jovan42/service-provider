package service.delivery.domain;

import lombok.Getter;
import lombok.Setter;
import service.delivery.domain.enums.DeliveryStatus;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Delivery extends BaseEntity {
    private Long orderId;
    private DeliveryStatus status;
    @ManyToOne
    private DeliveryMan deliveryMan;
}
