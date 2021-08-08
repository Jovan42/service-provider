package service.serviceprovider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.serviceprovider.controllers.api.OrganisationRestApi;
import service.serviceprovider.dto.OrganisationRequest;
import service.serviceprovider.dto.OrganisationRequestNoPassword;
import service.serviceprovider.dto.OrganisationResponse;
import service.serviceprovider.services.OrganisationService;
import service.sharedlib.dto.CustomPage;

import javax.annotation.security.RolesAllowed;

@Controller
public class OrganisationController implements OrganisationRestApi {
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @Override
    public ResponseEntity<CustomPage<OrganisationResponse>> search(Integer pageNumber,
                                                                   Integer pageSize) {
        return new ResponseEntity<>(organisationService.search(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrganisationResponse> getById(Long organisationId) {
        return new ResponseEntity<>(organisationService.getById(organisationId), HttpStatus.OK);
    }

    @Override
    @RolesAllowed("manage-account")
    public ResponseEntity<OrganisationResponse> create(OrganisationRequest organisationRequest) {
        return new ResponseEntity<>(organisationService.create(organisationRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrganisationResponse> update(Long organisationId,
                                                       OrganisationRequestNoPassword organisationRequest) {
        return new ResponseEntity<>(organisationService.update(organisationId, organisationRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long organisationId) {
        organisationService.delete(organisationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
