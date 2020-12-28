package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class MenuPartRequest {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    List<MenuItemsRequest> menuItems;

}
