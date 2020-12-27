package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
}
