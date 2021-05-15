package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountValidationFinishedEvent extends BaseEvent {
    private Long orderId;
    private Long userId;
    private Long accountId;
    private Double price;
}
