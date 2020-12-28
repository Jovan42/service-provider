package service.serviceprovider.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.MenuPart;

public interface MenuPartRepository extends JpaRepository<MenuPart, Long> {
    Page<MenuPart> findAllByServiceProvider_Id(Long organisationId, Pageable pageable);

}
