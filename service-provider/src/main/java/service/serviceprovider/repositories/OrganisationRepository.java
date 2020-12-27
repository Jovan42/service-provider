package service.serviceprovider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.serviceprovider.domain.Organisation;

import java.util.List;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

}
