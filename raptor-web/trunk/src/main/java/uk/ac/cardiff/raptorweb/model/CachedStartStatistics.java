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
package uk.ac.cardiff.raptorweb.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.StatisticParameters.ResourceCategory;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet.TimeRange;

public class CachedStartStatistics {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(CachedStartStatistics.class);

    /** The cached statistic */
    private List<StartStatistics> cached = new ArrayList<StartStatistics>();

    public StartStatistics getStartstatistics(final TimeRange timeRange, final String eventType, final ResourceCategory category) {

        for (StartStatistics statistic : cached) {
            log.debug("Looking for statistics [{}] with [{}] for range [{}={}] and Resource Type [{}={}]",
                    new Object[] { eventType, statistic.getComputedForClassType(), timeRange, statistic.getTimeRange(), category, statistic.getResourceCategory() });
            if (statistic.getComputedForClassType().equals(eventType) && timeRange == statistic.getTimeRange() && category == statistic.getResourceCategory()) {
                log.debug("Returning start statistic [{},{}]", statistic.getComputedForClassType(), statistic.getTopFiveResouces());
                return statistic;
            }
        }
        return null;
    }

    /**
     * @param cached
     *            the cached to set
     */
    public void setCached(List<StartStatistics> cached) {
        for (StartStatistics statistic : cached) {
            log.debug("Storing the cached start statistic for {}, time range {} and resource category {}",
                    new Object[] { statistic.getComputedForClassType(), statistic.getTimeRange(), statistic.getResourceCategory() });
        }
        this.cached = cached;
    }

    /**
     * @return the cached
     */
    public List<StartStatistics> getCached() {
        return cached;
    }

}
