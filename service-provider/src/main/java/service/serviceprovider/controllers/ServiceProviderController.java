package service.serviceprovider.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.*;
import service.serviceprovider.services.ServiceProviderService;
import service.sharedlib.dto.CustomPage;

@Controller
@RequestMapping()
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @GetMapping
    private ResponseEntity<CustomPage<ServiceProviderResponse>> search(@RequestParam Integer pageNumber,
                                                                    @RequestParam Integer pageSize) {
        return new ResponseEntity<>(serviceProviderService.search(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{serviceProviderId}")
    private ResponseEntity<ServiceProviderResponse> getById(@PathVariable Long serviceProviderId) {
        return new ResponseEntity<>(serviceProviderService.getById(serviceProviderId), HttpStatus.OK);
    }

    @GetMapping("/organisations/{organisationId}/serviceProviders")
    private ResponseEntity<CustomPage<ServiceProviderResponse>> searchServiceProviders(@PathVariable Long organisationId,
                                                                                       @RequestParam Integer pageNumber,
                                                                                       @RequestParam Integer pageSize) {
        return new ResponseEntity<>(serviceProviderService
                .searchByOrganisation(organisationId, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @PostMapping("/organisations/{organisationId}")
    private ResponseEntity<ServiceProviderResponse> create(@PathVariable Long organisationId,
                                                           @Validated @RequestBody ServiceProviderRequest serviceProviderRequest) {
        return new ResponseEntity<>(serviceProviderService.create(organisationId, serviceProviderRequest), HttpStatus.CREATED);
    }

    @PutMapping("/organisations/{organisationId}/serviceProviders/{serviceProviderId}")
    private ResponseEntity<ServiceProviderResponse> update(@PathVariable Long organisationId,
                                                           @PathVariable Long serviceProviderId,
                                                           @Validated @RequestBody ServiceProviderRequest serviceProviderRequest) {
        return new ResponseEntity<>(serviceProviderService.update(organisationId, serviceProviderId, serviceProviderRequest), HttpStatus.OK);
    }
}
