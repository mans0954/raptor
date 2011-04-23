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
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.CachedStartStatistics;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;
import uk.ac.cardiff.raptorweb.model.DashboardStatistic;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.StartModel;
import uk.ac.cardiff.raptorweb.model.StartStatistics;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.model.StartModel.TimeRange;
import uk.ac.cardiff.raptorweb.service.StartService;

/**
 * @author philsmart
 *
 */
public class StartServiceImpl implements StartService {
    //TODO start or dashboard stats should not be hard coded
    
    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(StartServiceImpl.class);

    /** The engine that is delegated to for all common internal functions of RaptorWeb*/
    private RaptorWebEngine webEngine;
    
    private List<DashboardStatistic> dashboardStatistics;
    

    /** holds the statistics for the front page gathered from the background worker thread */
    private CachedStartStatistics cachedStartModelTodayShib;
    private CachedStartStatistics cachedStartModelLastWeekShib;
    private CachedStartStatistics cachedStartModelLastMonthShib;
    private CachedStartStatistics cachedStartModelLastYearShib;
    
    private CachedStartStatistics cachedStartModelTodayEzproxy;
    private CachedStartStatistics cachedStartModelLastWeekEzproxy;
    private CachedStartStatistics cachedStartModelLastMonthEzproxy;
    private CachedStartStatistics cachedStartModelLastYearEzproxy;

    /** The chart processor which converts Aggregator Charts into RaptorWeb charts for outputting */
    private ChartProcessor chartProcessor;

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
	generateStatistics(cachedStartModelTodayShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday,shibEventType);
	generateStatistics(cachedStartModelTodayEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday,ezproxyEventType);
	log.debug("Background start page worker getting last week");
	generateStatistics(cachedStartModelLastWeekShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek,shibEventType);
	generateStatistics(cachedStartModelLastWeekEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek,ezproxyEventType);
	log.debug("Background start page worker getting last month");
	generateStatistics(cachedStartModelLastMonthShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth,shibEventType);
	generateStatistics(cachedStartModelLastMonthEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth,ezproxyEventType);
	log.debug("Background start page worker getting last year");
	generateStatistics(cachedStartModelLastYearShib.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear,shibEventType);
	generateStatistics(cachedStartModelLastYearEzproxy.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear,ezproxyEventType);

