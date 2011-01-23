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
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.CachedStartStatistics;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
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
    
    public void generateStatisticsBackground(){
	log.info("Generating background statistics for the start page, using {}",this);
	CurrentTimeRange currentTimeRange = getTimeRanges();
	if (cachedStartModelToday==null)    
	    cachedStartModelToday= new CachedStartStatistics();
	if (cachedStartModelLastWeek==null)
	    cachedStartModelLastWeek= new CachedStartStatistics();
	if (cachedStartModelLastMonth==null)
	    cachedStartModelLastMonth= new CachedStartStatistics();
	if (cachedStartModelLastYear==null)
	    cachedStartModelLastYear= new CachedStartStatistics();
	
	log.debug("Background start page worker getting today");
	generateStatistics(cachedStartModelToday.getCached(),currentTimeRange.currentTime,currentTimeRange.startToday);
	log.debug("Background start page worker getting last week");
	generateStatistics(cachedStartModelLastWeek.getCached(),currentTimeRange.currentTime,currentTimeRange.startWeek);
	log.debug("Background start page worker getting last month");
	generateStatistics(cachedStartModelLastMonth.getCached(),currentTimeRange.currentTime,currentTimeRange.startMonth);
	log.debug("Background start page worker getting last year");
	generateStatistics(cachedStartModelLastYear.getCached(),currentTimeRange.currentTime,currentTimeRange.startYear);
	
	log.info("Generating background statistics for the start page...done");
    }
    
    @Override
    public void generateStatistics(WebSession websession){
	log.debug("Getting start statistics for {} from {}",websession.getStartmodel().getStatsRangeSelector(),this);
	
	if (websession.getStartmodel().getStatsRangeSelector()==StartModel.TimeRange.LASTMONTH)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastMonth.getCached());
	if (websession.getStartmodel().getStatsRangeSelector()==StartModel.TimeRange.LASTWEEK)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastWeek.getCached());
	if (websession.getStartmodel().getStatsRangeSelector()==StartModel.TimeRange.TODAY)
	    websession.getStartmodel().setStartStatistics(cachedStartModelToday.getCached());
	if (websession.getStartmodel().getStatsRangeSelector()==StartModel.TimeRange.LASTYEAR)
	    websession.getStartmodel().setStartStatistics(cachedStartModelLastYear.getCached());
	
	//so we could output the name of the attached MUA
	Capabilities capabilities = getAttachedCapabilities();
	if (capabilities!=null){
	    websession.getStartmodel().setAttachedMUACapabilities(capabilities);

	}
    }
    
    private CurrentTimeRange getTimeRanges(){
	long currentTimeInMS = System.currentTimeMillis();
	//decide dates to use
	DateTime currentDateTime = new DateTime(currentTimeInMS);
	//log.debug("Time now: "+currentDateTime);
	DateTime today = new DateTime(currentTimeInMS);
	today = today.minusHours(today.getHourOfDay());
	today = today.minusMinutes(today.getMinuteOfHour());
	today = today.minusSeconds(today.getSecondOfMinute());
	//log.debug("Today starts at {} ",today);
	DateTime oneMonthPrevious = currentDateTime.minusMonths(1);
	//log.debug("One Month Previos {}",oneMonthPrevious);
	DateTime oneYearPrevious = currentDateTime.minusYears(1);
	//log.debug("One Year Previous {}",oneYearPrevious);
	DateTime oneWeekPrevious = currentDateTime.minusWeeks(1);
	
	CurrentTimeRange currentRange = new CurrentTimeRange();
	currentRange.startMonth=oneMonthPrevious;
	currentRange.startToday=today;
	currentRange.startWeek=oneWeekPrevious;
	currentRange.startYear=oneYearPrevious;
	currentRange.currentTime = currentDateTime;
	return currentRange;	
	
    }
    

    /**
     * Adds the following statistical information from the attached MUA: 
     * 1. Number of authentications per <RANGE>
     * 2. 
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

	if (numberOfAuthenticationsPerUnitInformation != null) {
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfAuthenticationsPerUnitInformation.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel numberOfAuthentications = webEngine.updateAndInvokeStatisticalUnit(numberOfAuthenticationsPerUnitInformation);
	    if (numberOfAuthentications!=null){
        	    RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(numberOfAuthentications);
        	    // should only have one result
        	    if (table.getRows().size() == 1) {
        		if (table.getRows().get(0).getValue() instanceof Double) {
        		    startstats.setNumberOfAuthenticationsPer(((Double) table.getRows().get(0).getValue()));
        		}
        	    }
	    }
	    else{
		startstats.setNumberOfAuthenticationsPer(0);
	    }
	}

	if (numberOfUniqueUsersPerUnitInformation != null) {
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfUniqueUsersPerUnitInformation.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel numberOfUniqueUsers = webEngine.updateAndInvokeStatisticalUnit(numberOfUniqueUsersPerUnitInformation);
	    if (numberOfUniqueUsers!=null){
        	    if (numberOfUniqueUsers != null) {
        		RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(numberOfUniqueUsers);
        		// each result shows one distinct value, so number of results show number of distinct values
        		log.debug("Number of rows: {}",table.getRows().size());
        		if (table.getRows() != null) {
        		    startstats.setNumberOfUniqueAuthenticationsPer(table.getRows().size());
        		}
        	    }
	    }
	    else{
		startstats.setNumberOfUniqueAuthenticationsPer(0);
	    }

	}

	if (topFiveResources != null) {
	    topFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    topFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel topFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(topFiveResources);
	    if (topFiveResourcesModel!=null){
		RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(topFiveResourcesModel);
		startstats.setTopFiveResouces(table);
	    }
	    else
		startstats.setTopFiveResouces(null);
	}

	if (bottomFiveResources != null) {
	    bottomFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    bottomFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel bottomFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(bottomFiveResources);
	    if (bottomFiveResourcesModel!=null){
		RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(bottomFiveResourcesModel);
		startstats.setBottomFiveResouces(table);
	    }
	    else
		startstats.setBottomFiveResouces(null);

	}

	if (numberOfAuthenticationsPerIntervalNumber != null) {
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setEndTime(currentDateTime);
	    numberOfAuthenticationsPerIntervalNumber.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel numberOfAuthenticationsPerIntervalNumberModel = webEngine.updateAndInvokeStatisticalUnit(numberOfAuthenticationsPerIntervalNumber);
	    if (numberOfAuthenticationsPerIntervalNumberModel!=null){
        	    RaptorGraphModel graph = ChartProcessor.constructRaptorGraphModel(numberOfAuthenticationsPerIntervalNumberModel);
        	    //blank some of the labels for display reasons
        	    for (int i =0; i < graph.getGroupLabels().size();i++){
        		if ((i+1)%10!=0 && i!=1)
        		    graph.getGroupLabels().set(i,new String(""));
        	    }
        	    startstats.setHeadlineGraph(graph);
	    }
	    else
		startstats.setHeadlineGraph(null);
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


}

/* Temporary storage for constantly updated start times */
class CurrentTimeRange{
    public DateTime currentTime;
    public DateTime startToday;
    public DateTime startWeek;
    public DateTime startMonth;
    public DateTime startYear;    

}
