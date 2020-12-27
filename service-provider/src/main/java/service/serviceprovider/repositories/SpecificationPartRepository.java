package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.SpecificationPart;

public interface SpecificationPartRepository extends JpaRepository<SpecificationPart, Long> {
}
