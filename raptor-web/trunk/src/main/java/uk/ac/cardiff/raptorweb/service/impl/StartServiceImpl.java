/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.CachedStartStatistics;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.StartModel;
import uk.ac.cardiff.raptorweb.model.StartStatistics;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.model.StartModel.TimeRange;
import uk.ac.cardiff.raptorweb.model.dashboard.AbstractDashboardStatistic;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticException;
import uk.ac.cardiff.raptorweb.service.StartService;

/**
 * @author philsmart
 * 
 */
public class StartServiceImpl implements StartService {
    // TODO start or dashboard stats should not be hard coded

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(StartServiceImpl.class);

    /** The engine that is delegated to for all common internal functions of RaptorWeb */
    private RaptorWebEngine webEngine;

    /** A <code>List</code> of all the dashboard statistics to compute */
    private List<AbstractDashboardStatistic> dashboardStatistics;

    // TODO FIX THE MESS OF CachedStartStatistics in this class (URGENT)
    /** holds the statistics for the front page gathered from the background worker thread */
    private CachedStartStatistics cachedStartModelTodayShibExternal;
    private CachedStartStatistics cachedStartModelLastWeekShibExternal;
    private CachedStartStatistics cachedStartModelLastMonthShibExternal;
    private CachedStartStatistics cachedStartModelLastYearShibExternal;

    private CachedStartStatistics cachedStartModelTodayShibInternal;
    private CachedStartStatistics cachedStartModelLastWeekShibInternal;
    private CachedStartStatistics cachedStartModelLastMonthShibInternal;
    private CachedStartStatistics cachedStartModelLastYearShibInternal;

    private CachedStartStatistics cachedStartModelTodayShibAll;
    private CachedStartStatistics cachedStartModelLastWeekShibAll;
    private CachedStartStatistics cachedStartModelLastMonthShibAll;
    private CachedStartStatistics cachedStartModelLastYearShibAll;

    private CachedStartStatistics cachedStartModelTodayEzproxyInternal;
    private CachedStartStatistics cachedStartModelLastWeekEzproxyInternal;
    private CachedStartStatistics cachedStartModelLastMonthEzproxyInternal;
    private CachedStartStatistics cachedStartModelLastYearEzproxyInternal;

    private CachedStartStatistics cachedStartModelTodayEzproxyExternal;
    private CachedStartStatistics cachedStartModelLastWeekEzproxyExternal;
    private CachedStartStatistics cachedStartModelLastMonthEzproxyExternal;
    private CachedStartStatistics cachedStartModelLastYearEzproxyExternal;

    private CachedStartStatistics cachedStartModelTodayEzproxyAll;
    private CachedStartStatistics cachedStartModelLastWeekEzproxyAll;
    private CachedStartStatistics cachedStartModelLastMonthEzproxyAll;
    private CachedStartStatistics cachedStartModelLastYearEzproxyAll;

    public StartServiceImpl() {
        cachedStartModelTodayShibInternal = new CachedStartStatistics();
        cachedStartModelLastWeekShibInternal = new CachedStartStatistics();
        cachedStartModelLastMonthShibInternal = new CachedStartStatistics();
        cachedStartModelLastYearShibInternal = new CachedStartStatistics();

        cachedStartModelTodayShibExternal = new CachedStartStatistics();
        cachedStartModelLastWeekShibExternal = new CachedStartStatistics();
        cachedStartModelLastMonthShibExternal = new CachedStartStatistics();
        cachedStartModelLastYearShibExternal = new CachedStartStatistics();

        cachedStartModelTodayShibAll = new CachedStartStatistics();
        cachedStartModelLastWeekShibAll = new CachedStartStatistics();
        cachedStartModelLastMonthShibAll = new CachedStartStatistics();
        cachedStartModelLastYearShibAll = new CachedStartStatistics();

        cachedStartModelTodayEzproxyInternal = new CachedStartStatistics();
        cachedStartModelLastWeekEzproxyInternal = new CachedStartStatistics();
        cachedStartModelLastMonthEzproxyInternal = new CachedStartStatistics();
        cachedStartModelLastYearEzproxyInternal = new CachedStartStatistics();

        cachedStartModelTodayEzproxyExternal = new CachedStartStatistics();
        cachedStartModelLastWeekEzproxyExternal = new CachedStartStatistics();
        cachedStartModelLastMonthEzproxyExternal = new CachedStartStatistics();
        cachedStartModelLastYearEzproxyExternal = new CachedStartStatistics();

        cachedStartModelTodayEzproxyAll = new CachedStartStatistics();
        cachedStartModelLastWeekEzproxyAll = new CachedStartStatistics();
        cachedStartModelLastMonthEzproxyAll = new CachedStartStatistics();
        cachedStartModelLastYearEzproxyAll = new CachedStartStatistics();
    }

