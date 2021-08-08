package service.delivery.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import service.delivery.domain.DeliveryMan;
import service.delivery.dto.DeliveryManRequest;
import service.delivery.dto.DeliveryManResponse;
import service.delivery.repository.DeliveryManRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryManServiceImpl implements DeliveryManService {
    private final DeliveryManRepository deliveryManRepository;
    private final ModelMapper modelMapper;

    public DeliveryManServiceImpl(
            DeliveryManRepository deliveryManRepository, ModelMapper modelMapper) {
        this.deliveryManRepository = deliveryManRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DeliveryManResponse create(DeliveryManRequest deliveryManRequest) {
        DeliveryMan deliveryMan = modelMapper.map(deliveryManRequest, DeliveryMan.class);
        deliveryMan.setCreatedTime(LocalDateTime.now());
        deliveryMan.setId(0L);
        return modelMapper.map(deliveryManRepository.save(deliveryMan), DeliveryManResponse.class);
    }

    @Override
    public List<DeliveryManResponse> getAll() {
        return deliveryManRepository.findAll().stream()
                .map(deliveryMan -> modelMapper.map(deliveryManRepository.save(deliveryMan), DeliveryManResponse.class))
                .collect(Collectors.toList());
    }
}
