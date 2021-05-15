package service.ordering.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartOrderPreparationRequest {
    private Long preparationTimeInMinutes;
}
