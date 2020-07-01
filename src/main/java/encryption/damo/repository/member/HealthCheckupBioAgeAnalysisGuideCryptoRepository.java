package encryption.damo.repository.member;

import encryption.damo.model.member.HealthCheckupBioAgeAnalysisGuideCrypto;
import encryption.damo.model.member.key.HealthCheckupBioAgeAnalysisGuideCryptoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckupBioAgeAnalysisGuideCryptoRepository
        extends JpaRepository<HealthCheckupBioAgeAnalysisGuideCrypto, HealthCheckupBioAgeAnalysisGuideCryptoKey> {
}
