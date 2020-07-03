package encryption.damo.utils;

import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseUtils {
    public static <T> ResponseEntity<ResponseObject<T>> createResponseEntity(ResponseCodes responseCodes, T data) {
        return new ResponseEntity<>(createResponseObject(responseCodes, data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> createResponseEntity(ResponseCodes responseCodes) {
        return new ResponseEntity<>(createResponseObject(responseCodes, null), HttpStatus.OK);
    }

    private static <T> ResponseObject<T> createResponseObject(ResponseCodes responseCodes, T data) {
        return ResponseObject.<T>builder()
                .code(responseCodes.getCode())
                .title(responseCodes.getTitle())
                .message(responseCodes.getMessage())
                .data(data)
                .build();
    }
}
