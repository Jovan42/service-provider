package service.serviceprovider.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.MenuPartRequest;
import service.serviceprovider.dto.MenuPartResponse;
import service.sharedlib.dto.CustomPage;

@Api
@RequestMapping("")
public interface MenuPartRestApi {

    @GetMapping("menuParts/{menuPartId}")
    @ApiOperation(value = "Get MenuPart by ID")
    ResponseEntity<MenuPartResponse> getById(@PathVariable Long menuPartId);

    @GetMapping("/serviceProviders/{serviceProviderId}/menu")
    @ApiOperation(value = "Search service providers menu")
    ResponseEntity<CustomPage<MenuPartResponse>> searchServiceProviders(@PathVariable Long serviceProviderId,
                                                                        @RequestParam Integer pageNumber,
                                                                        @RequestParam Integer pageSize);

    @PostMapping("/serviceProviders/{serviceProviderId}")
    @ApiOperation(value = "Create MenuPart for specific service provider")
    ResponseEntity<MenuPartResponse> create(@PathVariable Long serviceProviderId,
                                            @Validated @RequestBody MenuPartRequest menuPartRequest);

    @PutMapping("/serviceProviders/{serviceProviderId}/menuParts/{menuPartId}")
    @ApiOperation(value = "Update MenuPart")
    ResponseEntity<MenuPartResponse> update(@PathVariable Long serviceProviderId,
                                            @PathVariable Long menuPartId,
                                            @Validated @RequestBody MenuPartRequest menuPartRequest);
}
