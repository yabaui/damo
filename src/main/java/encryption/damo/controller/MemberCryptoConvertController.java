package encryption.damo.controller;

import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.service.member.MemberCryptoConvertService;
import encryption.damo.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile(value = {"dev", "stg", "member"})
@RestController
@RequiredArgsConstructor
public class MemberCryptoConvertController {
    private final MemberCryptoConvertService cryptoConvertService;

    @GetMapping("/member-crypto/convert/health-checkup")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckup() {
        cryptoConvertService.convertHealthCheckupCrypto();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member-crypto/convert/health-checkup-detail")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupDetail() {
        cryptoConvertService.convertHealthCheckupDetailCrypto();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member-crypto/convert/family-health-checkup")
    public ResponseEntity<ResponseObject<String>> convertFamilyHeathCheckup() {
        cryptoConvertService.convertFamilyHeathCheckupCrypto();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member-crypto/convert/health-checkup-analysis-pdf")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupAnalysisPdf() {
        cryptoConvertService.convertHealthCheckupAnalysisPdfCrypto();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member-crypto/convert/health-checkup-analysis-guide")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupBioAgeAnalysisGuide() {
        cryptoConvertService.convertHealthCheckupBioAgeAnalysisGuideCrypto();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member-crypto/convert/health-checkup-life-expectancy")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupBioAgeLifeExpectancy() {
        cryptoConvertService.convertHealthCheckupBioAgeLifeExpectancyCrypto();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member-crypto/convert/ins-market-subscription-history")
    public ResponseEntity<ResponseObject<String>> convertInsMarketSubscriptionHistory() {
        cryptoConvertService.convertInsMarketSubscriptionHistory();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }

    @GetMapping("/member/decrypt-legacy/ins-market-subscription-history")
    public ResponseEntity<ResponseObject<String>> decryptLegacyInsMarketSubscriptionHistory(
            @RequestParam("transactionNo") String transactionNo) {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.decryptLegacyInsMarketSubscriptionHistory(transactionNo));
    }

    @GetMapping("/member-crypto/verification/ins-market-subscription-history")
    public ResponseEntity<ResponseObject<String>> verifyInsMarketSubscriptionHistory() {
        cryptoConvertService.verifyInsMarketSubscriptionHistory();
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS);
    }
}
