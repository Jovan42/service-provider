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
public class ServiceProvider extends BaseEntity {
    private String name;
    private String address;
    private String description;
    @OneToMany(mappedBy = "serviceProvider")
    List<MenuPart> menuParts;
    @ManyToOne
    private Organisation organisation;
    private Boolean manualApprovalRequired;
}
