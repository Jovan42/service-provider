package service.ordering.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long serviceProviderId;
    @NotNull
    List<BoughtItemResponse> boughtItems;
}
