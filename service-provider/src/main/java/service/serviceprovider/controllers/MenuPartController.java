package service.serviceprovider.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.MenuPartRequest;
import service.serviceprovider.dto.MenuPartResponse;
import service.serviceprovider.services.MenuPartService;
import service.sharedlib.dto.CustomPage;

@Controller
@RequestMapping("")
public class MenuPartController {
    private final MenuPartService menuPartService;

    public MenuPartController(MenuPartService menuPartService) {
        this.menuPartService = menuPartService;
    }

    @GetMapping("/{menuPartId}")
    private ResponseEntity<MenuPartResponse> getById(@PathVariable Long menuPartId) {
        return new ResponseEntity<>(menuPartService.getById(menuPartId), HttpStatus.OK);
    }

    @GetMapping("/serviceProviders/{serviceProviderId}/menu")
    private ResponseEntity<CustomPage<MenuPartResponse>> searchServiceProviders(@PathVariable Long serviceProviderId,
                                                                                @RequestParam Integer pageNumber,
                                                                                @RequestParam Integer pageSize) {
        return new ResponseEntity<>(menuPartService
                .searchByServiceProvider(serviceProviderId, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @PostMapping("/serviceProviders/{serviceProviderId}")
    private ResponseEntity<MenuPartResponse> create(@PathVariable Long serviceProviderId,
                                                    @Validated @RequestBody MenuPartRequest menuPartRequest) {
        return new ResponseEntity<>(menuPartService.create(serviceProviderId, menuPartRequest), HttpStatus.CREATED);
    }

    @PutMapping("/serviceProviders/{serviceProviderId}/menuParts/{menuPartId}")
    private ResponseEntity<MenuPartResponse> update(@PathVariable Long serviceProviderId,
                                                    @PathVariable Long menuPartId,
                                                    @Validated @RequestBody MenuPartRequest menuPartRequest) {
        return new ResponseEntity<>(menuPartService.update(serviceProviderId, menuPartId, menuPartRequest), HttpStatus.OK);
    }
}
