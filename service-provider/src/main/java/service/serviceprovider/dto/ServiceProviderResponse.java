package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceProviderResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Boolean manualApprovalRequired;
    private List<MenuPartResponse> menuParts;
}
