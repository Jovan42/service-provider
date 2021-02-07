package service.accounting.domain;

import lombok.Getter;
import lombok.Setter;
import service.accounting.domain.enums.AccountStatus;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Account extends BaseEntity {
    private String accountNumber;
    private AccountStatus status;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
