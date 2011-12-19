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
package uk.ac.cardiff.raptorweb.model.dashboard;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.model.StartStatistics;

public abstract class AbstractDashboardStatistic {
    
    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(AbstractDashboardStatistic.class);
    
    /** The name of ths statistical unit in the MUA that is invoked*/
    private String statisticalUnitName;
    
    /** Whether the statistic is to be computed */
    private boolean enabled;    
    
    /** The type of event this statistic should work over. For example, SHIBBOLETH_AUTHENTICATION*/
    private StatisticParameters.EventType eventType;
    
    /** The chart processor which converts Aggregator Charts into RaptorWeb charts for outputting */
    protected ChartProcessor chartProcessor;
    
    /** Stores which dashboard statistic this should be applied to */
    private StartStatistics.StartStatisticType dashboardStatisticType;
    

    /**
     * Process the <code>model</code> passed as a parameter, and return the result
     * 
     * @param model
     * @return
     * @throws DashboardStatisticException
     */
    public abstract Object processStatistic(AggregatorGraphModel model,StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException;
    
    public Object getProcessedStatistic(AggregatorGraphModel model, StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException{
        if (model==null){
            throw new DashboardStatisticException("Input graph model was null");
        }
        return processStatistic(model,statisticalUnitInformation);
    }

    public void setStatisticalUnitName(String statisticalUnitName) {
        this.statisticalUnitName = statisticalUnitName;
    }

    public String getStatisticalUnitName() {
        return statisticalUnitName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEventType(StatisticParameters.EventType eventType) {
        this.eventType = eventType;
    }

    public StatisticParameters.EventType getEventType() {
        return eventType;
    }

    public void setChartProcessor(ChartProcessor chartProcessor) {
        this.chartProcessor = chartProcessor;
    }

    public ChartProcessor getChartProcessor() {
        return chartProcessor;
    }

    public void setDashboardStatisticType(StartStatistics.StartStatisticType dashboardStatisticType) {
        this.dashboardStatisticType = dashboardStatisticType;
    }

    public StartStatistics.StartStatisticType getDashboardStatisticType() {
        return dashboardStatisticType;
    }

}
