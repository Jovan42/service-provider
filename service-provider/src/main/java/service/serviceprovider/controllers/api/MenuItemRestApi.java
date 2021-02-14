package service.serviceprovider.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.MenuItemRequest;
import service.serviceprovider.dto.MenuItemResponse;
import service.sharedlib.dto.CustomPage;

@Api
@RequestMapping("menuItems")
public interface MenuItemRestApi {

    @GetMapping("/{menuItemId}")
    @ApiOperation(value = "Get MenuItem by ID")
    ResponseEntity<MenuItemResponse> getById(@PathVariable(required = false) Long menuItemId);

    @GetMapping("")
    @ApiOperation(value = "Search service providers menu")
    ResponseEntity<CustomPage<MenuItemResponse>> search(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @PostMapping("/{menuItemId}")
    @ApiOperation(value = "Create MenuItem for specific service provider")
    ResponseEntity<MenuItemResponse> create(
            @PathVariable Long menuItemId, @Validated @RequestBody MenuItemRequest menuItemRequest);

    @PutMapping("/{menuItemId}")
    @ApiOperation(value = "Update MenuItem")
    ResponseEntity<MenuItemResponse> update(
            @PathVariable Long menuItemId, @Validated @RequestBody MenuItemRequest menuItemRequest);
}
