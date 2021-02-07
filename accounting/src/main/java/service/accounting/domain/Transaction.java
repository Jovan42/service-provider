package service.accounting.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Transaction extends BaseEntity {
    private Double amount;
    @ManyToOne
    private Account account;
    private Long orderId;
}
