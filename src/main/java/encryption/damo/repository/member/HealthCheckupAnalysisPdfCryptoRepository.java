package encryption.damo.repository.member;

import java.util.List;

import encryption.damo.model.member.HealthCheckupAnalysisPdfCrypto;
import encryption.damo.model.member.key.HealthCheckupAnalysisPdfCryptoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckupAnalysisPdfCryptoRepository
        extends JpaRepository<HealthCheckupAnalysisPdfCrypto, HealthCheckupAnalysisPdfCryptoKey> {
    List<HealthCheckupAnalysisPdfCrypto> findAllByPdfPathNot(String pdfPath);
}
