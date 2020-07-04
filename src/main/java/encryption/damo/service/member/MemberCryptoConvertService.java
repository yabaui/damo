package encryption.damo.service.member;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import encryption.damo.enums.ResponseCodes;
import encryption.damo.exception.IntegrationException;
import encryption.damo.model.member.*;
import encryption.damo.repository.member.*;
import encryption.damo.utils.CryptoUtil;
import encryption.damo.utils.DAmoStringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Profile(value = {"dev", "stg", "member"})
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberCryptoConvertService {
    @Value("${security.db.key}")
    private String securityDbKeyLegacy;
    @Value("${member.security.personal.key}")
    private String securityDbKey;
    @Value("${damo.agent.path}")
    private String agentPath;

    private static final String EMPTY = ">>>>>>>> empty";
    private static final String SUCCESS = ">>>>>>>> success";
    private static final String EMPTY_STRING = "";
    private static final String ID_FORMAT = "id : %s\n";
    private static final String CHECKUP_DATE_FORMAT = "mb_uid : %d / checkup_date : %s\n";
    private static final int SIZE = 5000;
    private static final String ENCRYPTION_FAIL_FORMAT = ">>>>>>Method : %s EncryptionFail List\n";

    private final FamilyHealthCheckupCryptoRepository familyHealthCheckupCryptoRepository;
    private final HealthCheckupCryptoRepository healthCheckupCryptoRepository;
    private final HealthCheckupDetailCryptoRepository healthCheckupDetailCryptoRepository;
    private final HealthCheckupAnalysisPdfCryptoRepository healthCheckupAnalysisPdfCryptoRepository;
    private final HealthCheckupBioAgeAnalysisGuideCryptoRepository healthCheckupBioAgeAnalysisGuideCryptoRepository;
    private final HealthCheckupBioAgeLifeExpectancyCryptoRepository healthCheckupBioAgeLifeExpectancyCryptoRepository;
    private final InsMarketSubscriptionHistoryRepository insMarketSubscriptionHistoryRepository;

    @Async("threadPoolTaskExecutor")
    @Transactional(value = "transactionManagerMember")
    public void convertHealthCheckupCrypto() {
        final String method = "convertHealthCheckupCrypto";
        int page = 0;

        int totalCount = healthCheckupCryptoRepository.countAllByOptionalNot(EMPTY_STRING);

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<HealthCheckupCrypto> list =
                    healthCheckupCryptoRepository.findAllByOptionalNot(EMPTY_STRING, PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                break;
            }

            for (HealthCheckupCrypto crypto : list) {
                String optional = CryptoUtil.aesDecryption(crypto.getOptional(), key, iv);

                if (Objects.isNull(optional)) {
                    builder.append(String.format(ID_FORMAT, crypto.getUid()));
                    continue;
                }

                crypto.setOptional(CryptoUtil.encryptionBase64(optional, securityDbKey, agentPath));
            }

            healthCheckupCryptoRepository.saveAll(list);
            healthCheckupCryptoRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Async("threadPoolTaskExecutor")
    @Transactional(value = "transactionManagerMember")
    public void convertFamilyHeathCheckupCrypto() {
        final String method = "convertFamilyHeathCheckupCrypto";
        int page = 0;

        int totalCount = familyHealthCheckupCryptoRepository.countAllByRiskScoreNot(EMPTY_STRING);

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<FamilyHealthCheckupCrypto> list =
                    familyHealthCheckupCryptoRepository.findAllByRiskScoreNot(EMPTY_STRING, PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                break;
            }

            for (FamilyHealthCheckupCrypto crypto : list) {
                String riskScore = CryptoUtil.aesDecryption(crypto.getRiskScore(), key, iv);

                if (Objects.isNull(riskScore)) {
                    builder.append(String.format(ID_FORMAT, crypto.getUid()));
                    continue;
                }

                crypto.setRiskScore(CryptoUtil.encryptionBase64(riskScore, securityDbKey, agentPath));
            }

            familyHealthCheckupCryptoRepository.saveAll(list);
            familyHealthCheckupCryptoRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Async("threadPoolTaskExecutor")
    public void convertHealthCheckupDetailCrypto() {
        final String method = "convertHealthCheckupDetailCrypto";
        int page = 0;

        int totalCount = healthCheckupDetailCryptoRepository.countAllBy();

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            if (!this.updateHealthCheckupDetailCrypto(page, method, key, iv, builder)) {
                break;
            }

            page++;
            totalCount -= SIZE;

        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Transactional(value = "transactionManagerMember", propagation = Propagation.REQUIRES_NEW)
    public boolean updateHealthCheckupDetailCrypto(int page, String method, String key, String iv, StringBuilder builder) {
        List<HealthCheckupDetailCrypto> list = healthCheckupDetailCryptoRepository.findAllBy(PageRequest.of(page, SIZE));

        if (CollectionUtils.isEmpty(list)) {
            log.info(EMPTY);
            return false;
        }

        for (HealthCheckupDetailCrypto crypto : list) {
            if (!StringUtils.isEmpty(crypto.getCheckupValue())) {
                String checkupValue = CryptoUtil.aesDecryption(crypto.getCheckupValue(), key, iv);

                if (Objects.isNull(checkupValue)) {
                    builder.append(String.format(ID_FORMAT, crypto.getUid()));
                    continue;
                }

                crypto.setCheckupValue(CryptoUtil.encryptionBase64(checkupValue, securityDbKey, agentPath));
            }

            if (!StringUtils.isEmpty(crypto.getTargetDiseases())) {
                String targetDiseases = CryptoUtil.aesDecryption(crypto.getTargetDiseases(), key, iv);

                if (Objects.isNull(targetDiseases)) {
                    builder.append(String.format(ID_FORMAT, crypto.getUid()));
                    continue;
                }

                crypto.setTargetDiseases(CryptoUtil.encryptionBase64(targetDiseases, securityDbKey, agentPath));
            }

            if (!StringUtils.isEmpty(crypto.getTargetDiseasesKey())) {
                String targetDiseasesKey = CryptoUtil.aesDecryption(crypto.getTargetDiseasesKey(), key, iv);
                if (Objects.isNull(targetDiseasesKey)) {
                    builder.append(String.format(ID_FORMAT, crypto.getUid()));
                    continue;
                }

                crypto.setTargetDiseasesKey(CryptoUtil.encryptionBase64(targetDiseasesKey, securityDbKey, agentPath));
            }
        }

        healthCheckupDetailCryptoRepository.saveAll(list);
        healthCheckupDetailCryptoRepository.flush();

        return true;
    }

    @Async("threadPoolTaskExecutor")
    @Transactional(value = "transactionManagerMember")
    public void convertHealthCheckupAnalysisPdfCrypto() {
        final String method = "convertHealthCheckupAnalysisPdfCrypto";
        int page = 0;

        int totalCount = healthCheckupAnalysisPdfCryptoRepository.countAllByPdfPathNot(EMPTY_STRING);

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<HealthCheckupAnalysisPdfCrypto> list =
                    healthCheckupAnalysisPdfCryptoRepository.findAllByPdfPathNot(EMPTY_STRING, PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                break;
            }

            for (HealthCheckupAnalysisPdfCrypto crypto : list) {
                String pdfPath = CryptoUtil.aesDecryption(crypto.getPdfPath(), key, iv);

                if (Objects.isNull(pdfPath)) {
                    builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                    continue;
                }

                crypto.setPdfPath(CryptoUtil.encryptionBase64(pdfPath, securityDbKey, agentPath));
            }

            healthCheckupAnalysisPdfCryptoRepository.saveAll(list);
            healthCheckupAnalysisPdfCryptoRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Async("threadPoolTaskExecutor")
    @Transactional(value = "transactionManagerMember")
    public void convertHealthCheckupBioAgeAnalysisGuideCrypto() {
        final String method = "convertHealthCheckupBioAgeAnalysisGuideCrypto";
        int page = 0;

        int totalCount = healthCheckupBioAgeAnalysisGuideCryptoRepository.countAllBy();

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<HealthCheckupBioAgeAnalysisGuideCrypto> list =
                    healthCheckupBioAgeAnalysisGuideCryptoRepository.findAllBy(PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                break;
            }

            for (HealthCheckupBioAgeAnalysisGuideCrypto crypto : list) {
                if (!StringUtils.isEmpty(crypto.getBioAge())) {
                    String bioAge = CryptoUtil.aesDecryption(crypto.getBioAge(), key, iv);
                    if (Objects.isNull(bioAge)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setBioAge(CryptoUtil.encryptionBase64(bioAge, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getTotalGuide())) {
                    String totalGuide = CryptoUtil.aesDecryption(crypto.getTotalGuide(), key, iv);
                    if (Objects.isNull(totalGuide)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setTotalGuide(CryptoUtil.encryptionBase64(totalGuide, securityDbKey, agentPath));
                }
            }

            healthCheckupBioAgeAnalysisGuideCryptoRepository.saveAll(list);
            healthCheckupBioAgeAnalysisGuideCryptoRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Async("threadPoolTaskExecutor")
    @Transactional(value = "transactionManagerMember")
    public void convertHealthCheckupBioAgeLifeExpectancyCrypto() {
        final String method = "convertHealthCheckupBioAgeLifeExpectancyCrypto";
        int page = 0;

        int totalCount = healthCheckupBioAgeLifeExpectancyCryptoRepository.countAllBy();

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
        final String iv = CryptoUtil.getCryptoIv(key);
        StringBuilder builder = new StringBuilder();

        do {
            List<HealthCheckupBioAgeLifeExpectancyCrypto> list =
                    healthCheckupBioAgeLifeExpectancyCryptoRepository.findAllBy(PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                break;
            }

            for (HealthCheckupBioAgeLifeExpectancyCrypto crypto : list) {
                if (!StringUtils.isEmpty(crypto.getAgingIndex())) {
                    String agingIndex = CryptoUtil.aesDecryption(crypto.getAgingIndex(), key, iv);
                    if (Objects.isNull(agingIndex)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setAgingIndex(CryptoUtil.encryptionBase64(agingIndex, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getAgingRank())) {
                    String agingRank = CryptoUtil.aesDecryption(crypto.getAgingRank(), key, iv);
                    if (Objects.isNull(agingRank)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setAgingRank(CryptoUtil.encryptionBase64(agingRank, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getTle())) {
                    String tle = CryptoUtil.aesDecryption(crypto.getTle(), key, iv);
                    if (Objects.isNull(tle)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setTle(CryptoUtil.encryptionBase64(tle, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getTleAvg())) {
                    String tleAvg = CryptoUtil.aesDecryption(crypto.getTleAvg(), key, iv);
                    if (Objects.isNull(tleAvg)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setTleAvg(CryptoUtil.encryptionBase64(tleAvg, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getCaa())) {
                    String caa = CryptoUtil.aesDecryption(crypto.getCaa(), key, iv);
                    if (Objects.isNull(caa)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setCaa(CryptoUtil.encryptionBase64(caa, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getHea())) {
                    String hea = CryptoUtil.aesDecryption(crypto.getHea(), key, iv);
                    if (Objects.isNull(hea)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setHea(CryptoUtil.encryptionBase64(hea, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getPaa())) {
                    String paa = CryptoUtil.aesDecryption(crypto.getPaa(), key, iv);
                    if (Objects.isNull(paa)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setPaa(CryptoUtil.encryptionBase64(paa, securityDbKey, agentPath));
                }

                if (!StringUtils.isEmpty(crypto.getRea())) {
                    String rea = CryptoUtil.aesDecryption(crypto.getRea(), key, iv);
                    if (Objects.isNull(rea)) {
                        builder.append(String.format(CHECKUP_DATE_FORMAT, crypto.getMbUid(), crypto.getCheckupDate()));
                        continue;
                    }
                    crypto.setRea(CryptoUtil.encryptionBase64(rea, securityDbKey, agentPath));
                }
            }

            healthCheckupBioAgeLifeExpectancyCryptoRepository.saveAll(list);
            healthCheckupBioAgeLifeExpectancyCryptoRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Async("threadPoolTaskExecutor")
    @Transactional(value = "transactionManagerMember")
    public void convertInsMarketSubscriptionHistory() {
        final String method = "convertInsMarketSubscriptionHistory";
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
                break;
            }

            for (InsMarketSubscriptionHistory history : list) {
                String ssn = CryptoUtil.aesDecryption(history.getSsn(), key, iv);

                if (Objects.isNull(ssn)) {
                    builder.append("transaction_no : ").append(history.getTransactionNo()).append("\n");
                    continue;
                }

                history.setSsn(CryptoUtil.encryptionBase64(ssn, securityDbKey, agentPath));
            }

            insMarketSubscriptionHistoryRepository.saveAll(list);
            insMarketSubscriptionHistoryRepository.flush();
            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(String.format(ENCRYPTION_FAIL_FORMAT, method) + builder.toString());
        }

        log.info(SUCCESS);
    }

    @Transactional(value = "transactionManagerMember")
    public String decryptLegacyInsMarketSubscriptionHistory(String transactionNo) {
        Optional<InsMarketSubscriptionHistory> history = insMarketSubscriptionHistoryRepository.findById(transactionNo);

        if (!history.isPresent()) {
            throw new IntegrationException(ResponseCodes.FAIL, "not found");
        }

        String ssn = CryptoUtil.decryptedUTF8(history.get().getSsn());

        if (!DAmoStringUtils.isNumeric(ssn)) {
            ssn = CryptoUtil.decryptedEUCKR(history.get().getSsn());
        }

        if (Objects.nonNull(ssn)) {
            final String key = CryptoUtil.getCryptoKey(securityDbKeyLegacy);
            final String iv = CryptoUtil.getCryptoIv(key);

            history.get().setSsn(CryptoUtil.aesEncryption(ssn, key, iv));
        }

        return ssn;
    }

    @Transactional(value = "transactionManagerMember")
    public void verifyInsMarketSubscriptionHistory() {
        int page = 0;

        int totalCount = insMarketSubscriptionHistoryRepository.countAllBySsnNot(EMPTY_STRING);

        if (totalCount == page) {
            log.info(EMPTY);
            return;
        }

        StringBuilder builder = new StringBuilder();

        do {
            List<InsMarketSubscriptionHistory> list =
                    insMarketSubscriptionHistoryRepository.findAllBySsnNot(EMPTY_STRING, PageRequest.of(page, SIZE));

            if (CollectionUtils.isEmpty(list)) {
                log.info(EMPTY);
                break;
            }

            for (InsMarketSubscriptionHistory history : list) {
                String ssn = CryptoUtil.decryptionBase64(history.getSsn(), securityDbKey, agentPath);

                if (Objects.isNull(ssn)) {
                    builder.append("transaction_no : ").append(history.getTransactionNo()).append("\n");
                }
            }

            page++;
            totalCount -= SIZE;
        } while (totalCount >= 0);

        if (!StringUtils.isEmpty(builder.toString())) {
            log.info(builder.toString());
        }

        log.info(SUCCESS);
    }
}
