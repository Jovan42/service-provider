package service.accounting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.accounting.controller.api.UserRestApi;
import service.accounting.dto.*;
import service.accounting.service.AccountService;

import java.util.List;

@Controller
public class UserController implements UserRestApi {
    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<AccountResponse> addAccount(String userId, AccountRequest accountRequest) {
        return new ResponseEntity<>(accountService.addAccount(userId, accountRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AccountResponse>> getAccountsForCurrentUser() {
        return new ResponseEntity<>(accountService.getAccountsForCurrentUser(), HttpStatus.OK);
    }
}
