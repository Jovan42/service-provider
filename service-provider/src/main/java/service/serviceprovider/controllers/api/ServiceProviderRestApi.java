package service.serviceprovider.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.ServiceProviderRequest;
import service.serviceprovider.dto.ServiceProviderResponse;
import service.sharedlib.dto.CustomPage;

@Api
@RequestMapping()
public interface ServiceProviderRestApi {

    @GetMapping("/serviceProviders")
    @ApiOperation(value = "Search ServiceProvider")
    ResponseEntity<CustomPage<ServiceProviderResponse>> search(@RequestParam Integer pageNumber,
                                                               @RequestParam Integer pageSize);

    @GetMapping("/serviceProviders/{serviceProviderId}")
    @ApiOperation(value = "Get ServiceProvider by ID")
    ResponseEntity<ServiceProviderResponse> getById(@PathVariable Long serviceProviderId);

    @GetMapping("/organisations/{organisationId}/serviceProviders")
    @ApiOperation(value = "Search ServiceProvider owned by Organisation")
    ResponseEntity<CustomPage<ServiceProviderResponse>> searchServiceProviders(@PathVariable Long organisationId,
                                                                               @RequestParam Integer pageNumber,
                                                                               @RequestParam Integer pageSize);

    @PostMapping("/organisations/{organisationId}")
    @ApiOperation(value = "Create ServiceProvider under Organisation")
    ResponseEntity<ServiceProviderResponse> create(@PathVariable Long organisationId,
                                                   @Validated @RequestBody ServiceProviderRequest serviceProviderRequest);

    @PutMapping("/organisations/{organisationId}/serviceProviders/{serviceProviderId}")
    @ApiOperation(value = "Update ServiceProvider")
    ResponseEntity<ServiceProviderResponse> update(@PathVariable Long organisationId,
                                                   @PathVariable Long serviceProviderId,
                                                   @Validated @RequestBody ServiceProviderRequest serviceProviderRequest);
}
