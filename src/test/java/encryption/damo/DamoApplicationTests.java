package encryption.damo;

import java.util.List;

import encryption.damo.model.member.FamilyHealthCheckupCrypto;
import encryption.damo.repository.member.FamilyHealthCheckupCryptoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DamoApplicationTests {

	@Autowired
	FamilyHealthCheckupCryptoRepository familyHealthCheckupCryptoRepository;

	@Test
	public void contextLoads() {
		List<FamilyHealthCheckupCrypto> list = familyHealthCheckupCryptoRepository.findAll();

		for (FamilyHealthCheckupCrypto crypto : list) {
			log.info(crypto.getRiskScore());
		}
	}

}
