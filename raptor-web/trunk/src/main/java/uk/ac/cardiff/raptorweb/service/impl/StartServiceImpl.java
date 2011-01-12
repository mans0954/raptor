/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import org.mortbay.log.Log;
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
    public void generateStatistics(StartModel startmodel) {
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
	    AggregatorGraphModel numberOfAuthentications = webEngine.invokeStatisticalUnit(numberOfAuthenticationsPerUnitInformation);
	    RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(numberOfAuthentications);
	    // should only have one result
	    if (table.getRows().size() == 1) {
		if (table.getRows().get(0).getValue() instanceof Double) {
		    startmodel.setNumberOfAuthenticationsPer(((Double) table.getRows().get(0).getValue()));
		}
	    }
	}

	if (numberOfUniqueUsersPerUnitInformation != null) {
	    AggregatorGraphModel numberOfUniqueUsers = webEngine.invokeStatisticalUnit(numberOfUniqueUsersPerUnitInformation);
	    if (numberOfUniqueUsers != null) {
		RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(numberOfUniqueUsers);
		// each result shows one distinct value, so number of results show number of distinct values
		log.debug("Number of rows: {}",table.getRows().size());
		if (table.getRows() != null) {
		    startmodel.setNumberOfUniqueAuthenticationsPer(table.getRows().size());
		}
	    }

	}

	if (topFiveResources != null) {
	    AggregatorGraphModel topFiveResourcesModel = webEngine.invokeStatisticalUnit(topFiveResources);
	    RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(topFiveResourcesModel);
	    startmodel.setTopFiveResouces(table);
	}

	if (bottomFiveResources != null) {
	    AggregatorGraphModel bottomFiveResourcesModel = webEngine.invokeStatisticalUnit(bottomFiveResources);
	    RaptorTableChartModel table = ChartProcessor.constructRaptorTableChartModel(bottomFiveResourcesModel);
	    startmodel.setBottomFiveResouces(table);
	}

	if (numberOfAuthenticationsPerIntervalNumber != null) {
	    AggregatorGraphModel numberOfAuthenticationsPerIntervalNumberModel = webEngine.invokeStatisticalUnit(numberOfAuthenticationsPerIntervalNumber);
	    RaptorGraphModel graph = ChartProcessor.constructRaptorGraphModel(numberOfAuthenticationsPerIntervalNumberModel);
	    //blank the middle group labels, as no axis needed
	    for (int i =1; i < graph.getGroupLabels().size()-1;i++)
		graph.getGroupLabels().set(i,new String(""));
	    startmodel.setHeadlineGraph(graph);
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
