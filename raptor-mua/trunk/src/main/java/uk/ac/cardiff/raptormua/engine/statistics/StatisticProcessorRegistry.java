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

package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.raptormua.engine.statistics.processor.ProcessorTemplate;

public interface StatisticProcessorRegistry {

    /**
     * Gets an instantiated {@link StatisticPostProcessor} from the processor registry based on the
     * <code>processorInformation</code>.
     * 
     * @param processorInformation information from which to generate a {@link StatisticPostProcessor}.
     * @return a {@link StatisticPostProcessor}.
     * 
     * @throws ProcessorRegistryException
     * @throws StatisticPostProcessorFactoryException
     */
    public StatisticPostProcessor getProcessor(ProcessorInformation processorInformation)
            throws ProcessorRegistryException, StatisticPostProcessorFactoryException;

    /**
     * Gets all the registered statistic processor templates.
     * 
     * @return a list of {@link ProcessorTemplate}.
     */
    public List<ProcessorTemplate> getStatisticProcessorTemplates();

}
