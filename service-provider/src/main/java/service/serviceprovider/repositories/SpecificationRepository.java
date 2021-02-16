package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.serviceprovider.domain.Specification;

import java.util.List;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {
    List<Specification> findAllByMenuItem_IdAndDeletedOrDeleted(
            Long menuItem, Boolean deleted, Boolean deletedNull);

    @Query("select s from Specification s where s.menuItem.id = :menuItem AND (s.deleted = false OR s.deleted = null)")
    List<Specification> findAllByMenuItem(Long menuItem);
}
