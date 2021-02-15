package service.serviceprovider.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import service.serviceprovider.controllers.api.SpecificationRestApi;
import service.serviceprovider.dto.SpecificationRequest;
import service.serviceprovider.dto.SpecificationResponse;
import service.serviceprovider.services.SpecificationService;

import java.util.List;

@Controller
public class SpecificationController implements SpecificationRestApi {
    private final SpecificationService specificationService;

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    @Override
    public ResponseEntity<List<SpecificationResponse>> create(
            Long menuItemId, List<SpecificationRequest> specificationsRequest) {
        return new ResponseEntity<>(
                specificationService.create(menuItemId, specificationsRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<SpecificationResponse>> get(Long menuItemId) {
        return new ResponseEntity<>(
                specificationService.get(menuItemId), HttpStatus.OK);
    }
}
