package encryption.damo.utils;

import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseUtils {
    public static <T> ResponseEntity<ResponseObject<T>> createResponseEntity(ResponseCodes responseCodes, T data) {
        return new ResponseEntity<>(createResponseObject(responseCodes, data), HttpStatus.OK);
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
