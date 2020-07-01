package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.FamilyHealthCheckupCrypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyHealthCheckupCryptoRepository extends JpaRepository<FamilyHealthCheckupCrypto, Long> {
    List<FamilyHealthCheckupCrypto> findAllByRiskScoreNot(String riskScore);
}
