package encryption.damo.service.b2b;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import encryption.damo.enums.ResponseCodes;
import encryption.damo.exception.IntegrationException;
import encryption.damo.model.btob.InsMarketSubscriptionHistory;
import encryption.damo.model.btob.OverseasIns;
import encryption.damo.repository.btob.InsMarketSubscriptionHistoryRepository;
import encryption.damo.repository.btob.OverseasInsRepository;
import encryption.damo.utils.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
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
    private static final String EMPTY = "empty";
    private static final String SUCCESS = "success";
    private static final String LOG_FORMAT = " / ssn : %s / d_ssn : null\n";
    private static final String EMPTY_STRING = "";
    private static final int SIZE = 5000;

    private final OverseasInsRepository overseasInsRepository;
    private final InsMarketSubscriptionHistoryRepository insMarketSubscriptionHistoryRepository;

    @Async("threadPoolTaskExecutor")
    @Transactional("transactionManagerBToB")
    public void convertOverseasIns() {
        int page = 0;

        int totalCount = overseasInsRepository.countAllBySsnNot(EMPTY_STRING);

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<OverseasIns> list = overseasInsRepository.findAllBySsnNot(EMPTY_STRING, PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                return;
            }

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

            if (!StringUtils.isEmpty(builder)) {
                throw new IntegrationException(ResponseCodes.FAIL, builder.toString());
            }

            overseasInsRepository.saveAll(list);
            overseasInsRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        log.info(SUCCESS);
    }

    @Async("threadPoolTaskExecutor")
    @Transactional("transactionManagerBToB")
    public void convertInsMarketSubscriptionHistory() {
        int page = 0;

        int totalCount = insMarketSubscriptionHistoryRepository.countAllBySsnNot(EMPTY_STRING);

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<InsMarketSubscriptionHistory> list =
                    insMarketSubscriptionHistoryRepository.findAllBySsnNot(EMPTY_STRING, PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                return;
            }

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

            if (!StringUtils.isEmpty(builder)) {
                throw new IntegrationException(ResponseCodes.FAIL, builder.toString());
            }

            insMarketSubscriptionHistoryRepository.saveAll(list);
            insMarketSubscriptionHistoryRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        log.info(SUCCESS);
    }

    @Transactional("transactionManagerBToB")
    public String updateInsMarketSubscriptionHistory() {
        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.of(2020, 5, 1, 0, 0, 0));
        List<InsMarketSubscriptionHistory> list = insMarketSubscriptionHistoryRepository.findAllByCreatedDateGreaterThanEqual(createdAt);

        if (CollectionUtils.isEmpty(list)) {
            return EMPTY;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);

        StringBuilder builder = new StringBuilder();
        StringBuilder decryptionBuilder = new StringBuilder();

        for (InsMarketSubscriptionHistory history : list) {
            if (StringUtils.isEmpty(history.getSsn())) {
                continue;
            }

//            String ssn = CryptoUtil.aesDecryption(history.getSsn(), key, iv);
            String ssn = this.decrypted(history.getSsn());

            if (Objects.isNull(ssn)) {
                builder.append("transaction_no : ")
                        .append(history.getTransactionNo())
                        .append(" / ssn : ")
                        .append(history.getSsn())
                        .append(" / d_ssn : ")
                        .append(ssn)
                        .append("\n");
                continue;
            }

            decryptionBuilder.append("transaction_no : ")
                    .append(history.getTransactionNo())
                    .append(" / ssn : ")
                    .append(history.getSsn())
                    .append(" / d_ssn : ")
                    .append(ssn)
                    .append("\n");

//            history.setSsn(CryptoUtil.aesEncryption(ssn, key, iv));
        }

        if (!StringUtils.isEmpty(builder.toString())) {
            return builder.toString();
        }

//        insMarketSubscriptionHistoryRepository.saveAll(list);

        return decryptionBuilder.toString();
    }

    private String decrypted(String value) {
        final String devKey = "54204599-a1fa-4c10-96a8-99af0d38f8f6";
        final String stgKey = "ce545558-fcc0-4aa4-adc2-06257c8d26c8";
        final String prodKey = "754aa6dd-bcd4-4e06-a95f-9bcc3dd407aa";
        final String memberKey = "64ddc7b5-9aef-4d7a-99b2-65977fbe41cc";

        String privateKey = CryptoUtil.getCryptoKey(devKey);
        String privateIv = CryptoUtil.getCryptoIv(privateKey);

        String decryptedValue = CryptoUtil.aesDecryption(value, privateKey, privateIv);

        if (Objects.nonNull(decryptedValue)) {
            return decryptedValue;
        }

        privateKey = CryptoUtil.getCryptoKey(stgKey);
        privateIv = CryptoUtil.getCryptoIv(privateKey);

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
