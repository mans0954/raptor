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
        TODAY, LASTWEEK, LASTMONTH, LASTYEAR
    }

    private List<AbstractDashboardStatistic> dashboardStatistics;

    /**
     * @param computedOverTimeRanges
     *            the computedOverTimeRanges to set
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
     * @param resourceCategorys
     *            the resourceCategorys to set
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
