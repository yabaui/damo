package encryption.damo.controller;

import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.service.member.MemberCryptoConvertService;
import encryption.damo.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile(value = {"dev", "stg", "member"})
@RestController
@RequiredArgsConstructor
public class MemberCryptoConvertController {
    private final MemberCryptoConvertService cryptoConvertService;

    @GetMapping("/member-crypto/convert/health-checkup")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckup() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertHealthCheckupCrypto());
    }

    @GetMapping("/member-crypto/convert/health-checkup-detail")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupDetail() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertHealthCheckupDetailCrypto());
    }

    @GetMapping("/member-crypto/convert/family-health-checkup")
    public ResponseEntity<ResponseObject<String>> convertFamilyHeathCheckup() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertFamilyHeathCheckupCrypto());
    }

    @GetMapping("/member-crypto/convert/health-checkup-analysis-pdf")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupAnalysisPdf() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertHealthCheckupAnalysisPdfCrypto());
    }

    @GetMapping("/member-crypto/convert/health-checkup-analysis-guide")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupBioAgeAnalysisGuide() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertHealthCheckupBioAgeAnalysisGuideCrypto());
    }

    @GetMapping("/member-crypto/convert/health-checkup-life-expectancy")
    public ResponseEntity<ResponseObject<String>> convertHealthCheckupBioAgeLifeExpectancy() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertHealthCheckupBioAgeLifeExpectancyCrypto());
    }

    @GetMapping("/member-crypto/convert/ins-market-subscription-history")
    public ResponseEntity<ResponseObject<String>> convertInsMarketSubscriptionHistory() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertInsMarketSubscriptionHistory());
    }
}
