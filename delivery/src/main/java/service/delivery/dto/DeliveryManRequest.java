package service.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import service.delivery.domain.ContactInformation;
import service.delivery.domain.enums.DeliveryManStatus;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DeliveryManRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private DeliveryManStatus status = DeliveryManStatus.ACTIVE;
    private ContactInformation contactInformation;
    @NotBlank
    private Long serviceProviderId;
}
