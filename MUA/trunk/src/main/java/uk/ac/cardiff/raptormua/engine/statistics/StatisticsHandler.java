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
/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

/**
 * @author philsmart
 * Allows the storage and invocation of statistical units
 */
public class StatisticsHandler {
	static Logger log = LoggerFactory.getLogger(StatisticsHandler.class);

	private List<Statistic> statisticalUnits;
	private Set<Entry> entries;

	public void setStatisticalUnits(List<Statistic> statisticalUnits) {
		this.statisticalUnits = statisticalUnits;
	}

	public List<Statistic> getStatisticalUnits() {
		return statisticalUnits;
	}

	/**
	 * @param statisticName
	 */
	public AggregatorGraphModel peformStatistic(String statisticName) {
		for (Statistic statistic : statisticalUnits){
			if (statistic.getStatisticParameters().getUnitName().equals(statisticName)){
				return performStatiticalPipeline(statistic);
			}
		}
		return null;
	}

	private AggregatorGraphModel performStatiticalPipeline(Statistic statistic){
		//on set entries, also perform preprocessing, could be done as an extra method call
		statistic.setEntries(entries);
		Boolean success =  invoke(statistic);
		log.info("Statistic succedded "+success);
		if (success){
			//now send through post processor
			statistic.postProcess();
			return statistic.constructGraphModel();
		}
		return null;
	}

	/**
	 * @param statistic
	 */
	private Boolean invoke(Statistic statistic) {
		try{
			List<String> params = statistic.getStatisticParameters().getMethodParams();
			Object[] paramsO = new Object[params.size()];
			for (int i=0; i < paramsO.length; i++){
				paramsO[i] = params.get(i);
			}
			return invoke(statistic.getStatisticParameters().getMethodName(),paramsO,statistic);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}

	private Boolean invoke(String fieldname, Object[] params, Object object) {
		try {
		    Class id = object.getClass();
		    Class[] paramC = new Class[params.length];
		    for (int i=0; i < params.length;i++){
		    	paramC[i] = params[i].getClass();
		    }
		    log.debug("Calling method: "+fieldname+ " on "+object);
		    Method statisticalMethod = id.getMethod(fieldname, paramC);
		    //log.debug("Trying to Set :"+setter);
		    Boolean success = (Boolean) statisticalMethod.invoke(object, params);
		   // AggregatorGraphModel gmodel = (AggregatorGraphModel) statisticalMethod.invoke(object, params);
		    return success;
		} catch (Throwable e) {
		    log.error("Field name '" + fieldname + "' does not match internal model attribute");
		    e.printStackTrace();
		    // System.exit(1);

		}
		return null;
	    }

	public void setEntries(Set<Entry> entries) {
		this.entries = entries;

	}

	/**
	 * updates a statistical unit based on the values in the <code>statisticalUnitInformation</code> parameter
	 * Not a very good primary key (unit name) should be something else
	 *
	 * @param statisticalUnitInformation
	 */
	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
	    log.info("Updating statistical unit {} ",statisticalUnitInformation.getStatisticParameters().getUnitName());
	    Statistic toUpdate = null;
	    for (Statistic statistic : statisticalUnits){
		if (statistic.getStatisticParameters().getUnitName().equals(statisticalUnitInformation.getStatisticParameters().getUnitName()))
		    toUpdate = statistic;
	    }
	    log.debug("Found Statistic {} to update",toUpdate);
	    update(toUpdate, statisticalUnitInformation);

	}


	/**
	 * Only currently updates certain values, these will need to be completed in the future
	 *
	 * @param statistic
	 * @param statisticalUnitInformation
	 */
	private void update(Statistic statistic, StatisticalUnitInformation statisticalUnitInformation){
	    statistic.getStatisticParameters().setEndTime(statisticalUnitInformation.getStatisticParameters().getEndTimeAsDate());
	    statistic.getStatisticParameters().setStartTime(statisticalUnitInformation.getStatisticParameters().getStartTimeAsDate());
	}




}
