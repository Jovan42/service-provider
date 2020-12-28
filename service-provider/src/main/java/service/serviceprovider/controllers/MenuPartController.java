package service.serviceprovider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.serviceprovider.controllers.api.MenuPartRestApi;
import service.serviceprovider.dto.MenuPartRequest;
import service.serviceprovider.dto.MenuPartResponse;
import service.serviceprovider.services.MenuPartService;
import service.sharedlib.dto.CustomPage;

@Controller
public class MenuPartController implements MenuPartRestApi {
    private final MenuPartService menuPartService;

    @Autowired
    public MenuPartController(MenuPartService menuPartService) {
        this.menuPartService = menuPartService;
    }

    @Override
    public ResponseEntity<MenuPartResponse> getById(Long menuPartId) {
        return new ResponseEntity<>(menuPartService.getById(menuPartId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomPage<MenuPartResponse>> searchServiceProviders(Long serviceProviderId,
                                                                               Integer pageNumber,
                                                                               Integer pageSize) {
        return new ResponseEntity<>(menuPartService
                .searchByServiceProvider(serviceProviderId, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MenuPartResponse> create(Long serviceProviderId, MenuPartRequest menuPartRequest) {
        return new ResponseEntity<>(menuPartService.create(serviceProviderId, menuPartRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MenuPartResponse> update(Long serviceProviderId, Long menuPartId, MenuPartRequest menuPartRequest) {
        return new ResponseEntity<>(menuPartService.update(serviceProviderId, menuPartId, menuPartRequest), HttpStatus.OK);
    }
}
