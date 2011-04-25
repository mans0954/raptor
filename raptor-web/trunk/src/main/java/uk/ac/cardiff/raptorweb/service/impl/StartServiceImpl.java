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

    /** holds the statistics for the front page gathered from the background worker thread */
    private CachedStartStatistics cachedStartModelTodayShib;
    private CachedStartStatistics cachedStartModelLastWeekShib;
    private CachedStartStatistics cachedStartModelLastMonthShib;
    private CachedStartStatistics cachedStartModelLastYearShib;

    private CachedStartStatistics cachedStartModelTodayEzproxy;
    private CachedStartStatistics cachedStartModelLastWeekEzproxy;
    private CachedStartStatistics cachedStartModelLastMonthEzproxy;
    private CachedStartStatistics cachedStartModelLastYearEzproxy;

    public StartServiceImpl() {
        cachedStartModelTodayShib = new CachedStartStatistics();
        cachedStartModelLastWeekShib = new CachedStartStatistics();
        cachedStartModelLastMonthShib = new CachedStartStatistics();
        cachedStartModelLastYearShib = new CachedStartStatistics();

        cachedStartModelTodayEzproxy = new CachedStartStatistics();
        cachedStartModelLastWeekEzproxy = new CachedStartStatistics();
        cachedStartModelLastMonthEzproxy = new CachedStartStatistics();
        cachedStartModelLastYearEzproxy = new CachedStartStatistics();
    }

    public void generateStatisticsBackground() {
        log.info("Generating background statistics for the start page, using {}", this);
        CurrentTimeRange currentTimeRange = getTimeRanges();

        StatisticParameters.EventType shibEventType = StatisticParameters.EventType.SHIBBOLETH_AUTHENTICATION;
        StatisticParameters.EventType ezproxyEventType = StatisticParameters.EventType.EZPROXY_AUTHENTICATION;

        log.debug("Background start page worker getting today");
        generateStatistics(cachedStartModelTodayShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, shibEventType);
        generateStatistics(cachedStartModelTodayEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday, ezproxyEventType);
        log.debug("Background start page worker getting last week");
        generateStatistics(cachedStartModelLastWeekShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, shibEventType);
        generateStatistics(cachedStartModelLastWeekEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek, ezproxyEventType);
        log.debug("Background start page worker getting last month");
        generateStatistics(cachedStartModelLastMonthShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, shibEventType);
        generateStatistics(cachedStartModelLastMonthEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth, ezproxyEventType);
        log.debug("Background start page worker getting last year");
        generateStatistics(cachedStartModelLastYearShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, shibEventType);
        generateStatistics(cachedStartModelLastYearEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear, ezproxyEventType);

        log.info("Generating background statistics for the start page...done");
    }

    @Override
    public void generateStatistics(WebSession websession) {
        log.debug("Getting start statistics for {} from {}", websession.getStartmodel().getStatsRangeSelector(), this);

        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthShib.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekShib.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayShib.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearShib.getCached());

        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthEzproxy.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekEzproxy.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayEzproxy.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastYearEzproxy.getCached());

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
    private void generateStatistics(StartStatistics startstats, DateTime currentDateTime, DateTime chosenStartTime, StatisticParameters.EventType eventType) {

        List<StatisticalUnitInformation> statisticalUnits = getStatisticalUnits();
        log.info("Dashboard statistics are to be computed from a possible {} MUA statistics", statisticalUnits.size());

        int computed=0;
        int toCompute =0;
        for (AbstractDashboardStatistic dashboardStatistic : dashboardStatistics) {
            if (dashboardStatistic.isEnabled() && dashboardStatistic.getEventType()==eventType) {
                toCompute++;
                for (StatisticalUnitInformation unit : statisticalUnits) {
                    if (unit.getStatisticParameters().getType() == StatisticParameters.StatisticType.SYSTEM) {
                        if (unit.getStatisticParameters().getUnitName().equals(dashboardStatistic.getStatisticalUnitName())) {
                            unit.getStatisticParameters().setEndTime(currentDateTime);
                            unit.getStatisticParameters().setStartTime(chosenStartTime);
                            unit.getStatisticParameters().setEventType(dashboardStatistic.getEventType());
                            log.debug("Invoking statistic {} for the dasboard page",unit.getStatisticParameters().getUnitName());
                            AggregatorGraphModel model = webEngine.updateAndInvokeStatisticalUnit(unit);
                            try {
                                Object result = dashboardStatistic.getProcessedStatistic(model,unit);
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
        log.info("{} out of a possible {} dashboard statistics computed",computed,toCompute);
        startstats.setAccurateOf(new DateTime(System.currentTimeMillis()));

    }

    private void cacheStatistic(StartStatistics startstats, Object result, StartStatistics.StartStatisticType statisticType) throws ClassCastException  {
        log.debug("Setting cached start statistic, for type {}",statisticType);
        
        if (statisticType==null){
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
