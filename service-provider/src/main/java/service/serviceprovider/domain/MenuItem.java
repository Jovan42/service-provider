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
public class MenuItem extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private Boolean showSpecification;
    private Boolean showAdditionalRequirements;
    @OneToMany(mappedBy = "menuItem")
    List<Specification> specifications;
    @ManyToOne()
    private MenuPart menuPart;
}
