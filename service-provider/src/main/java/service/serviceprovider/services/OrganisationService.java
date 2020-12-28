package service.serviceprovider.services;

import org.springframework.data.domain.Pageable;
import service.serviceprovider.dto.OrganisationRequest;
import service.serviceprovider.dto.OrganisationRequestNoPassword;
import service.serviceprovider.dto.OrganisationResponse;
import service.sharedlib.dto.CustomPage;


public interface OrganisationService {
    OrganisationResponse create(OrganisationRequest organisationRequest);

    CustomPage<OrganisationResponse> search(Pageable pageable);

    OrganisationResponse getById(Long organisationId);

    OrganisationResponse update(Long organisationId, OrganisationRequestNoPassword organisationRequest);
}
