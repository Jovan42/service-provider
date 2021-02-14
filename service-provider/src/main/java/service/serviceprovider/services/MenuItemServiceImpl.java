package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.dto.MenuItemRequest;
import service.serviceprovider.dto.MenuItemResponse;
import service.serviceprovider.repositories.MenuItemRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;

@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, ModelMapper modelMapper) {
        this.menuItemRepository = menuItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MenuItemResponse create(Long serviceProviderId, MenuItemRequest menuItemRequest) {
        return null;
    }

    @Override
    public MenuItemResponse getById(Long menuItemId) {
        return modelMapper.map(
                menuItemRepository.findById(menuItemId).orElseThrow(NotFoundException::new),
                MenuItemResponse.class);
    }

    @Override
    public MenuItemResponse update(
            Long serviceProviderId, Long menuItemId, MenuItemRequest menuItemRequest) {
        return null;
    }

    @Override
    public CustomPage<MenuItemResponse> searchByServiceProvider(
            Long serviceProviderId, Pageable pageable) {
        return null;
    }
}
