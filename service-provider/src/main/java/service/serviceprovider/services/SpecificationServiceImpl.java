package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.MenuItem;
import service.serviceprovider.domain.Specification;
import service.serviceprovider.dto.SpecificationRequest;
import service.serviceprovider.dto.SpecificationResponse;
import service.serviceprovider.repositories.MenuItemRepository;
import service.serviceprovider.repositories.SpecificationRepository;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    private final ModelMapper modelMapper;
    private final MenuItemRepository menuItemRepository;
    private final SpecificationRepository specificationRepository;

    public SpecificationServiceImpl(
            ModelMapper modelMapper,
            MenuItemRepository menuItemRepository,
            SpecificationRepository specificationRepository) {
        this.modelMapper = modelMapper;
        this.menuItemRepository = menuItemRepository;
        this.specificationRepository = specificationRepository;
    }

    @Override
    public List<SpecificationResponse> create(
            Long menuItemId, List<SpecificationRequest> specificationsRequest) {
        MenuItem menuItem =
                menuItemRepository.findById(menuItemId).orElseThrow(NotFoundException::new);
        invalidatePreviousSpecification(menuItemId);
        List<Specification> listToSave =
                specificationsRequest.stream()
                        .map(
                                spec -> {
                                    Specification mapped =
                                            modelMapper.map(spec, Specification.class);
                                    mapped.setMenuItem(menuItem);
                                    mapped.setCreatedTime(LocalDateTime.now());
                                    return mapped;
                                })
                        .collect(Collectors.toList());
        return specificationRepository.saveAll(listToSave).stream()
                .map(spec -> modelMapper.map(spec, SpecificationResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecificationResponse> get(Long menuItemId) {
        return specificationRepository
                .findAllByMenuItem(menuItemId)
                .stream()
                .map(spec -> modelMapper.map(spec, SpecificationResponse.class))
                .collect(Collectors.toList());
    }

    private void invalidatePreviousSpecification(Long menuItemId) {
        specificationRepository
                .findAllByMenuItem_IdAndDeletedOrDeleted(menuItemId, false, null)
                .forEach(
                        spec -> {
                            spec.setDeleted(true);
                        });
    }
}
