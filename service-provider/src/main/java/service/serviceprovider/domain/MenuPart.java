package service.serviceprovider.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class MenuPart extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(mappedBy = "menuPart")
    List<MenuItem> menuItems;
    @ManyToOne
    private ServiceProvider serviceProvider;
}
