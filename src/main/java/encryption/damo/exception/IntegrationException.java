package encryption.damo.exception;

import encryption.damo.enums.ResponseCodes;
import lombok.Getter;

public class IntegrationException extends RuntimeException {
    @Getter
    private final ResponseCodes responseCodes;
    @Getter
    private final String message;

    public IntegrationException(ResponseCodes responseCodes) {
        this.responseCodes = responseCodes;
        this.message = null;
    }

    public IntegrationException(ResponseCodes responseCodes, String message) {
        this.responseCodes = responseCodes;
        this.message = message;
    }
}
