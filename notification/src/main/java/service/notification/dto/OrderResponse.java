package service.notification.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long serviceProviderId;
    List<BoughtItemResponse> boughtItems;
    private String status;
    private Integer userId;
    private Integer accountId;
}
