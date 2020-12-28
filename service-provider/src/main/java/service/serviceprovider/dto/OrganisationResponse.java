package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationResponse {
    private Long id;
    private String name;
    private String email;
    private String description;
    private String address;
}
