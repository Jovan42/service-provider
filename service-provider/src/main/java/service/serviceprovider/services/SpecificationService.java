package service.serviceprovider.services;

import org.springframework.stereotype.Service;
import service.serviceprovider.dto.SpecificationRequest;
import service.serviceprovider.dto.SpecificationResponse;

import java.util.List;

@Service
public interface SpecificationService {

    List<SpecificationResponse> create(Long menuItemId, List<SpecificationRequest> specificationRequest);

    List<SpecificationResponse> get(Long menuItemId);
}
