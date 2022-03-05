package service.accounting.dto;

import lombok.Getter;
import lombok.Setter;
import service.accounting.domain.enums.AccountStatus;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountRequest {
    @NotBlank
    private String accountNumber;
    private AccountStatus status = AccountStatus.ACTIVE;
    private Double balance = 0d;


}
