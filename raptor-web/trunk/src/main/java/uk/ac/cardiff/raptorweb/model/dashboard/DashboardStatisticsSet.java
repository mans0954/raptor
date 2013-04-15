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

import java.util.List;

import uk.ac.cardiff.model.wsmodel.StatisticParameters;

public class DashboardStatisticsSet {

    /**
     * A list of time periods (ranges) that this dasbhoard statistic should be computed for.
     */
    private List<TimeRange> computedOverTimeRanges;

    /**
     * A list of resource categories that this dashboard statistic should be computed for.
     */
    private List<StatisticParameters.ResourceCategory> resourceCategorys;

    public enum TimeRange {
        TODAY, LASTWEEK, LASTMONTH, LASTYEAR, SCONULYEAR, PREVIOUSMONTH
    }

    private List<AbstractDashboardStatistic> dashboardStatistics;

    /**
     * @param computedOverTimeRanges the computedOverTimeRanges to set
     */
    public void setComputedOverTimeRanges(List<TimeRange> computedOverTimeRanges) {
        this.computedOverTimeRanges = computedOverTimeRanges;
    }

    /**
     * @return the computedOverTimeRanges
     */
    public List<TimeRange> getComputedOverTimeRanges() {
        return computedOverTimeRanges;
    }

    /**
     * @param resourceCategorys the resourceCategorys to set
     */
    public void setResourceCategorys(List<StatisticParameters.ResourceCategory> resourceCategorys) {
        this.resourceCategorys = resourceCategorys;
    }

    /**
     * @return the resourceCategorys
     */
    public List<StatisticParameters.ResourceCategory> getResourceCategorys() {
        return resourceCategorys;
    }

    /**
     * @param dashboardStatistics the dashboardStatistics to set
     */
    public void setDashboardStatistics(List<AbstractDashboardStatistic> dashboardStatistics) {
        this.dashboardStatistics = dashboardStatistics;
    }

    /**
     * @return the dashboardStatistics
     */
    public List<AbstractDashboardStatistic> getDashboardStatistics() {
        return dashboardStatistics;
    }

}
