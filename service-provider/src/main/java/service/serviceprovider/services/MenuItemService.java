package service.serviceprovider.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.serviceprovider.dto.MenuItemRequest;
import service.serviceprovider.dto.MenuItemResponse;
import service.sharedlib.dto.CustomPage;

@Service
public interface MenuItemService {
    MenuItemResponse create(Long serviceProviderId, MenuItemRequest menuItemRequest);

    MenuItemResponse getById(Long menuItemId);

    MenuItemResponse update(Long serviceProviderId,
                            Long menuItemId,
                            MenuItemRequest menuItemRequest);

    CustomPage<MenuItemResponse> searchByServiceProvider(Long serviceProviderId, Pageable pageable);

}
