package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.ServiceProvider;
import service.serviceprovider.dto.ServiceProviderRequest;
import service.serviceprovider.dto.ServiceProviderResponse;
import service.serviceprovider.repositories.OrganisationRepository;
import service.serviceprovider.repositories.ServiceProviderRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private final ModelMapper modelMapper;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OrganisationRepository organisationRepository;

    public ServiceProviderServiceImpl(ModelMapper modelMapper,
                                      ServiceProviderRepository serviceProviderRepository,
                                      OrganisationRepository organisationRepository) {
        this.modelMapper = modelMapper;
        this.serviceProviderRepository = serviceProviderRepository;
        this.organisationRepository = organisationRepository;
    }

    @Override
    public ServiceProviderResponse create(Long organisationId, ServiceProviderRequest serviceProviderRequest) {
        ServiceProvider serviceProvider = modelMapper.map(serviceProviderRequest, ServiceProvider.class);
        serviceProvider.setCreatedTime(LocalDateTime.now());
        serviceProvider.setOrganisation(organisationRepository.findById(organisationId)
                .orElseThrow(NotFoundException::new));
        return modelMapper.map(serviceProviderRepository.save(serviceProvider), ServiceProviderResponse.class);
    }

    @Override
    public CustomPage<ServiceProviderResponse> search(Pageable pageable) {
        return new CustomPage<>(serviceProviderRepository.findAll(pageable)
                .map(serviceProvider -> modelMapper.map(serviceProvider, ServiceProviderResponse.class)));
    }

    @Override
    public ServiceProviderResponse getById(Long serviceProviderId) {
        return modelMapper.map(serviceProviderRepository.findById(serviceProviderId).orElseThrow(NotFoundException::new)
                , ServiceProviderResponse.class);
    }

    @Override
    public ServiceProviderResponse update(Long organisationId,
                                          Long serviceProviderId,
                                          ServiceProviderRequest serviceProviderRequest) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(NotFoundException::new);
        serviceProvider.setLastModified(LocalDateTime.now());
        serviceProvider.setName(serviceProviderRequest.getName());
        serviceProvider.setDescription(serviceProviderRequest.getDescription());
        serviceProvider.setAddress(serviceProviderRequest.getAddress());
        serviceProvider.setOrganisation(organisationRepository.findById(organisationId)
                .orElseThrow(NotFoundException::new));
        return modelMapper.map(serviceProviderRepository.save(serviceProvider), ServiceProviderResponse.class);

    }

    @Override
    public CustomPage<ServiceProviderResponse> searchByOrganisation(Long organisationId, Pageable pageable) {
        return new CustomPage<>(serviceProviderRepository.findAllByOrganisation_Id(organisationId, pageable)
                .map(serviceProvider -> modelMapper.map(serviceProvider, ServiceProviderResponse.class)));
    }
}
