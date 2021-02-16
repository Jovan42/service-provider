package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.MenuItem;
import service.serviceprovider.domain.MenuPart;
import service.serviceprovider.dto.MenuItemRequest;
import service.serviceprovider.dto.MenuItemResponse;
import service.serviceprovider.repositories.MenuItemRepository;
import service.serviceprovider.repositories.MenuPartRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;

@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuPartRepository menuPartRepository;
    private final ModelMapper modelMapper;

    public MenuItemServiceImpl(
            MenuItemRepository menuItemRepository,
            MenuPartRepository menuPartRepository,
            ModelMapper modelMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuPartRepository = menuPartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MenuItemResponse create(Long menuPartId, MenuItemRequest menuItemRequest) {
        MenuPart menuPart =
                menuPartRepository.findById(menuPartId).orElseThrow(NotFoundException::new);
        MenuItem menuItem = modelMapper.map(menuItemRequest, MenuItem.class);
        menuItem.setMenuPart(menuPart);
        return modelMapper.map(menuItemRepository.save(menuItem), MenuItemResponse.class);
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
    public CustomPage<MenuItemResponse> searchByMenuPartId(Long menuPartId, Pageable pageable) {
        return null;
    }
}
