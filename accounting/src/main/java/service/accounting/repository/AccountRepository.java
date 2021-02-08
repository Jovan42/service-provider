package service.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.accounting.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
