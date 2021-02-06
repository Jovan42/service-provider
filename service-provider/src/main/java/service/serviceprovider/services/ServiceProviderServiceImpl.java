package service.serviceprovider.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.MenuItem;
import service.serviceprovider.domain.ServiceProvider;
import service.serviceprovider.dto.ServiceProviderRequest;
import service.serviceprovider.dto.ServiceProviderResponse;
import service.serviceprovider.repositories.MenuItemRepository;
import service.serviceprovider.repositories.OrganisationRepository;
import service.serviceprovider.repositories.ServiceProviderRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.events.pojo.OrderCreatedItem;
import service.sharedlib.exceptions.InvalidOrderException;
import service.sharedlib.exceptions.NotFoundException;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private final ModelMapper modelMapper;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OrganisationRepository organisationRepository;
    private final MenuItemRepository menuItemRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Autowired
    public ServiceProviderServiceImpl(
            ModelMapper modelMapper,
            ServiceProviderRepository serviceProviderRepository,
            OrganisationRepository organisationRepository,
            MenuItemRepository menuItemRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.modelMapper = modelMapper;
        this.serviceProviderRepository = serviceProviderRepository;
        this.organisationRepository = organisationRepository;
        this.menuItemRepository = menuItemRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ServiceProviderResponse create(
            Long organisationId, ServiceProviderRequest serviceProviderRequest) {
        ServiceProvider serviceProvider =
                modelMapper.map(serviceProviderRequest, ServiceProvider.class);
        serviceProvider.setCreatedTime(LocalDateTime.now());
        serviceProvider.setOrganisation(
                organisationRepository
                        .findById(organisationId)
                        .orElseThrow(NotFoundException::new));
        return modelMapper.map(
                serviceProviderRepository.save(serviceProvider), ServiceProviderResponse.class);
    }

    @Override
    public CustomPage<ServiceProviderResponse> search(Pageable pageable) {
        return new CustomPage<>(
                serviceProviderRepository
                        .findAll(pageable)
                        .map(
                                serviceProvider ->
                                        modelMapper.map(
                                                serviceProvider, ServiceProviderResponse.class)));
    }

    @Override
    public ServiceProviderResponse getById(Long serviceProviderId) {
        return modelMapper.map(
                serviceProviderRepository
                        .findById(serviceProviderId)
                        .orElseThrow(NotFoundException::new),
                ServiceProviderResponse.class);
    }

    @Override
    public ServiceProviderResponse update(
            Long organisationId,
            Long serviceProviderId,
            ServiceProviderRequest serviceProviderRequest) {
        ServiceProvider serviceProvider =
                serviceProviderRepository
                        .findById(serviceProviderId)
                        .orElseThrow(NotFoundException::new);
        serviceProvider.setLastModified(LocalDateTime.now());
        serviceProvider.setName(serviceProviderRequest.getName());
        serviceProvider.setDescription(serviceProviderRequest.getDescription());
        serviceProvider.setAddress(serviceProviderRequest.getAddress());
        serviceProvider.setOrganisation(
                organisationRepository
                        .findById(organisationId)
                        .orElseThrow(NotFoundException::new));
        return modelMapper.map(
                serviceProviderRepository.save(serviceProvider), ServiceProviderResponse.class);
    }

    @Override
    public CustomPage<ServiceProviderResponse> searchByOrganisation(
            Long organisationId, Pageable pageable) {
        return new CustomPage<>(
                serviceProviderRepository
                        .findAllByOrganisation_Id(organisationId, pageable)
                        .map(
                                serviceProvider ->
                                        modelMapper.map(
                                                serviceProvider, ServiceProviderResponse.class)));
    }

    @Override
    public Boolean validateOrder(OrderCreatedEvent orderCreatedEvent) {
        Optional<ServiceProvider> serviceProvider =
                serviceProviderRepository.findById(orderCreatedEvent.getServiceProviderId());
        if (serviceProvider.isEmpty()) {
            throw new InvalidOrderException(
                    orderCreatedEvent.getOrderId(),
                    OrderInvalidReason.NONEXISTENT_SERVICE_PROVIDER);
        }

        List<MenuItem> menuItems =
                menuItemRepository.check(
                        orderCreatedEvent.getServiceProviderId(),
                        orderCreatedEvent.getOrderCreatedItems().stream()
                                .map(OrderCreatedItem::getMenuItemId)
                                .collect(Collectors.toList()));
        if (orderCreatedEvent.getOrderCreatedItems().size() != menuItems.size()) {
            throw new InvalidOrderException(
                    orderCreatedEvent.getOrderId(),
                    OrderInvalidReason.MENU_ITEM_AND_SERVICE_PROVIDER_DOESNT_MATCH);
        }

        return true;
    }

    private void sentInvalidateEvent(Long orderId, OrderInvalidReason orderInvalidReason) {
        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderRequestDeclinedEvent.builder()
                        .orderId(orderId)
                        .reason(orderInvalidReason)
                        .build());
    }
}
