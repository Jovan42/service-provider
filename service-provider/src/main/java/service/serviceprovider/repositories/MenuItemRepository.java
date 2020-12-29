package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import service.serviceprovider.domain.MenuItem;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query(
            "select mi from MenuItem mi where mi.menuPart.serviceProvider.id = :serviceProviderId and mi.id in :menuPartIds")
    List<MenuItem> check(
            @Param("serviceProviderId") Long serviceProviderId,
            @Param("menuPartIds") List<Long> menuPartIds);
}
