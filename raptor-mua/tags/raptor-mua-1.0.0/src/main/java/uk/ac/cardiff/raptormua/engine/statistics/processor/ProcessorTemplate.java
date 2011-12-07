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

package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticPostProcessor;

public class ProcessorTemplate implements InitializingBean{

    private Class<? extends StatisticPostProcessor> processorClass;

    private String processorFriendlyName;
    
    /** A String ID of the processor, used for matching statistical unit processors against template processors */
    private String processorId;

    private List<MethodParameter> methodParameters;
    
    /**
     * what scope the class has, whether there should be only one shared instance (SINGLETON),
     * or a new instance for each requester (NON-SINGLETON).
     */
    public enum SCOPE{
        SINGLETON, 
        NONSINGLETON
    }

    /** which scope this processor has, if SINGLETON, then a reference to the singleton bean instance must be given*/
    private SCOPE scope;
    
    /** If scope is SINGLETON, then a refernce to the shared bean instance of that processor must be given */
    private StatisticPostProcessor singletonBeanReference;
    
    /**
     * Default constructor. Makes nonsingleton scope default.
     */
    public ProcessorTemplate(){       
        scope = SCOPE.NONSINGLETON;
    }
    
    /**
     * Method to validate the construction of this processor template at run-time.
     */
    public void afterPropertiesSet(){
        if (scope.equals(SCOPE.SINGLETON)){
                org.springframework.util.Assert.notNull(singletonBeanReference, "A singleton class must have reference to a singleton bean, e.g. set the  singletonBeanReference property");
        }
        org.springframework.util.Assert.notNull(processorFriendlyName,"Friendly name required");
        org.springframework.util.Assert.notNull(processorClass,"Processor class type is required (add processorClass to bean config)");
        org.springframework.util.Assert.notNull(processorId,"Processors must have a processorId for matching");
    }
    
    /**
     * @param processorFriendlyName the processorFriendlyName to set
     */
    public void setProcessorFriendlyName(String processorFriendlyName) {
        this.processorFriendlyName = processorFriendlyName;
    }

    /**
     * @return the processorFriendlyName
     */
    public String getProcessorFriendlyName() {
        return processorFriendlyName;
    }

    /**
     * @param processorClass the processorClass to set
     */
    public void setProcessorClass(Class<? extends StatisticPostProcessor> processorClass) {
        this.processorClass = processorClass;
    }

    /**
     * @return the processorClass
     */
    public Class<? extends StatisticPostProcessor> getProcessorClass() {
        return processorClass;
    }

    /**
     * @param methodParameters the methodParameters to set
     */
    public void setMethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    /**
     * @return the methodParameters
     */
    public List<MethodParameter> getMethodParameters() {
        return methodParameters;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(SCOPE scope) {
        this.scope = scope;
    }

    /**
     * @return the scope
     */
    public SCOPE getScope() {
        return scope;
    }

    /**
     * @param singletonBeanReference the singletonBeanReference to set
     */
    public void setSingletonBeanReference(StatisticPostProcessor singletonBeanReference) {
        this.singletonBeanReference = singletonBeanReference;
    }

    /**
     * @return the singletonBeanReference
     */
    public StatisticPostProcessor getSingletonBeanReference() {
        return singletonBeanReference;
    }

    /**
     * @param processorId the processorId to set
     */
    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    /**
     * @return the processorId
     */
    public String getProcessorId() {
        return processorId;
    }

}
