package otus.nchuraeva.spring.task16.monitoring;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class MonitorAspect {

    private final MonitoringService monitoringService;

    @Around(value = "@within(otus.nchuraeva.spring.task16.monitoring.AppMonitoring)" +
            " || @annotation(otus.nchuraeva.spring.task16.monitoring.AppMonitoring)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        monitoringService.countAll(className, methodName);
        try {
            Object result = joinPoint.proceed();
            monitoringService.countPositive(className, methodName);
            return result;
        } catch (Exception ex) {
            monitoringService.countError(className, methodName);
            throw ex;
        }
    }
}
