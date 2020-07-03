package encryption.damo.repository.btob;

import java.util.List;

import encryption.damo.model.btob.OverseasIns;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OverseasInsRepository extends JpaRepository<OverseasIns, Long> {
    int countAllBySsnNot(String ssn);
    List<OverseasIns> findAllBySsnNot(String ssn, Pageable pageable);
}
