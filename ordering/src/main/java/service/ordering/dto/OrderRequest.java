package service.ordering.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long id;
    @NotNull private Long serviceProviderId;
    @NotNull @NotEmpty List<BoughtItemRequest> boughtItems;
    @NotNull private Integer userId;
    @NotNull private Integer accountId;
}
