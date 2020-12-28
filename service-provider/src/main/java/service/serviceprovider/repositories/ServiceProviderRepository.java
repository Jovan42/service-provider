package service.serviceprovider.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    Page<ServiceProvider> findAllByOrganisation_Id(Long organisationId, Pageable pageable);
}
