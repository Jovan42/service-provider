package service.serviceprovider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.OrganisationRequest;
import service.serviceprovider.dto.OrganisationRequestNoPassword;
import service.serviceprovider.dto.OrganisationResponse;
import service.serviceprovider.services.OrganisationService;
import service.sharedlib.dto.CustomPage;

@Controller
@RequestMapping("organisations")
public class OrganisationController {
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping
    private ResponseEntity<CustomPage<OrganisationResponse>> search(@RequestParam Integer pageNumber,
                                                                    @RequestParam Integer pageSize) {
        return new ResponseEntity<>(organisationService.search(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{organisationId}")
    private ResponseEntity<OrganisationResponse> getById(@PathVariable Long organisationId) {
        return new ResponseEntity<>(organisationService.getById(organisationId), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<OrganisationResponse> create(@Validated @RequestBody OrganisationRequest organisationRequest) {
        return new ResponseEntity<>(organisationService.create(organisationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{organisationId}")
    private ResponseEntity<OrganisationResponse> update(@PathVariable Long organisationId,
                                                        @Validated @RequestBody OrganisationRequestNoPassword organisationRequest) {
        return new ResponseEntity<>(organisationService.update(organisationId, organisationRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{organisationId}")
    private ResponseEntity<Void> delete(@PathVariable Long organisationId) {
        organisationService.delete(organisationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
