package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceProviderResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
}
