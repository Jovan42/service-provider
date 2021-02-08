package service.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.accounting.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
