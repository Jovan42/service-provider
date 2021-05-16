package service.delivery.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import service.delivery.domain.Delivery;
import service.delivery.dto.DeliveryResponse;

@Component
public class Mappers {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TypeMap<Delivery, DeliveryResponse> deliveryMapper() {
        return new ModelMapper()
                .createTypeMap(Delivery.class, DeliveryResponse.class)
                .addMapping(
                        delivery -> delivery.getDeliveryMan().getId(),
                        DeliveryResponse::setDeliveryManId);
    }
}
