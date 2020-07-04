package encryption.damo.aop.aspect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import encryption.damo.enums.ResponseCodes;
import encryption.damo.exception.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggedExecuteMethodTime {
    @Around("execution(* encryption.damo.service.member.MemberCryptoConvertService.*(..))" +
            "|| execution(* encryption.damo.service.b2b.BtoBCryptoConvertService.*(..))")
    public Object checkSecure(ProceedingJoinPoint joinPoint) throws Throwable {
        final String START_FORMAT = "Class: %s / Method: %s / START_TIME: %s";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        log.info(String.format(START_FORMAT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                LocalDateTime.now().format(formatter)));

        Object object = joinPoint.proceed();

        final String END_FORMAT = "Class: %s / Method: %s / END_TIME: %s";

        log.info(String.format(END_FORMAT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                LocalDateTime.now().format(formatter)));

        return object;
    }
}
