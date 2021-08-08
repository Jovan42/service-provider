package service.accounting.service;

import service.accounting.dto.AccountRequest;
import service.accounting.dto.AccountResponse;

import java.util.List;

public interface AccountService {
    void validateOrder(String userId, Long accountId, Double price, Long orderId);

    AccountResponse addAccount(String userId, AccountRequest accountRequest);

    List<AccountResponse> getAccountsForCurrentUser();
}
