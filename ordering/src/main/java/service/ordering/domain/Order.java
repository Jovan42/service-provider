package service.ordering.domain;

import lombok.Getter;
import lombok.Setter;
import service.ordering.domain.enums.OrderStatus;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private Long serviceProviderId;
    private OrderStatus status;
    private String cancelReason;
    private Long userId;
    private Long accountId;
    private Long preparationTimeInMinutes;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BoughtItem> boughtItems;
}
