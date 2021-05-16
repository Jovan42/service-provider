package service.ordering.repostiroy;

import org.springframework.data.jpa.repository.JpaRepository;
import service.ordering.domain.BoughtItem;

public interface BoughtItemsRepository extends JpaRepository<BoughtItem, Long> {}
