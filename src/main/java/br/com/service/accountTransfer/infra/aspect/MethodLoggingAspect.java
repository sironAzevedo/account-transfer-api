package br.com.service.accountTransfer.infra.aspect;

import br.com.service.accountTransfer.infra.log.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MethodLoggingAspect {

    @Pointcut("@annotation(logging)")
    public void callAt(Loggable logging) {}

//    @Before("@annotation(br.com.service.accountTransfer.infra.log.LogMethodExecution)")
//    public void logMethodExecution(JoinPoint joinPoint) {
//        log.info("Executing method: {}", joinPoint.getSignature().toString());
//    }

    @Around("callAt(logging)")
    public Object around(ProceedingJoinPoint pjp, Loggable logging) throws Throwable {
        long start = System.currentTimeMillis();
        var methodName = StringUtils.defaultIfEmpty(logging.value(), pjp.getSignature().toString());
        try {
            log.info("INICIO - [Executing method: {}]", methodName);
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("FIM - [Executing method: {}] - Duration: {} ms", methodName, duration);
        }
    }
}
