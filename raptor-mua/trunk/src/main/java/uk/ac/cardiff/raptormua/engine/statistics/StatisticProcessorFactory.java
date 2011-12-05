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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.raptormua.engine.statistics.processor.AbstractStatisticPostProcessor;
import uk.ac.cardiff.raptormua.engine.statistics.processor.PostprocessorException;
import uk.ac.cardiff.raptormua.engine.statistics.processor.ProcessorTemplate;

/**
 * Factory for building post-processors and pre-processors given a set of parameters
 * 
 * @author philsmart
 * 
 */
public class StatisticProcessorFactory {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(StatisticProcessorFactory.class);

    public StatisticPostProcessor getPostProcessor(ProcessorTemplate template, List<MethodParameter> parameters)
            throws StatisticPostProcessorFactoryException {

        try {
            AbstractStatisticPostProcessor processor =
                    (AbstractStatisticPostProcessor) template.getProcessorClass().newInstance();
            processor.setFriendlyName(template.getProcessorFriendlyName());
            processor.registerAndSetMethodParameters(parameters);
            // add information about methods from the template to the input.
            if (parameters != null) {
                for (MethodParameter parameter : parameters) {
                    for (MethodParameter templateParameter : template.getMethodParameters()) {
                        log.trace("MethodParameter [{}], template parameter [{}]", parameter.getParameterName(),
                                templateParameter.getParameterName());
                        if (parameter.getParameterName().equals(templateParameter.getParameterName())) {
                            parameter.setValueType(templateParameter.getValueType());
                        }
                    }
                }
            }
            log.debug("Returning new processor {}", processor);
            return processor;
        } catch (InstantiationException e) {
            throw new StatisticPostProcessorFactoryException(e);
        } catch (IllegalAccessException e) {
            throw new StatisticPostProcessorFactoryException(e);
        } catch (PostprocessorException e) {
            throw new StatisticPostProcessorFactoryException(e);
        }

    }
}
