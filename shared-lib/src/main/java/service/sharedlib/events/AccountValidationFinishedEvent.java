package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountValidationFinishedEvent extends BaseEvent {
    private Long orderId;
    private String userId;
    private Long accountId;
    private Double price;
}
