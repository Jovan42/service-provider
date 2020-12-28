package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.serviceprovider.domain.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

}
