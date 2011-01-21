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
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.StartModel;
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

    /**
     * Adds the following statistical information from the attached MUA: 1. Number of authentications per <RANGE>
     */
    @Override
    public void generateStatistics(WebSession websession) {
	StartModel startmodel = websession.getStartmodel();
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
	DateTime chosenStartTime = null;
	if (startmodel.getStatsRangeSelector()==StartModel.TimeRange.LASTMONTH)
	    chosenStartTime = oneMonthPrevious;
	if (startmodel.getStatsRangeSelector()==StartModel.TimeRange.LASTWEEK)
	    chosenStartTime = oneWeekPrevious;
	if (startmodel.getStatsRangeSelector()==StartModel.TimeRange.TODAY)
	    chosenStartTime = today;
	if (startmodel.getStatsRangeSelector()==StartModel.TimeRange.LASTYEAR)
	    chosenStartTime = oneYearPrevious;

	// get all the stats
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
        		    startmodel.setNumberOfAuthenticationsPer(((Double) table.getRows().get(0).getValue()));
        		}
        	    }
	    }
	    else{
		startmodel.setNumberOfAuthenticationsPer(0);
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
        		    startmodel.setNumberOfUniqueAuthenticationsPer(table.getRows().size());
        		}
        	    }
	    }
	    else{
		startmodel.setNumberOfUniqueAuthenticationsPer(0);
	    }

	}

	if (topFiveResources != null) {
	    topFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    topFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel topFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(topFiveResources);
	    if (topFiveResourcesModel!=null){
		RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(topFiveResourcesModel);
		startmodel.setTopFiveResouces(table);
	    }
	    else
		startmodel.setTopFiveResouces(null);
	}

	if (bottomFiveResources != null) {
	    bottomFiveResources.getStatisticParameters().setEndTime(currentDateTime);
	    bottomFiveResources.getStatisticParameters().setStartTime(chosenStartTime);
	    AggregatorGraphModel bottomFiveResourcesModel = webEngine.updateAndInvokeStatisticalUnit(bottomFiveResources);
	    if (bottomFiveResourcesModel!=null){
		RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(bottomFiveResourcesModel);
		startmodel.setBottomFiveResouces(table);
	    }
	    else
		startmodel.setBottomFiveResouces(null);

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
        	    startmodel.setHeadlineGraph(graph);
	    }
	    else
		startmodel.setHeadlineGraph(null);
	}

	Capabilities capabilities = getAttachedCapabilities();
	if (capabilities!=null){
	    startmodel.setAttachedMUACapabilities(capabilities);

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
