package service.accounting.service;

import service.accounting.dto.AccountRequest;
import service.accounting.dto.AccountResponse;

public interface AccountService {
    void validateOrder(Long userId, Long accountId, Double price, Long orderId);

    AccountResponse addAccount(Long userId, AccountRequest accountRequest);
}
