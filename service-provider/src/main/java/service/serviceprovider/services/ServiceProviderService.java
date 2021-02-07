package service.serviceprovider.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.dto.ServiceProviderRequest;
import service.serviceprovider.dto.ServiceProviderResponse;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.events.OrderCreatedEvent;

@Service
public interface ServiceProviderService {
    ServiceProviderResponse create(Long organisationId, ServiceProviderRequest serviceProviderRequest);

    CustomPage<ServiceProviderResponse> search(Pageable pageable);

    ServiceProviderResponse getById(Long serviceProviderId);

    ServiceProviderResponse update(Long organisationId,
                                   Long serviceProviderId,
                                   ServiceProviderRequest serviceProviderRequest);

    CustomPage<ServiceProviderResponse> searchByOrganisation(Long organisationId, Pageable pageable);

    void validateOrder(OrderCreatedEvent orderCreatedEvent);
}
