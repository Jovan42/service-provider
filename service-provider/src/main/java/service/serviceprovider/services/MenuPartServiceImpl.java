package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.MenuPart;
import service.serviceprovider.dto.MenuPartRequest;
import service.serviceprovider.dto.MenuPartResponse;
import service.serviceprovider.repositories.MenuPartRepository;
import service.serviceprovider.repositories.ServiceProviderRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class MenuPartServiceImpl implements MenuPartService {
    private final ModelMapper modelMapper;
    private final ServiceProviderRepository serviceProviderRepository;
    private final MenuPartRepository menuPartRepository;

    @Autowired
    public MenuPartServiceImpl(ModelMapper modelMapper, ServiceProviderRepository serviceProviderRepository, MenuPartRepository menuPartRepository) {
        this.modelMapper = modelMapper;
        this.serviceProviderRepository = serviceProviderRepository;
        this.menuPartRepository = menuPartRepository;
    }

    @Override
    public MenuPartResponse create(Long serviceProviderId, MenuPartRequest menuPartRequest) {
        MenuPart menuPart = modelMapper.map(menuPartRequest, MenuPart.class);
        menuPart.getMenuItems().forEach(menuItem -> menuItem.setMenuPart(menuPart));
        menuPart.setCreatedTime(LocalDateTime.now());
        menuPart.setServiceProvider(serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(NotFoundException::new));
        return modelMapper.map(menuPartRepository.save(menuPart), MenuPartResponse.class);
    }

    @Override
    public MenuPartResponse getById(Long menuPartId) {
        return modelMapper.map(menuPartRepository.findById(menuPartId).orElseThrow(NotFoundException::new)
                , MenuPartResponse.class);
    }

    @Override
    public MenuPartResponse update(Long serviceProviderId, Long menuPartId, MenuPartRequest menuPartRequest) {
        MenuPart menuPart = menuPartRepository.findById(serviceProviderId)
                .orElseThrow(NotFoundException::new);
        menuPart.setLastModified(LocalDateTime.now());
        menuPart.setServiceProvider(serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(NotFoundException::new));
        return modelMapper.map(menuPartRepository.save(menuPart), MenuPartResponse.class);
    }

    @Override
    public CustomPage<MenuPartResponse> searchByServiceProvider(Long serviceProviderId, Pageable pageable) {
        return new CustomPage<>(menuPartRepository.findAllByServiceProvider_Id(serviceProviderId, pageable)
                .map(menuPart -> modelMapper.map(menuPart, MenuPartResponse.class)));
    }
}
