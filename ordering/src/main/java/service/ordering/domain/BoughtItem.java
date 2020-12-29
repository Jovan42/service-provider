package service.ordering.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class BoughtItem extends BaseEntity {
    private Long menuItemId;
    private Double currentPricePerUnit;
    private Integer amount;
    @ManyToOne
    private Order order;
}
