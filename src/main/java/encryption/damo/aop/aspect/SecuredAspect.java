package encryption.damo.aop.aspect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.exception.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class SecuredAspect {
    @Before("within(encryption.damo.controller.*)")
    public void checkSecure() {
        if (Objects.isNull(RequestContextHolder.getRequestAttributes())) {
            throw new IntegrationException(ResponseCodes.FAIL);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        final String clientKey = "client";
        final String secretKey = "secret";
        final String clientCheckString = "sangik";

        final String client = request.getHeader(clientKey);
        final String secret = request.getHeader(secretKey);

        if (StringUtils.isEmpty(client) || StringUtils.isEmpty(secret)
                || !client.equals(clientCheckString)) {
            throw new IntegrationException(ResponseCodes.FAIL);
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");

        final String secretCheckString = "810609" + LocalDateTime.now().format(formatter);

        if (!secret.equals(secretCheckString)) {
            throw new IntegrationException(ResponseCodes.FAIL);
        }
    }
}
