package service.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoughtItemResponse {
    private Long id;
    private Long menuItemId;
    private Double currentPricePerUnit;
    private Integer amount;
}
