
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.store.SaveAndApplyResourceClassificationTask;

public class PerformanceInterceptor {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(SaveAndApplyResourceClassificationTask.class);

    public Object traceSave(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("Method {} took {} ms to executed", joinPoint.getSignature().getName(), (end - start));
        return result;
    }
}
