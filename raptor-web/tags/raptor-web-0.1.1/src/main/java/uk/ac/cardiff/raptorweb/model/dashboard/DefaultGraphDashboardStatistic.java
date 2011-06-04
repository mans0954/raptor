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

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;

public class DefaultGraphDashboardStatistic extends AbstractDashboardStatistic{
    
    


    public Object processStatistic(AggregatorGraphModel model, StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException {
        DateTime chosenStartTime = statisticalUnitInformation.getStatisticParameters().getStartTimeAsDate();
        DateTime currentDateTime = statisticalUnitInformation.getStatisticParameters().getEndTimeAsDate();
        
        RaptorJFreeChartModel jfreeChart = chartProcessor.constructJFreeGraph(GraphPresentation.FRONT, ChartType.AREA, model, 1270, 350,
               (chosenStartTime.toString("ddMMyyyHH-mm") + "-" + currentDateTime.toString("ddMMyyyHH-mm")));
        
        return jfreeChart;
    }

}
