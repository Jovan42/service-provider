package service.ordering.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BoughtItemRequest {
    private Long id;
    @NotBlank private Long menuItemId;
    @NotNull private Double currentPricePerUnit;
    @NotNull private Integer amount;
}
