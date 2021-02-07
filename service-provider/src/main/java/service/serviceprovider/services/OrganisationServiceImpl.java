package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.Organisation;
import service.serviceprovider.dto.OrganisationRequest;
import service.serviceprovider.dto.OrganisationRequestNoPassword;
import service.serviceprovider.dto.OrganisationResponse;
import service.serviceprovider.repositories.OrganisationRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {
    private final ModelMapper modelMapper;
    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationServiceImpl(ModelMapper modelMapper, OrganisationRepository organisationRepository) {
        this.modelMapper = modelMapper;
        this.organisationRepository = organisationRepository;
    }

    @Override
    public OrganisationResponse create(OrganisationRequest organisationRequest) {
        Organisation organisation = modelMapper.map(organisationRequest, Organisation.class);
        organisation.setCreatedTime(LocalDateTime.now());
        return modelMapper.map(organisationRepository.save(organisation), OrganisationResponse.class);
    }

    @Override
    public CustomPage<OrganisationResponse> search(Pageable pageable) {
        return new CustomPage<>(organisationRepository.findAll(pageable)
                .map(organisation -> modelMapper.map(organisation, OrganisationResponse.class)));
    }

    @Override
    public OrganisationResponse getById(Long organisationId) {
        return modelMapper.map(organisationRepository.findById(organisationId).orElseThrow(NotFoundException::new)
                , OrganisationResponse.class);
    }

    @Override
    public OrganisationResponse update(Long organisationId, OrganisationRequestNoPassword organisationRequest) {
        Organisation organisation = organisationRepository.findById(organisationId).orElseThrow(NotFoundException::new);
        organisation.setLastModified(LocalDateTime.now());
        organisation.setName(organisationRequest.getName());
        organisation.setEmail(organisationRequest.getEmail());
        organisation.setDescription(organisationRequest.getDescription());
        organisation.setAddress(organisationRequest.getAddress());
        return modelMapper.map(organisationRepository.save(organisation), OrganisationResponse.class);
    }

    @Override
    public void delete(Long organisationId) {
        Organisation organisation = organisationRepository.findById(organisationId).orElseThrow(NotFoundException::new);
        organisation.setDeleted(true);
    }
}