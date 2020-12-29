package service.sharedlib.events.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreatedItem {
    private Long menuItemId;
    private Double currentPricePerUnit;
    private Integer amount;
}
