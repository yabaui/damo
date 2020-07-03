package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.HealthCheckupBioAgeLifeExpectancyCrypto;
import encryption.damo.model.member.key.HealthCheckupBioAgeLifeExpectancyCryptoKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckupBioAgeLifeExpectancyCryptoRepository
        extends JpaRepository<HealthCheckupBioAgeLifeExpectancyCrypto, HealthCheckupBioAgeLifeExpectancyCryptoKey> {
    int countAllBy();
    List<HealthCheckupBioAgeLifeExpectancyCrypto> findAllBy(Pageable pageable);
}
