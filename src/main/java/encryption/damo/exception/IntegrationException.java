package encryption.damo.exception;

import encryption.damo.enums.ResponseCodes;
import lombok.Getter;

public class IntegrationException extends RuntimeException {
    @Getter
    private final ResponseCodes responseCodes;

    public IntegrationException(ResponseCodes responseCodes) {
        this.responseCodes = responseCodes;
    }
}
