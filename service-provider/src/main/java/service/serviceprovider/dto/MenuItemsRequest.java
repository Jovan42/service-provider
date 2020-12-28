package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemsRequest {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
