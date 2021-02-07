package service.accounting.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Account> accounts;
}
