package service.serviceprovider.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@Getter
@Setter
public class Organisation extends BaseEntity {

    private String name;
    private String email;
    private String password;
    private String description;
    private String address;
    @OneToMany(mappedBy = "organisation")
    List<ServiceProvider> serviceProviders;

}
