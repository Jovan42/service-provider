package service.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import service.delivery.domain.ContactInformation;
import service.delivery.domain.enums.DeliveryManStatus;

@Getter
@Setter
public class DeliveryManResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private DeliveryManStatus status = DeliveryManStatus.ACTIVE;
    private ContactInformation contactInformation;
    private Long serviceProviderId;
}
