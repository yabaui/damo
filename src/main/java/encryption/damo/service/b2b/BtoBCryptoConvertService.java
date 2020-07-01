package encryption.damo.service.b2b;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import encryption.damo.model.btob.InsMarketSubscriptionHistory;
import encryption.damo.model.btob.OverseasIns;
import encryption.damo.repository.btob.InsMarketSubscriptionHistoryRepository;
import encryption.damo.repository.btob.OverseasInsRepository;
import encryption.damo.utils.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Profile(value = {"dev", "stg", "btob"})
@Service
@RequiredArgsConstructor
@Slf4j
public class BtoBCryptoConvertService {
    @Value("${security.db.key}")
    private String securityDbKeyLegacy;
    @Value("${btob.security.personal.key}")
    private String securityDbKey;
    @Value("${damo.agent.path}")
    private String agentPath;
    private final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
    private final String iv = CryptoUtil.getCryptoIv(key);
    private static final String EMPTY = "empty";
    private static final String SUCCESS = "success";
    private static final String LOG_FORMAT = " / ssn : %s / d_ssn : null\n";

    private final OverseasInsRepository overseasInsRepository;
    private final InsMarketSubscriptionHistoryRepository insMarketSubscriptionHistoryRepository;

    @Transactional("transactionManagerBToB")
    public String convertOverseasIns() {
        List<OverseasIns> list = overseasInsRepository.findAll();

        if (CollectionUtils.isEmpty(list)) {
            return EMPTY;
        }

        StringBuilder builder = new StringBuilder();

        for (OverseasIns overseasIns : list) {
            if (StringUtils.isEmpty(overseasIns.getSsn())) {
                continue;
            }

            String ssn = CryptoUtil.aesDecryption(overseasIns.getSsn(), key, iv);

            if (Objects.isNull(ssn)) {
                builder.append("id : ").append(overseasIns.getId()).append(String.format(LOG_FORMAT, overseasIns.getSsn()));
            } else {
                overseasIns.setSsn(CryptoUtil.encryptionBase64(ssn, securityDbKey, agentPath));
            }
        }

        overseasInsRepository.saveAll(list);

        if (StringUtils.isEmpty(builder.toString())) {
            return SUCCESS;
        }

        return builder.toString();
    }

    @Transactional("transactionManagerBToB")
    public String convertInsMarketSubscriptionHistory() {
        List<InsMarketSubscriptionHistory> list = insMarketSubscriptionHistoryRepository.findAll();

        if (CollectionUtils.isEmpty(list)) {
            return EMPTY;
        }

        StringBuilder builder = new StringBuilder();

        for (InsMarketSubscriptionHistory history : list) {
            if (StringUtils.isEmpty(history.getSsn())) {
                continue;
            }

            String ssn = CryptoUtil.aesDecryption(history.getSsn(), key, iv);

            if (Objects.isNull(ssn)) {
                builder.append("transaction_no : ").append(history.getTransactionNo())
                        .append(String.format(LOG_FORMAT, history.getSsn()));
            } else {
                history.setSsn(CryptoUtil.encryptionBase64(ssn, securityDbKey, agentPath));
            }
        }

        insMarketSubscriptionHistoryRepository.saveAll(list);

        if (StringUtils.isEmpty(builder.toString())) {
            return SUCCESS;
        }

        return builder.toString();
    }

    @Transactional("transactionManagerBToB")
    public String updateInsMarketSubscriptionHistory() {
        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.of(2020,5,1,0,0,0));
        List<InsMarketSubscriptionHistory> list = insMarketSubscriptionHistoryRepository.findAllByCreatedDateGreaterThanEqual(createdAt);

        if (CollectionUtils.isEmpty(list)) {
            return EMPTY;
        }

        StringBuilder builder = new StringBuilder();

        for (InsMarketSubscriptionHistory history : list) {
            if (StringUtils.isEmpty(history.getSsn())) {
                continue;
            }

            String ssn = this.decrypted(history.getSsn());

            if (Objects.isNull(ssn)) {
                builder.append("transaction_no : ")
                        .append(history.getTransactionNo())
                        .append(" / ssn : ")
                        .append(history.getSsn())
                        .append(" / d_ssn : ")
                        .append(ssn)
                        .append("\n");
            } else {
                history.setSsn(CryptoUtil.aesEncryption(ssn, key, iv));
            }
        }

        insMarketSubscriptionHistoryRepository.saveAll(list);

        if (StringUtils.isEmpty(builder.toString())) {
            return SUCCESS;
        }

        return builder.toString();
    }

    private String decrypted(String value) {
        final String stgKey = "ce545558-fcc0-4aa4-adc2-06257c8d26c8";
        final String prodKey = "754aa6dd-bcd4-4e06-a95f-9bcc3dd407aa";
        final String memberKey = "64ddc7b5-9aef-4d7a-99b2-65977fbe41cc";

        String decryptedValue = CryptoUtil.aesDecryption(value, key, iv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        String privateKey = CryptoUtil.getCryptoKey(stgKey);
        String privateIv = CryptoUtil.getCryptoIv(privateKey);

        decryptedValue = CryptoUtil.aesDecryption(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        privateKey = CryptoUtil.getCryptoKey(prodKey);
        privateIv = CryptoUtil.getCryptoIv(privateKey);

        decryptedValue = CryptoUtil.aesDecryption(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        privateKey = CryptoUtil.getCryptoKey(memberKey);
        privateIv = CryptoUtil.getCryptoIv(privateKey);

        return CryptoUtil.aesDecryption(value, privateKey, privateIv);
    }
}
