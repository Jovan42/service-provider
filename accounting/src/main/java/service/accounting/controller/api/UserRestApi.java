package service.accounting.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.accounting.dto.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Api
@RequestMapping("")
public interface UserRestApi {
    @PostMapping("users/{userId}/account")
    @ApiOperation(value = "Add account to User")
    ResponseEntity<AccountResponse> addAccount(
            @PathVariable String userId, @Validated @RequestBody AccountRequest accountRequest);

    @GetMapping("/accounts")
    @ApiOperation(value = "Add account to User")
    @RolesAllowed("manage-account")
    ResponseEntity<List<AccountResponse>> getAccountsForCurrentUser();
}
