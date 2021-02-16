package service.serviceprovider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.serviceprovider.controllers.api.MenuItemRestApi;
import service.serviceprovider.dto.MenuItemRequest;
import service.serviceprovider.dto.MenuItemResponse;
import service.serviceprovider.services.MenuItemService;
import service.sharedlib.dto.CustomPage;

@Controller
public class MenuItemController implements MenuItemRestApi {
    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @Override
    public ResponseEntity<MenuItemResponse> getById(Long menuItemId) {
        return new ResponseEntity<>(menuItemService.getById(menuItemId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomPage<MenuItemResponse>> search(
            Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<MenuItemResponse> create(Long menuPartId, MenuItemRequest menuItemRequest) {
        return new ResponseEntity<>(menuItemService.create(menuPartId, menuItemRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MenuItemResponse> update(Long menuItem, MenuItemRequest menuItemRequest) {
        return null;
    }
}
