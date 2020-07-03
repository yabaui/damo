package encryption.damo.repository.btob;

import java.sql.Timestamp;
import java.util.List;

import encryption.damo.model.btob.InsMarketSubscriptionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsMarketSubscriptionHistoryRepository extends JpaRepository<InsMarketSubscriptionHistory, String> {
    List<InsMarketSubscriptionHistory> findAllByCreatedDateGreaterThanEqual(Timestamp createdDate);
    InsMarketSubscriptionHistory findByTransactionNo(String transactionNo);
    int countAllBySsnNot(String ssn);
    List<InsMarketSubscriptionHistory> findAllBySsnNot(String ssn, Pageable pageable);
}