    public void generateStatisticsBackground() {
        log.info("Generating background statistics for the start page, using {}", this);
        CurrentTimeRange currentTimeRange = getTimeRanges();

        StatisticParameters.EventType shibEventType = StatisticParameters.EventType.SHIBBOLETH_AUTHENTICATION;
        StatisticParameters.EventType ezproxyEventType = StatisticParameters.EventType.EZPROXY_AUTHENTICATION;
        StatisticParameters.ResourceCategory resourceCategoryAll = StatisticParameters.ResourceCategory.ALL;
        StatisticParameters.ResourceCategory resourceCategoryExternal = StatisticParameters.ResourceCategory.EXTERNAL;
        StatisticParameters.ResourceCategory resourceCategoryInternal = StatisticParameters.ResourceCategory.INTERNAL;

        log.debug("Background start page worker getting today");
        generateStatistics(cachedStartModelTodayShibAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, shibEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelTodayShibInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, shibEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelTodayShibExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, shibEventType, resourceCategoryExternal);

        generateStatistics(cachedStartModelTodayEzproxyAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, ezproxyEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelTodayEzproxyInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, ezproxyEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelTodayEzproxyExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, ezproxyEventType, resourceCategoryExternal);

        log.debug("Background start page worker getting last week");
        generateStatistics(cachedStartModelLastWeekShibAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, shibEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelLastWeekShibInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, shibEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelLastWeekShibExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, shibEventType, resourceCategoryExternal);

        generateStatistics(cachedStartModelLastWeekEzproxyAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, ezproxyEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelLastWeekEzproxyInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, ezproxyEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelLastWeekEzproxyExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, ezproxyEventType, resourceCategoryExternal);

        log.debug("Background start page worker getting last month");
        generateStatistics(cachedStartModelLastMonthShibAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, shibEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelLastMonthShibInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, shibEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelLastMonthShibExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, shibEventType, resourceCategoryExternal);

        generateStatistics(cachedStartModelLastMonthEzproxyAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, ezproxyEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelLastMonthEzproxyInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, ezproxyEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelLastMonthEzproxyExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, ezproxyEventType, resourceCategoryExternal);

        log.debug("Background start page worker getting last year");
        generateStatistics(cachedStartModelLastYearShibAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, shibEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelLastYearShibInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, shibEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelLastYearShibExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, shibEventType, resourceCategoryExternal);

        generateStatistics(cachedStartModelLastYearEzproxyAll.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, ezproxyEventType, resourceCategoryAll);
        generateStatistics(cachedStartModelLastYearEzproxyInternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, ezproxyEventType, resourceCategoryInternal);
        generateStatistics(cachedStartModelLastYearEzproxyExternal.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, ezproxyEventType, resourceCategoryExternal);

        log.info("Generating background statistics for the start page...done");
    }

