package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuPartResponse {
    private Long id;
    private String name;
    private String description;
    List<MenuItemResponse> menuItems;
}
