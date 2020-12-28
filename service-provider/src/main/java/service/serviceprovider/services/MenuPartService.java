package service.serviceprovider.services;

import org.springframework.data.domain.Pageable;
import service.serviceprovider.dto.MenuPartRequest;
import service.serviceprovider.dto.MenuPartResponse;
import service.sharedlib.dto.CustomPage;

public interface MenuPartService {
    MenuPartResponse create(Long serviceProviderId, MenuPartRequest menuPartRequest);

    MenuPartResponse getById(Long menuPartId);

    MenuPartResponse update(Long serviceProviderId,
                            Long menuPartId,
                            MenuPartRequest menuPartRequest);

    CustomPage<MenuPartResponse> searchByServiceProvider(Long serviceProviderId, Pageable pageable);

}
