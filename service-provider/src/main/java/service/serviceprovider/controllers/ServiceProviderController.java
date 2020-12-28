package service.serviceprovider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.serviceprovider.controllers.api.ServiceProviderRestApi;
import service.serviceprovider.dto.ServiceProviderRequest;
import service.serviceprovider.dto.ServiceProviderResponse;
import service.serviceprovider.services.ServiceProviderService;
import service.sharedlib.dto.CustomPage;

@Controller
public class ServiceProviderController implements ServiceProviderRestApi {
    private final ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @Override
    public ResponseEntity<CustomPage<ServiceProviderResponse>> search(Integer pageNumber,
                                                                      Integer pageSize) {
        return new ResponseEntity<>(serviceProviderService.search(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ServiceProviderResponse> getById(Long serviceProviderId) {
        return new ResponseEntity<>(serviceProviderService.getById(serviceProviderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomPage<ServiceProviderResponse>> searchServiceProviders(Long organisationId,
                                                                                      Integer pageNumber,
                                                                                      Integer pageSize) {
        return new ResponseEntity<>(serviceProviderService
                .searchByOrganisation(organisationId, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ServiceProviderResponse> create(Long organisationId,
                                                          ServiceProviderRequest serviceProviderRequest) {
        return new ResponseEntity<>(serviceProviderService.create(organisationId, serviceProviderRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ServiceProviderResponse> update(Long organisationId,
                                                          Long serviceProviderId,
                                                          ServiceProviderRequest serviceProviderRequest) {
        return new ResponseEntity<>(serviceProviderService.update(organisationId, serviceProviderId, serviceProviderRequest), HttpStatus.OK);
    }
}
