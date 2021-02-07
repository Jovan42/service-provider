package service.sharedlib.events.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private Long menuItemId;
    private Double currentPricePerUnit;
    private Integer amount;
}