    @Override
    public void generateStatistics(WebSession websession) {
        log.debug("Getting start statistics for {} from {}", websession.getStartmodel().getStatsRangeSelector(), this);

        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthShibAll.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekShibAll.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayShibAll.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearShibAll.getCached());
        
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthShibExternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekShibExternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayShibExternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearShibExternal.getCached());
        
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthShibInternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekShibInternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayShibInternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearShibInternal.getCached());

        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthEzproxyAll.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekEzproxyAll.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayEzproxyAll.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.ALL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearEzproxyAll.getCached());
        
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthEzproxyInternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekEzproxyInternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayEzproxyInternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.INTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearEzproxyInternal.getCached());
        
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthEzproxyExternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekEzproxyExternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayEzproxyExternal.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION &&
                websession.getStartmodel().getResourceCategory() == StatisticParameters.ResourceCategory.EXTERNAL)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearEzproxyExternal.getCached());

        // so we could output the name of the attached MUA
        Capabilities capabilities = getAttachedCapabilities();
        if (capabilities != null) {
            websession.getStartmodel().setAttachedMUACapabilities(capabilities);

        }
    }

    private CurrentTimeRange getTimeRanges() {
        long currentTimeInMS = System.currentTimeMillis();
        DateTime currentDateTime = new DateTime(currentTimeInMS);
        DateTime today = new DateTime(currentTimeInMS);
        today = today.minusHours(today.getHourOfDay());
        today = today.minusMinutes(today.getMinuteOfHour());
        today = today.minusSeconds(today.getSecondOfMinute());
        DateTime oneMonthPrevious = currentDateTime.minusMonths(1);
        DateTime oneYearPrevious = currentDateTime.minusYears(1);
        DateTime oneWeekPrevious = currentDateTime.minusWeeks(1);

        CurrentTimeRange currentRange = new CurrentTimeRange();
        currentRange.startMonth = oneMonthPrevious;
        currentRange.startToday = today;
        currentRange.startWeek = oneWeekPrevious;
        currentRange.startYear = oneYearPrevious;
        currentRange.currentTime = currentDateTime;

        log.debug("Start Page, TODAY [start:{}] [end:{}]", today, currentDateTime);
        log.debug("Start Page, LASTWEEK [start:{}] [end:{}]", oneWeekPrevious, currentDateTime);
        log.debug("Start Page, LASTMONTH [start:{}] [end:{}]", oneMonthPrevious, currentDateTime);
        log.debug("Start Page, LASTYEAR [start:{}] [end:{}]", oneYearPrevious, currentDateTime);

        return currentRange;

    }

    /**
     * Adds the following statistical information from the attached MUA: 1. Number of authentications per <RANGE> 2.
     */
    private void generateStatistics(StartStatistics startstats, DateTime currentDateTime, DateTime chosenStartTime, StatisticParameters.EventType eventType,
            StatisticParameters.ResourceCategory resourceCategory) {

        List<StatisticalUnitInformation> statisticalUnits = getStatisticalUnits();
        log.info("Dashboard statistics are to be computed from a possible {} MUA statistics", statisticalUnits.size());

        int computed = 0;
        int toCompute = 0;
        for (AbstractDashboardStatistic dashboardStatistic : dashboardStatistics) {
            if (dashboardStatistic.isEnabled() && dashboardStatistic.getEventType() == eventType) {
                toCompute++;
                for (StatisticalUnitInformation unit : statisticalUnits) {
                    if (unit.getStatisticParameters().getType() == StatisticParameters.StatisticType.SYSTEM) {
                        if (unit.getStatisticParameters().getUnitName().equals(dashboardStatistic.getStatisticalUnitName())) {
                            unit.getStatisticParameters().setEndTime(currentDateTime);
                            unit.getStatisticParameters().setStartTime(chosenStartTime);
                            unit.getStatisticParameters().setEventType(dashboardStatistic.getEventType());
                            unit.getStatisticParameters().setResourceCategory(resourceCategory);
                            log.debug("Invoking statistic {} for the dasboard page", unit.getStatisticParameters().getUnitName());
                            AggregatorGraphModel model = webEngine.updateAndInvokeStatisticalUnit(unit);
                            try {
                                Object result = dashboardStatistic.getProcessedStatistic(model, unit);
                                cacheStatistic(startstats, result, dashboardStatistic.getDashboardStatisticType());
                                computed++;
                            } catch (DashboardStatisticException e) {
                                log.warn("Dashboard statistic failed to process statistic, {}", e.getMessage());
                            } catch (ClassCastException e) {
                                log.error("Unable to set cached statistic {}", e.getMessage());
                            }
                        }
                    }

                }
            }
        }
        log.info("{} out of a possible {} dashboard statistics computed", computed, toCompute);
        startstats.setAccurateOf(new DateTime(System.currentTimeMillis()));

    }

    private void cacheStatistic(StartStatistics startstats, Object result, StartStatistics.StartStatisticType statisticType) throws ClassCastException {
        log.debug("Setting cached start statistic, for type {}", statisticType);

        if (statisticType == null) {
            log.error("StatisticType not set for this statistic");
            return;
        }

        switch (statisticType) {
            case NO_AUTHS:
                startstats.setNumberOfAuthenticationsPer(((Double) result).doubleValue());
                break;

            case NO_UNIQUE_AUTHS:
                startstats.setNumberOfUniqueAuthenticationsPer(((Double) result).doubleValue());
                break;

            case TOP_FIVE:
                startstats.setTopFiveResouces((RaptorTableChartModel) result);
                break;

            case HEADLINE_GRAPH:
                startstats.setHeadlineGraph((RaptorJFreeChartModel) result);
                break;

            case TOP_FIVE_UNIQUE:
                startstats.setTopFiveUniqueUsersPerSP((RaptorTableChartModel) result);
                break;
            default:
                log.error("Statistic could not be cached, probably the wrong statisticType set");
        }

    }

    private List<StatisticalUnitInformation> getStatisticalUnits() {
        return webEngine.getStatisticalUnits();
    }

    public void setWebEngine(RaptorWebEngine webEngine) {
        this.webEngine = webEngine;
    }

    public RaptorWebEngine getWebEngine() {
        return webEngine;
    }

    public Capabilities getAttachedCapabilities() {
        return webEngine.getAttachedCapabilities();
    }

    public void setDashboardStatistics(List<AbstractDashboardStatistic> dashboardStatistics) {
        this.dashboardStatistics = dashboardStatistics;
    }

    public List<AbstractDashboardStatistic> getDashboardStatistics() {
        return dashboardStatistics;
    }

}

/* Temporary storage for constantly updated start times */
class CurrentTimeRange {
    public DateTime currentTime;
    public DateTime startToday;
    public DateTime startWeek;
    public DateTime startMonth;
    public DateTime startYear;

}
