package service.serviceprovider.services;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import service.serviceprovider.domain.MenuItem;
import service.serviceprovider.domain.ServiceProvider;
import service.serviceprovider.dto.ServiceProviderRequest;
import service.serviceprovider.dto.ServiceProviderResponse;
import service.serviceprovider.repositories.MenuItemRepository;
import service.serviceprovider.repositories.MenuPartRepository;
import service.serviceprovider.repositories.OrganisationRepository;
import service.serviceprovider.repositories.ServiceProviderRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderCreatedEvent;
import service.sharedlib.events.OrderItemsApprovedEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.events.pojo.OrderItem;
import service.sharedlib.exceptions.NotFoundException;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private final ModelMapper modelMapper;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OrganisationRepository organisationRepository;
    private final MenuItemRepository menuItemRepository;
    private final MenuPartRepository menuPartRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Autowired
    public ServiceProviderServiceImpl(
            ModelMapper modelMapper,
            ServiceProviderRepository serviceProviderRepository,
            OrganisationRepository organisationRepository,
            MenuItemRepository menuItemRepository,
            MenuPartRepository menuPartRepository, KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.modelMapper = modelMapper;
        this.serviceProviderRepository = serviceProviderRepository;
        this.organisationRepository = organisationRepository;
        this.menuItemRepository = menuItemRepository;
        this.menuPartRepository = menuPartRepository;
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
        ServiceProvider serviceProvider = serviceProviderRepository
                .findById(serviceProviderId)
                .orElseThrow(NotFoundException::new);
        serviceProvider.setMenuParts(menuPartRepository.findAllByServiceProvider_Id(serviceProviderId));
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        return modelMapper.map(
                serviceProvider,
                ServiceProviderResponse.class);
    }

    @Override
    public ServiceProviderResponse update(
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
    public void validateOrder(OrderCreatedEvent orderCreatedEvent) {
        Optional<ServiceProvider> serviceProvider =
                serviceProviderRepository.findById(orderCreatedEvent.getServiceProviderId());
        if (serviceProvider.isEmpty()) {
            sentInvalidateEvent(orderCreatedEvent.getOrderId(),
                    OrderInvalidReason.NONEXISTENT_SERVICE_PROVIDER);
            return;
        }

        List<MenuItem> menuItems =
                menuItemRepository.check(
                        orderCreatedEvent.getServiceProviderId(),
                        orderCreatedEvent.getOrderItems().stream()
                                .map(OrderItem::getMenuItemId)
                                .collect(Collectors.toList()));
        if (orderCreatedEvent.getOrderItems().size() != menuItems.size()) {
            sentInvalidateEvent(orderCreatedEvent.getOrderId(),
                    OrderInvalidReason.MENU_ITEM_AND_SERVICE_PROVIDER_DOESNT_MATCH);
            return;
        }
        kafkaTemplate.send(
                "orderTopic",
                "",
                OrderItemsApprovedEvent.builder()
                        .orderId(orderCreatedEvent.getOrderId())
                        .manualApprovalRequired(serviceProvider.get().getManualApprovalRequired() != null ?
                                serviceProvider.get().getManualApprovalRequired() : true)
                        .orderItems(getOrderItems(orderCreatedEvent.getOrderItems()))
                        .build());
    }

    private Map<Long, OrderItem> getOrderItems(List<OrderItem> orderItems) {
        Map<Long, OrderItem> orderMap = new HashMap<>();
        orderItems.forEach(
                item -> {
                    Optional<MenuItem> menuItem = menuItemRepository.findById(item.getMenuItemId());
                    menuItem.ifPresent(value -> item.setCurrentPricePerUnit(value.getPrice()));
                    orderMap.put(item.getMenuItemId(), item);
                });
        return orderMap;
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
