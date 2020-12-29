package service.sharedlib.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidOrderException extends RuntimeException {
    private Long orderId;
    private OrderInvalidReason reason;
}
