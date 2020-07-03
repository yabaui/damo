package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.FamilyHealthCheckupCrypto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyHealthCheckupCryptoRepository extends JpaRepository<FamilyHealthCheckupCrypto, Long> {
    int countAllByRiskScoreNot(String riskScore);
    List<FamilyHealthCheckupCrypto> findAllByRiskScoreNot(String riskScore, Pageable pageable);
}
