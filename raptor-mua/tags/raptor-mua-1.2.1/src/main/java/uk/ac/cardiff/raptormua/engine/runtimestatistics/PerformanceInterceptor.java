/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        log.debug("Asynchronous storage engine called");
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.debug("Method {} took {} ms to executed", joinPoint.getSignature().getName(), (end - start));
        return result;
    }
}
