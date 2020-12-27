package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
