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
/**
 * 
 */

package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticPostProcessor;

/**
 * @author philsmart
 * 
 */
public abstract class AbstractStatisticPostProcessor implements StatisticPostProcessor {

    private String friendlyName;

    private String processorId;

    /**
     * Throw an {@link PostprocessorException} if something about the setup of this postprocessor has gone wrong, and
     * hence the postprocessor should not be attached to the statistic.
     */
    public abstract void registerAndSetMethodParameters(List<MethodParameter> methodParameters)
            throws PostprocessorException;

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

    /**
     * @param friendlyName the friendlyName to set
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }

}
