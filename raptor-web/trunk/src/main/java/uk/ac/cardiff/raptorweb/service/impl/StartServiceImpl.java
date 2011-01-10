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
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.StartModel;
import uk.ac.cardiff.raptorweb.service.StartService;

/**
 * @author philsmart
 *
 */
public class StartServiceImpl implements StartService{

    static Logger log = LoggerFactory.getLogger(StartServiceImpl.class);


    private RaptorWebEngine webEngine;

    /**
     * Adds the following statistical information from the attached MUA:
     * 1. Number of authentications per <RANGE>
     */
    @Override
    public void generateStatistics(StartModel startmodel) {
	//get all the stats
	List<StatisticalUnitInformation> statisticalUnits = getStatisticalUnits();
	log.debug("Found {} statistics",statisticalUnits.size());
	//check for one named numberOfAuthenticationsPer, hence the MUA must support this
	StatisticalUnitInformation unitInformation = null;
	for (StatisticalUnitInformation unit : statisticalUnits){
	    log.debug("Statistic Type is {}",unit.getStatisticParameters().getType());
	    if (unit.getStatisticParameters().getType()==StatisticParameters.StatisticType.SYSTEM){
		log.debug("Have SYSTEM level statistic {}",unit);
		if (unit.getStatisticParameters().getUnitName().equals("numberOfAuthenticationsPer"))
		    unitInformation = unit;
	    }
	}
	log.debug("Using statistic {} to find number of authentications per",unitInformation);
	if (unitInformation!=null){
	    AggregatorGraphModel numberOfAuthentications = webEngine.invokeStatisticalUnit(unitInformation);
	    startmodel.setNumberOfAuthenticationsPer(10);
	}
	startmodel.setNumberOfAuthenticationsPer(0);
    }

    private List getStatisticalUnits(){
	return webEngine.getStatisticalUnits();
    }

    public void setWebEngine(RaptorWebEngine webEngine) {
	this.webEngine = webEngine;
    }

    public RaptorWebEngine getWebEngine() {
	return webEngine;
    }

}
