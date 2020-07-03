package encryption.damo.exception;

import javax.servlet.http.HttpServletRequest;
import encryption.damo.dto.response.global.ResponseObject;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public <T> ResponseEntity<ResponseObject<T>> handleException(HttpServletRequest request, Exception e) {
        this.printLog(request, e);

        return ResponseUtils.createResponseEntity(ResponseCodes.FAIL, null);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ResponseObject<String>> handleIntegrationException(HttpServletRequest request, IntegrationException ie) {
        this.printLog(request, ie);

        return ResponseUtils.createResponseEntity(ie.getResponseCodes(), ie.getMessage());
    }

    protected void printLog(HttpServletRequest request, Exception e) {
        log.error(String.valueOf(request.getRequestURL()));

        if (!StringUtils.isEmpty(e.getMessage())) {
            log.error(e.getMessage());
        }

        if (!StringUtils.isEmpty(e.getLocalizedMessage())) {
            log.error(e.getLocalizedMessage());
        }

        log.error(e.toString());
    }
}
