package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.Specification;

import java.util.List;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {
    List<Specification> findAllByMenuItem_IdAndDeletedOrDeleted(
            Long menuItem, Boolean deleted, Boolean deletedNull);
}
