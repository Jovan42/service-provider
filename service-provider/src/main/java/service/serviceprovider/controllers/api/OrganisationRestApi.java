package service.serviceprovider.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.OrganisationRequest;
import service.serviceprovider.dto.OrganisationRequestNoPassword;
import service.serviceprovider.dto.OrganisationResponse;
import service.sharedlib.dto.CustomPage;

@Api
@RequestMapping("organisations")
public interface OrganisationRestApi {

    @GetMapping
    @ApiOperation(value = "Search Organisations")
    ResponseEntity<CustomPage<OrganisationResponse>> search(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                            @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/{organisationId}")
    @ApiOperation(value = "Get Organisation by ID")
    ResponseEntity<OrganisationResponse> getById(@PathVariable Long organisationId);

    @PostMapping
    @ApiOperation(value = "Create Organisation")
    ResponseEntity<OrganisationResponse> create(@Validated @RequestBody OrganisationRequest organisationRequest);

    @PutMapping("/{organisationId}")
    @ApiOperation(value = "Update Organisation")
    ResponseEntity<OrganisationResponse> update(@PathVariable Long organisationId,
                                                @Validated @RequestBody OrganisationRequestNoPassword organisationRequest);

    @DeleteMapping("/{organisationId}")
    @ApiOperation(value = "Delete Organisation")
    ResponseEntity<Void> delete(@PathVariable Long organisationId);
}
