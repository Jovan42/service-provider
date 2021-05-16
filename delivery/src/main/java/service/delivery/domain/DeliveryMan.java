package service.delivery.domain;

import lombok.Getter;
import lombok.Setter;
import service.delivery.domain.enums.DeliveryManStatus;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class DeliveryMan extends BaseEntity {
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private DeliveryManStatus status;

    @OneToOne private ContactInformation contactInformation;
    private Long serviceProviderId;

    @OneToMany(mappedBy = "deliveryMan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Delivery> deliveries;
}
