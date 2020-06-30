package encryption.damo.controller;

import encryption.damo.dto.response.damo.CipherHashResponse;
import encryption.damo.dto.response.damo.ExportKeyResponse;
import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.service.DAmoService;
import encryption.damo.utils.ResponseUtils;
import lombok.Getter;
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
    public ResponseEntity<ResponseObject<ExportKeyResponse>> getScpExportKey(String keyName) {
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getScpExportKey(keyName));
    }

    @GetMapping("/export-key/service-id")
    public ResponseEntity<ResponseObject<ExportKeyResponse>> getScpExportKeyServiceID(String keyName) {
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getScpExportKeyServiceID(keyName));
    }

    @GetMapping("/export-context")
    public ResponseEntity<ResponseObject<ExportKeyResponse>> getScpExportContext(String keyName) {
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getScpExportContext(keyName));
    }

    @GetMapping("/export-context/service-id")
    public ResponseEntity<ResponseObject<ExportKeyResponse>> getScpExportContextServiceID(String keyName) {
        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getScpExportContextServiceID(keyName));
    }

    @PostMapping("/encode")
    public ResponseEntity<ResponseObject<String>> getEncode(String keyword, String key) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(keyword)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getEncode(keyword, key));
    }

    @PostMapping("/decode")
    public ResponseEntity<ResponseObject<String>> getDecode(String keyword, String key) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(keyword)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getDecode(keyword, key));
    }

    @PostMapping("/encode/b64")
    public ResponseEntity<ResponseObject<String>> getEncodeB64(String keyword, String key) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(keyword)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getEncodeB64(keyword, key));
    }

    @PostMapping("/decode/b64")
    public ResponseEntity<ResponseObject<String>> getDecodeB64(String keyword, String key) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(keyword)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getDecodeB64(keyword, key));
    }

    @PostMapping("/encode/b64/rrn")
    public ResponseEntity<ResponseObject<String>> getEncodeB64RRN(String keyword, String key) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(keyword)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getEncodeB64RRN(keyword, key));
    }

    @GetMapping("/cipher-hash")
    public ResponseEntity<ResponseObject<CipherHashResponse>> getCipherHash(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
        }

        return ResponseUtils.createResponseEntity(ResponseCodes.SUCCESS, dAmoService.getCipherHash(keyword));
    }

}
