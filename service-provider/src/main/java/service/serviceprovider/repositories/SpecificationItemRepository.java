package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.SpecificationItem;

public interface SpecificationItemRepository extends JpaRepository<SpecificationItem, Long> {
}
