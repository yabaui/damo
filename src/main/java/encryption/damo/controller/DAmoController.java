package encryption.damo.controller;

import encryption.damo.dto.response.damo.CryptoResponse;
import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.service.DAmoService;
import encryption.damo.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DAmoController {

    private final DAmoService dAmoService;

    @GetMapping("/export-key")
    public ResponseEntity<ResponseObject<CryptoResponse>> getScpExportKey(String key) {
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getScpExportKey(key));
    }

    @PostMapping("/encode")
    public ResponseEntity<ResponseObject<CryptoResponse>> getEncode(String value) {
        if (StringUtils.isEmpty(value)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getEncode(value));
    }

    @PostMapping("/decode")
    public ResponseEntity<ResponseObject<CryptoResponse>> getDecode(String value) {
        if (StringUtils.isEmpty(value)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getDecode(value));
    }

    @PostMapping("/encode/b64")
    public ResponseEntity<ResponseObject<CryptoResponse>> getEncodeB64(String value) {
        if (StringUtils.isEmpty(value)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getEncodeB64(value));
    }

    @PostMapping("/decode/b64")
    public ResponseEntity<ResponseObject<CryptoResponse>> getDecodeB64(String value) {
        if (StringUtils.isEmpty(value)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getDecodeB64(value));
    }
}
