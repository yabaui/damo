package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.HealthCheckupBioAgeAnalysisGuideCrypto;
import encryption.damo.model.member.key.HealthCheckupBioAgeAnalysisGuideCryptoKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckupBioAgeAnalysisGuideCryptoRepository
        extends JpaRepository<HealthCheckupBioAgeAnalysisGuideCrypto, HealthCheckupBioAgeAnalysisGuideCryptoKey> {
    int countAllBy();
    List<HealthCheckupBioAgeAnalysisGuideCrypto> findAllBy(Pageable pageable);
}
