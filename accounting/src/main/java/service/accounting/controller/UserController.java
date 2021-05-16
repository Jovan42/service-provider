package service.accounting.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.accounting.controller.api.UserRestApi;
import service.accounting.dto.*;
import service.accounting.repository.AccountRepository;
import service.accounting.service.AccountService;
import service.accounting.service.UserService;
import service.sharedlib.dto.CustomPage;

@Controller
public class UserController implements UserRestApi {
    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService,AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<CustomPage<UserResponse>> search(Integer pageNumber, Integer pageSize) {
        return new ResponseEntity<>(
                userService.search(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> getById(Long userId) {
        return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> create(UserCreateRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponse> update(Long userId, UserEditRequest userRequest) {
        return new ResponseEntity<>(userService.update(userId, userRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountResponse> addAccount(Long userId, AccountRequest accountRequest) {
        return new ResponseEntity<>(accountService.addAccount(userId, accountRequest), HttpStatus.OK);
    }
}
