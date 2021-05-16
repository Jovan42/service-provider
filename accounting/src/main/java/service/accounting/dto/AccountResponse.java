package service.accounting.dto;

import lombok.Getter;
import lombok.Setter;
import service.accounting.domain.enums.AccountStatus;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private AccountStatus status = AccountStatus.ACTIVE;
    private Double balance = 0d;
}
