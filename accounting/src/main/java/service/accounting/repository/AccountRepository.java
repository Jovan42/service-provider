package service.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.accounting.domain.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(String userId);
}
