package service.serviceprovider.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.serviceprovider.dto.SpecificationRequest;
import service.serviceprovider.dto.SpecificationResponse;

import java.util.List;

@Api
@RequestMapping("menuItems/{menuItemId}/specifications")
public interface SpecificationRestApi {

    @PostMapping("")
    @ApiOperation(value = "Create Specification under MenuItem")
    ResponseEntity<List<SpecificationResponse>> create(
            @PathVariable Long menuItemId,
            @Validated @RequestBody List<SpecificationRequest> specificationsRequest);

    @GetMapping("")
    @ApiOperation(value = "Get Specification for MenuItem")
    ResponseEntity<List<SpecificationResponse>> get(@PathVariable Long menuItemId);
}
