package service.ordering.dto;

import lombok.Getter;
import lombok.Setter;
import service.ordering.domain.enums.OrderStatus;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long serviceProviderId;
    List<BoughtItemResponse> boughtItems;
    private OrderStatus status;
    private Integer userId;
    private Integer accountId;
}
