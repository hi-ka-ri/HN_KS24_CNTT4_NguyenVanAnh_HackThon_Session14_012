package javaservice.mockhackthon.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {


    @Pointcut("execution(* javaservice.mockhackthon.service.CarService.create(..)) " +
            "|| execution(* javaservice.mockhackthon.service.CarService.update(..))")
    public void carCreateOrUpdate() {
    }

    @Pointcut("execution(* javaservice.mockhackthon.service..*(..))")
    public void allServiceMethods() {
    }


    @Before("carCreateOrUpdate()")
    public void logBeforeCreateOrUpdate(JoinPoint joinPoint) {
        log.info("[AOP - BEFORE] Method: {} | Args: {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Log sau khi create/update chạy thành công
     */
    @AfterReturning(pointcut = "carCreateOrUpdate()", returning = "result")
    public void logAfterReturningCreateOrUpdate(JoinPoint joinPoint, Object result) {
        log.info("[AOP - AFTER RETURNING] Method: {} | Result: {}",
                joinPoint.getSignature().getName(),
                result);
    }

    /**
     * Log khi service bị lỗi
     */
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("[AOP - EXCEPTION] Method: {} | Error: {}",
                joinPoint.getSignature().getName(),
                ex.getMessage());
    }
}