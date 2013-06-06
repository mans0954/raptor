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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.DynamicStatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.raptor.store.QueryableEventHandler;

/**
 * Constructs {@link BaseStatistic}s from its class type and the set of statistic parameters passed in. Allows runtime
 * construction of statistics, as opposed to just those preconfigured.
 */
public class StatisticTypeFactory {

    /** Class logger. */
    private static final Logger log = LoggerFactory.getLogger(StatisticTypeFactory.class);

    public BaseStatistic createNewBaseStatistic(Class<? extends BaseStatistic> statisticClass,
            StatisticParameters parameters) {
        return null;
    }

    /**
     * 
     * Creating a generic {@link BaseStatistic} from the class string encapsulated in statisticType.
     * 
     * @param statisticType an issues and null is returned.
     * @param eventHandler the {@link QueryableEventHandler} added to the statistic so it can be used during default
     *            parameter construction. Could be null.
     * @param registry a {@link StatisticProcessorRegistry} used to initialise post processors.
     * @return
     */
    public static BaseStatistic createNewBaseStatistic(StatisticFunctionType statisticType,
            QueryableEventHandler eventHandler) {
        try {
            Class<?> statisticClass = Class.forName(statisticType.getStatisticClass());
            Object statisticInstance = statisticClass.newInstance();
            if (statisticInstance instanceof BaseStatistic) {
                BaseStatistic statistic = (BaseStatistic) statisticInstance;
                statistic.setEntryHandler(eventHandler);
                statistic.setStatisticParameters(null);
                return statistic;
            } else {
                log.warn("Statistic class was created but it is not a BaseStatistic subclass, so will not be returned");
            }
        } catch (ClassNotFoundException e) {
            log.error(
                    "Can not create base statistic, the statistic function class [{}] does not exist or can not be created",
                    statisticType.getStatisticClass(), e);
        } catch (InstantiationException e) {
            log.error(
                    "Can not create base statistic, the statistic function class [{}] does not exist or can not be instantiated",
                    statisticType.getStatisticClass(), e);
        } catch (IllegalAccessException e) {
            log.error(
                    "Can not create base statistic, the statistic function class [{}] does not exist or can not be created due to access restraints",
                    statisticType.getStatisticClass(), e);
        }
        return null;
    }

    /**
     * 
     * @param statisticalUnitInformation to add statistical parameters to the statistic.
     * @param registry to attach post processors to the statistic
     * 
     * @return
     */
    public static BaseStatistic createNewBaseStatistic(DynamicStatisticalUnitInformation statisticalUnitInformation,
            StatisticProcessorRegistry registry) {
        try {
            Class<?> statisticClass = Class.forName(statisticalUnitInformation.getFunction().getStatisticClass());
            Object statisticInstance = statisticClass.newInstance();
            if (statisticInstance instanceof BaseStatistic) {
                BaseStatistic statistic = (BaseStatistic) statisticInstance;

                statistic.setStatisticParameters(statisticalUnitInformation.getStatisticUnitInformation()
                        .getStatisticParameters());
                statistic.setPostprocessor(null);

                List<StatisticPostProcessor> postProcessors =
                        initialisePostProcessors(statisticalUnitInformation.getStatisticUnitInformation()
                                .getPostprocessors(), registry);
                statistic.setPostprocessor(postProcessors);

                statistic.getStatisticParameters().setUnitName(
                        "DYNAMIC-" + statisticInstance.getClass().getSimpleName());
                return statistic;
            } else {
                log.warn("Statistic class was created but it is not a BaseStatistic subclass, so will not be returned");
            }
        } catch (ClassNotFoundException e) {
            log.error(
                    "Can not create base statistic, the statistic function class [{}] does not exist or can not be created",
                    statisticalUnitInformation.getFunction().getStatisticClass(), e);
        } catch (InstantiationException e) {
            log.error(
                    "Can not create base statistic, the statistic function class [{}] does not exist or can not be instantiated",
                    statisticalUnitInformation.getFunction().getStatisticClass(), e);
        } catch (IllegalAccessException e) {
            log.error(
                    "Can not create base statistic, the statistic function class [{}] does not exist or can not be created due to access restraints",
                    statisticalUnitInformation.getFunction().getStatisticClass(), e);
        }
        return null;
    }

    private static List<StatisticPostProcessor> initialisePostProcessors(
            List<ProcessorInformation> processorInformation, StatisticProcessorRegistry registry) {
        if (processorInformation == null) {
            return null;
        }
        ArrayList<StatisticPostProcessor> initlialisedPostProcessors = new ArrayList<StatisticPostProcessor>();
        for (ProcessorInformation information : processorInformation) {
            try {
                StatisticPostProcessor processor = registry.getProcessor(information);
                log.debug("Initialise post processor with friendlyname [{}]", processor.getFriendlyName());
                initlialisedPostProcessors.add(processor);
            } catch (StatisticPostProcessorFactoryException e) {
                log.error("Could not set processor {} on statistic", information.getProcessorClass(), e);
            } catch (ProcessorRegistryException e) {
                log.error("Could not set processor {} on statistic", information.getProcessorClass(), e);
            }
        }

        return initlialisedPostProcessors;
    }

}
