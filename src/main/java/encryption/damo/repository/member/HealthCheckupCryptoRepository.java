package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.HealthCheckupCrypto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckupCryptoRepository extends JpaRepository<HealthCheckupCrypto, Long> {
    List<HealthCheckupCrypto> findAllByOptionalNot(String optional, Pageable pageable);
    int countAllByOptionalNot(String optional);
}
