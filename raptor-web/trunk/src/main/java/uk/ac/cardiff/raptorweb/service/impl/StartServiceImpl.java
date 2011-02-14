/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.StatisticParameters;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor.GraphPresentation;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor.GraphType;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.CachedStartStatistics;
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

    static Logger log = LoggerFactory.getLogger(StartServiceImpl.class);

    private RaptorWebEngine webEngine;

    /* holds the statistics for the front page gathered from the background worker thread */
    private CachedStartStatistics cachedStartModelToday;
    private CachedStartStatistics cachedStartModelLastWeek;
    private CachedStartStatistics cachedStartModelLastMonth;
    private CachedStartStatistics cachedStartModelLastYear;

    /* The chart processor which converts Aggregator Charts into RaptorWeb charts for outputting */
    private ChartProcessor chartProcessor;

    public StartServiceImpl() {
	cachedStartModelToday = new CachedStartStatistics();
	cachedStartModelLastWeek = new CachedStartStatistics();
	cachedStartModelLastMonth = new CachedStartStatistics();
	cachedStartModelLastYear = new CachedStartStatistics();
    }

    public void generateStatisticsBackground() {
	log.info("Generating background statistics for the start page, using {}", this);
	CurrentTimeRange currentTimeRange = getTimeRanges();

	log.debug("Background start page worker getting today");
	generateStatistics(cachedStartModelToday.getCached(), currentTimeRange.currentTime, currentTimeRange.startToday);
	log.debug("Background start page worker getting last week");
	generateStatistics(cachedStartModelLastWeek.getCached(), currentTimeRange.currentTime, currentTimeRange.startWeek);
	log.debug("Background start page worker getting last month");
	generateStatistics(cachedStartModelLastMonth.getCached(), currentTimeRange.currentTime, currentTimeRange.startMonth);
	log.debug("Background start page worker getting last year");
	generateStatistics(cachedStartModelLastYear.getCached(), currentTimeRange.currentTime, currentTimeRange.startYear);

	log.info("Generating background statistics for the start page...done");
    }

    @Override
    public void generateStatistics(WebSession websession) {
	log.debug("Getting start statistics for {} from {}", websession.getStartmodel().getStatsRangeSelector(), this);

	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTMONTH)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastMonth.getCached());
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTWEEK)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastWeek.getCached());
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.TODAY)
	    websession.getStartmodel().setStartStatistics(cachedStartModelToday.getCached());
	if (websession.getStartmodel().getStatsRangeSelector() == StartModel.TimeRange.LASTYEAR)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastYear.getCached());

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
    private void generateStatistics(StartStatistics startstats, DateTime currentDateTime, DateTime chosenStartTime) {

	List<StatisticalUnitInformation> statisticalUnits = getStatisticalUnits();
	log.debug("Found {} statistics", statisticalUnits.size());
	// check for statistical units named numberOfAuthenticationsPer, numberOfUnqiueUsersPer, hence the MUA must support this
	StatisticalUnitInformation numberOfAuthenticationsPerUnitInformation = null;
	StatisticalUnitInformation numberOfUniqueUsersPerUnitInformation = null;
	StatisticalUnitInformation topFiveResources = null;
	StatisticalUnitInformation bottomFiveResources = null;
	StatisticalUnitInformation numberOfAuthenticationsPerIntervalNumber = null;
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
	    }
	}
	log.debug("Using statistic {} to find number of authentications per", numberOfAuthenticationsPerUnitInformation);
	log.debug("Using statistic {} to find number of unique users per", numberOfUniqueUsersPerUnitInformation);
	log.debug("Using statistic {} to find number top five resources", topFiveResources);
	log.debug("Using statistic {} to find number bottom five resources", topFiveResources);
	log.debug("Using statistic {} to find number of authentication in 12 intervals", numberOfAuthenticationsPerIntervalNumber);

	AggregatorGraphModel numberOfAuthentications = null;
	if (numberOfAuthenticationsPerUnitInformation != null) {
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfAuthentications = webEngine.updateAndInvokeStatisticalUnit(numberOfAuthenticationsPerUnitInformation);
	}

	AggregatorGraphModel numberOfUniqueUsers = null;
	if (numberOfUniqueUsersPerUnitInformation != null) {
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfUniqueUsers = webEngine.updateAndInvokeStatisticalUnit(numberOfUniqueUsersPerUnitInformation);

	}

	AggregatorGraphModel topFiveResourcesModel = null;
	if (topFiveResources != null) {
	    topFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    topFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    topFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(topFiveResources);
	}

	AggregatorGraphModel bottomFiveResourcesModel = null;
	if (bottomFiveResources != null) {
	    bottomFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    bottomFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    bottomFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(bottomFiveResources);
	}

	AggregatorGraphModel numberOfAuthenticationsPerIntervalNumberModel = null;
	if (numberOfAuthenticationsPerIntervalNumber != null) {
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setStartTime(chosenStartTime);
	    numberOfAuthenticationsPerIntervalNumberModel = webEngine.updateAndInvokeStatisticalUnit(numberOfAuthenticationsPerIntervalNumber);
	}

	// Now set the statistics values ------

	if (numberOfAuthentications != null && numberOfUniqueUsers != null && topFiveResourcesModel != null && bottomFiveResourcesModel != null && numberOfAuthenticationsPerIntervalNumberModel != null) {
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

	    if (bottomFiveResourcesModel != null) {
		RaptorTableChartModel table = getChartProcessor().constructRaptorTableChartModel(bottomFiveResourcesModel);
		startstats.setBottomFiveResouces(table);
	    }
	    if (numberOfAuthenticationsPerIntervalNumberModel != null) {
		RaptorJFreeChartModel jfreeChart = getChartProcessor().constructJFreeGraph(GraphPresentation.FRONT,GraphType.AREA,numberOfAuthenticationsPerIntervalNumberModel,1270,300,(chosenStartTime.toString("ddMMyyyHH-mm")+"-"+currentDateTime.toString("ddMMyyyHH-mm")));
		startstats.setHeadlineGraph(jfreeChart);
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

}

/* Temporary storage for constantly updated start times */
class CurrentTimeRange {
    public DateTime currentTime;
    public DateTime startToday;
    public DateTime startWeek;
    public DateTime startMonth;
    public DateTime startYear;

}
