package service.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SpecificationRequest {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String value;
}
