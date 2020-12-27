package service.serviceprovider.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class SpecificationItem extends BaseEntity {
    private Long id;
    private String name;
    private String value;
    @ManyToOne
    private SpecificationPart specificationPart;
}
