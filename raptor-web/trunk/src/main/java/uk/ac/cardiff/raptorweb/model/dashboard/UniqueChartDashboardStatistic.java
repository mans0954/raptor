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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;

public class UniqueChartDashboardStatistic extends AbstractDashboardStatistic {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(UniqueChartDashboardStatistic.class);

    public Object processStatistic(AggregatorGraphModel model, StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException {
        log.info("Processing Statistic Using {}",UniqueChartDashboardStatistic.class.getCanonicalName());
        
        RaptorTableChartModel table = chartProcessor.constructRaptorTableChartModel(model);

        if (table.getTableSeries().size() == 1) {
            if (table.getTableSeries().get(0).getRows().size() == 1) {
                if (table.getTableSeries().get(0).getRows().get(0).getValue() instanceof Double) {                    
                    Double value = (((Double) table.getTableSeries().get(0).getRows().get(0).getValue()));                    
                    return value;
                } else {
                    log.error("Table value at series 0, row 0 was not an instance of double");
                    throw new DashboardStatisticException("Table value at series 1, row 1 was not an instance of double");
                }
            } else {
                log.error("Table did not have 1 and only 1 row");
                throw new DashboardStatisticException("Table did not have 1 and only 1 row");
            }
        } else {
            log.error("Table did not have 1 and only 1 series");
            throw new DashboardStatisticException("Table did not have 1 and only 1 series");
        }
    }

}
