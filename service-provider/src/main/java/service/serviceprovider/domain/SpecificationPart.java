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
public class SpecificationPart extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(mappedBy = "specificationPart")
    List<SpecificationItem> specificationItems;
    @ManyToOne
    private MenuItem menuItem;
}
