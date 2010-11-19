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

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;

/**
 * @author philsmart
 * Allows the storage and invocation of statistical units
 */
public class StatisticsHandler {
	static Logger log = Logger.getLogger(StatisticsHandler.class);

	private List<Statistic> statisticalUnits;
	private List<Entry> entries;

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
			if (statistic.getUnitName().equals(statisticName)){
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
			List<String> params = statistic.getMethodParams();
			Object[] paramsO = new Object[params.size()];
			for (int i=0; i < paramsO.length; i++){
				paramsO[i] = params.get(i);
			}
			return invoke(statistic.getMethodName(),paramsO,statistic);
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

	public void setEntries(List<Entry> entries) {
		this.entries = entries;

	}




}