	log.info("Generating background statistics for the start page...done");
    }

    @Override
    public void generateStatistics(WebSession websession) {
	log.debug("Getting start statistics for {} from {}", websession.getStartmodel().getStatsRangeSelector(), this);

	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
	        websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthShib.getCached());
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekShib.getCached());
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
	    websession.getStartmodel().setStartStatistics(cachedStartModelTodayShib.getCached());
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.SHIBBOLETH_AUTHENTICATION)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastYearShib.getCached());
	
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastMonthEzproxy.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelLastWeekEzproxy.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
            websession.getStartmodel().setStartStatistics(cachedStartModelTodayEzproxy.getCached());
        if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR && 
                websession.getStartmodel().getEventType() == StartModel.EventType.EZPROXY_AUTHENTICATION)
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
	log.debug("Found {} statistics", statisticalUnits.size());
	// check for statistical units named numberOfAuthenticationsPer, numberOfUnqiueUsersPer, hence the MUA must support this
	StatisticalUnitInformation numberOfAuthenticationsPerUnitInformation = null;
	StatisticalUnitInformation numberOfUniqueUsersPerUnitInformation = null;
	StatisticalUnitInformation topFiveResources = null;
	StatisticalUnitInformation bottomFiveResources = null;
	StatisticalUnitInformation numberOfAuthenticationsPerIntervalNumber = null;
	StatisticalUnitInformation numberOfUniqueAuthenticationsPerSP =null;
	for (StatisticalUnitInformation unit : statisticalUnits) {
	    if (unit.getStatisticParameters().getType() == StatisticParameters.StatisticType.SYSTEM) {
		if (unit.getStatisticParameters().getUnitName().equals("numberOfAuthenticationsPer"))
		    numberOfAuthenticationsPerUnitInformation = unit;
		if (unit.getStatisticParameters().getUnitName().equals("numberOfUnqiueUsersPer"))
		    numberOfUniqueUsersPerUnitInformation = unit;
		if (unit.getStatisticParameters().getUnitName().equals("top5Resources"))
		    topFiveResources = unit;
		if (unit.getStatisticParameters().getUnitName().equals("bottom5Resources"))
		    bottomFiveResources = unit;
		if (unit.getStatisticParameters().getUnitName().equals("numberOfAuthenticationsPerIntervalNumber"))
		    numberOfAuthenticationsPerIntervalNumber = unit;
		if (unit.getStatisticParameters().getUnitName().equals("numberOfUniqueAuthenticationsPerSP"))
		    numberOfUniqueAuthenticationsPerSP = unit;
	    }
	}
	log.debug("Using statistic {} to find number of authentications per", numberOfAuthenticationsPerUnitInformation);
	log.debug("Using statistic {} to find number of unique users per", numberOfUniqueUsersPerUnitInformation);
	log.debug("Using statistic {} to find number top five resources", topFiveResources);
	log.debug("Using statistic {} to find number bottom five resources", topFiveResources);
	log.debug("Using statistic {} to find number of authentication in 12 intervals", numberOfAuthenticationsPerIntervalNumber);
	log.debug("Using statistic {} to find number of unique authentication per service provider", numberOfUniqueAuthenticationsPerSP);

	AggregatorGraphModel numberOfAuthentications = null;
	if (numberOfAuthenticationsPerUnitInformation != null) {
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setEventType(eventType);
	    numberOfAuthentications = webEngine.updateAndInvokeStatisticalUnit(numberOfAuthenticationsPerUnitInformation);
	}

	AggregatorGraphModel numberOfUniqueUsers = null;
	if (numberOfUniqueUsersPerUnitInformation != null) {
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setEventType(eventType);
	    numberOfUniqueUsers = webEngine.updateAndInvokeStatisticalUnit(numberOfUniqueUsersPerUnitInformation);

	}

	AggregatorGraphModel topFiveResourcesModel = null;
	if (topFiveResources != null) {
	    topFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    topFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    topFiveResources.getStatisticParameters().setEventType(eventType);
	    topFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(topFiveResources);
	}


	AggregatorGraphModel numberOfAuthenticationsPerIntervalNumberModel = null;
	if (numberOfAuthenticationsPerIntervalNumber != null) {
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setEventType(eventType);
	    numberOfAuthenticationsPerIntervalNumberModel = webEngine.updateAndInvokeStatisticalUnit(numberOfAuthenticationsPerIntervalNumber);
	}

	AggregatorGraphModel uniqueAuthsPerSPModel =null;
	if (numberOfUniqueAuthenticationsPerSP !=null){
	    numberOfUniqueAuthenticationsPerSP.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfUniqueAuthenticationsPerSP.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfUniqueAuthenticationsPerSP.getStatisticParameters().setEventType(eventType);
	    uniqueAuthsPerSPModel = webEngine.updateAndInvokeStatisticalUnit(numberOfUniqueAuthenticationsPerSP);
	}

	// Now set the statistics values ------

	if (numberOfAuthentications != null && numberOfUniqueUsers != null && topFiveResourcesModel != null && numberOfAuthenticationsPerIntervalNumberModel != null && uniqueAuthsPerSPModel!=null) {
	    if (numberOfAuthentications != null) {
		RaptorTableChartModel table = getChartProcessor().constructRaptorTableChartModel(numberOfAuthentications);
		//should only have one series, and one row
		if (table.getTableSeries().size()==1){
        		if (table.getTableSeries().get(0).getRows().size() == 1) {
        		    if (table.getTableSeries().get(0).getRows().get(0).getValue() instanceof Double) {
        			startstats.setNumberOfAuthenticationsPer(((Double) table.getTableSeries().get(0).getRows().get(0).getValue()));
        		    }
        		}
		}
	    }

	    if (numberOfUniqueUsers != null) {
		if (numberOfUniqueUsers != null) {
		    RaptorTableChartModel table = getChartProcessor().constructRaptorTableChartModel(numberOfUniqueUsers);
		    // there should only be one series, and each result shows one distinct value, so number of results show number of distinct values
		    if (table.getTableSeries().size()==1){
        		    if (table.getTableSeries().get(0).getRows() != null) {
        			startstats.setNumberOfUniqueAuthenticationsPer(table.getTableSeries().get(0).getRows().size());
        		    }
		    }
		}
	    }

	    if (topFiveResourcesModel != null) {
		RaptorTableChartModel table = getChartProcessor().constructRaptorTableChartModel(topFiveResourcesModel);
		startstats.setTopFiveResouces(table);
	    }

	    if (numberOfAuthenticationsPerIntervalNumberModel != null) {
		RaptorJFreeChartModel jfreeChart = getChartProcessor().constructJFreeGraph(GraphPresentation.FRONT,ChartType.AREA,numberOfAuthenticationsPerIntervalNumberModel,1270,350,(chosenStartTime.toString("ddMMyyyHH-mm")+"-"+currentDateTime.toString("ddMMyyyHH-mm")));
		startstats.setHeadlineGraph(jfreeChart);
	    }
	    if (uniqueAuthsPerSPModel != null) {
		RaptorTableChartModel table = getChartProcessor().constructRaptorTableChartModel(uniqueAuthsPerSPModel);
		startstats.setTopFiveUniqueUsersPerSP(table);
	    }

	    // set update time on the stats
	    startstats.setAccurateOf(new DateTime(System.currentTimeMillis()));
	} else {
	    log.warn("Did not fetch all statistics for dates {} and {}", chosenStartTime, currentDateTime);
	}

    }

    private List getStatisticalUnits() {
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

    public void setChartProcessor(ChartProcessor chartProcessor) {
	this.chartProcessor = chartProcessor;
    }

    public ChartProcessor getChartProcessor() {
	return chartProcessor;
    }

    public void setDashboardStatistics(List<DashboardStatistic> dashboardStatistics) {
        this.dashboardStatistics = dashboardStatistics;
    }

    public List<DashboardStatistic> getDashboardStatistics() {
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
