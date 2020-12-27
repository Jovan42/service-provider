package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.MenuPart;

public interface MenuPartRepository extends JpaRepository<MenuPart, Long> {
}
