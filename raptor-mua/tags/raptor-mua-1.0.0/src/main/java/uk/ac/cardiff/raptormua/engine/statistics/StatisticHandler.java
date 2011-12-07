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

package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.store.QueryableEventHandler;

/**
 * Allows the storage and invocation of statistical units
 * 
 * @author philsmart
 */
public class StatisticHandler {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(StatisticHandler.class);

    /** Register for managing statistics **/
    private StatisticRegistry statisticRegistry;

    /** A reference to the event handler that is used to access all underlying events */
    private QueryableEventHandler eventHandler;

    /**
     * @param statisticName
     */
    public AggregatorGraphModel performStatistic(String statisticName) {
        if (statisticRegistry != null) {
            BaseStatistic statistic = statisticRegistry.getStatistic(statisticName);
            if (statistic != null) {
                return performStatiticalPipeline(statistic);
            }
        } else {
            log.error("No statistical registry has been defined or attached to this statistic handler, no statistics to perform");
        }
        return null;
    }

    private AggregatorGraphModel performStatiticalPipeline(BaseStatistic statistic) {
        statistic.setEntryHandler(getEventHandler());
        Boolean success = invoke(statistic);
        log.info("Statistic [{}] succedded {}", statistic.getStatisticParameters().getUnitName(), success);
        if (success) {
            // now send through post processor
            statistic.postProcess();
            try {
                AggregatorGraphModel graphModel = statistic.constructGraphModel();
                statistic.reset();
                return graphModel;
            } catch (Exception e) {
                // must catch this error here, so we can clear the observations that the statistic has generated
                statistic.reset();
                log.error("Problem constructing graph model for statistic {}, {}", statistic.getStatisticParameters()
                        .getUnitName(), e.getMessage());
                return null;
            }
        }
        /*
         * always reset the observationseries for the statistic, so the next execution is not an accumulation of the
         * ones before it
         */
        statistic.reset();
        return null;
    }

    /**
     * @param statistic
     */
    private Boolean invoke(BaseStatistic statistic) {
        if (getEventHandler() != null)
            log.debug("Invoking statistic [{}], working off {} events", statistic.getStatisticParameters()
                    .getUnitName(), getEventHandler().getNumberOfEvents());

        /* stop processing if there are no valid entries */
        if (getEventHandler() == null || getEventHandler().getNumberOfEvents() == 0) {
            log.error("Not enough events to perform statistic {}", statistic.getStatisticParameters().getUnitName());
            return false;
        }

        try {
            List<MethodParameter> params = statistic.getStatisticParameters().getMethodParams();
            List<Series> listOfSeries = statistic.getStatisticParameters().getSeries();
            boolean success = true;
            for (Series series : listOfSeries) {
                String whereClause = series.computeComparisonAsSQL();
                if (whereClause == null) {
                    whereClause = "";
                }
                success = statistic.performStatistic(params, whereClause);
            }
            return success;
        } catch (StatisticalUnitException e) {
            log.error("Failed to invoke statistics [{}]", statistic.getStatisticParameters().getUnitName(), e);

        }
        return false;

    }

    /**
     * Delegates to the statisticRegistry to update a <code>Statistic</code>
     * 
     * @param statisticalUnitInformation
     */
    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
        statisticRegistry.updateStatisticalUnit(statisticalUnitInformation);

    }

    public List<BaseStatistic> getStatisticalUnits() {
        return statisticRegistry.getStatisticalUnits();
    }

    /**
     * @param eventHandler the eventHandler to set
     */
    public void setEventHandler(QueryableEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * @return the eventHandler
     */
    public QueryableEventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * Method that maintains compatibility with older configuration files that specify the list of statistical units
     * directly on the statistics handler.
     * 
     * @param statisticalUnits
     */
    @Deprecated
    public void setStatisticalUnits(List<BaseStatistic> statisticalUnits) {
        if (statisticRegistry == null) {
            statisticRegistry = new ContextAwareStatisticRegistry();
        }
        statisticRegistry.setStatisticalUnits(statisticalUnits);
    }

    /**
     * @param statisticRegistry the statisticRegistry to set
     */
    public void setStatisticRegistry(StatisticRegistry statisticRegistry) {
        this.statisticRegistry = statisticRegistry;
    }

    /**
     * @return the statisticRegistry
     */
    public StatisticRegistry getStatisticRegistry() {
        return statisticRegistry;
    }

}