package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.HealthCheckupDetailCrypto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckupDetailCryptoRepository extends JpaRepository<HealthCheckupDetailCrypto, Long> {
    int countAllBy();
    List<HealthCheckupDetailCrypto> findAllBy(Pageable pageable);
}
