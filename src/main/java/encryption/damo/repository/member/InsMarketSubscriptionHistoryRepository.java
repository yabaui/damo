package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.InsMarketSubscriptionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsMarketSubscriptionHistoryRepository extends JpaRepository<InsMarketSubscriptionHistory, String> {
    int countAllBySsnNot(String ssn);
    List<InsMarketSubscriptionHistory> findAllBySsnNot(String ssn, Pageable pageable);
}
