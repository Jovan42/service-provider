package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;
import service.serviceprovider.domain.MenuItem;
import service.serviceprovider.domain.ServiceProvider;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class MenuPartResponse {
    private Long id;
    private String name;
    private String description;
    List<MenuItemsResponse> menuItems;
}
