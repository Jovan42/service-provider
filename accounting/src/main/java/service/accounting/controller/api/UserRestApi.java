package service.accounting.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.accounting.dto.UserCreateRequest;
import service.accounting.dto.UserEditRequest;
import service.accounting.dto.UserResponse;
import service.sharedlib.dto.CustomPage;

@Api
@RequestMapping("users")
public interface UserRestApi {
    @GetMapping("")
    @ApiOperation(value = "Search User")
    ResponseEntity<CustomPage<UserResponse>> search(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/{userId}")
    @ApiOperation(value = "Get User by ID")
    ResponseEntity<UserResponse> getById(@PathVariable Long userId);

    @PostMapping("")
    @ApiOperation(value = "Create new User")
    ResponseEntity<UserResponse> create(@Validated @RequestBody UserCreateRequest userRequest);

    @PutMapping("/{userId}")
    @ApiOperation(value = "Update User")
    ResponseEntity<UserResponse> update(
            @PathVariable Long userId, @Validated @RequestBody UserEditRequest userRequest);
}
