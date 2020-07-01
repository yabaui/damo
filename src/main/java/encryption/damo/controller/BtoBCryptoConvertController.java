package encryption.damo.controller;

import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.service.b2b.BtoBCryptoConvertService;
import encryption.damo.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile(value = {"dev", "stg", "btob"})
@RestController
@RequiredArgsConstructor
public class BtoBCryptoConvertController {
    private final BtoBCryptoConvertService cryptoConvertService;

    @GetMapping("/btob-crypto/convert/overseas")
    public ResponseEntity<ResponseObject<String>> convertOverseas() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertOverseasIns());
    }

    @GetMapping("/btob-crypto/convert/ins-market-subscription-history")
    public ResponseEntity<ResponseObject<String>> convertInsMarketSubscriptionHistory() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.convertInsMarketSubscriptionHistory());
    }

    @GetMapping("/btob-crypto/update/ins-market-subscription-history")
    public ResponseEntity<ResponseObject<String>> updateInsMarketSubscriptionHistory() {
        return ResponseUtils.createResponseEntity(
                ResponseCodes.SUCCESS, cryptoConvertService.updateInsMarketSubscriptionHistory());
    }
}
