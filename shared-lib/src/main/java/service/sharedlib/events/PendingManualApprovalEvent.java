package service.sharedlib.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingManualApprovalEvent extends BaseEvent {
    private Long orderId;
    private Long serviceProviderId;
}
