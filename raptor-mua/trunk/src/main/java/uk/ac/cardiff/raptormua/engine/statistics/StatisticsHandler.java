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

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.sql.SQLFilter;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.store.EntryHandler;

/**
 * @author philsmart Allows the storage and invocation of statistical units
 */
public class StatisticsHandler {
    
        /** Class Logger */
        private final Logger log = LoggerFactory.getLogger(StatisticsHandler.class);

	/** List of {@link uk.ac.cardiff.raptormua.engine.statistics.Statistic} that have been registered
	 * with this handler
	 */
        private List<Statistic> statisticalUnits;

        /** A reference to the entry handler that is used to access all underlying events*/
	private EntryHandler entryHandler;

	/** 
	 * Registers a List of statistics with this StatisticsHandler 
	 */
	public void setStatisticalUnits(List<Statistic> statisticalUnits) {
	    for (Statistic stat : statisticalUnits){
	        log.info("Registering statistic {}, role {}",stat.getStatisticParameters().getUnitName(),stat.getStatisticParameters().getType());
	    }
	    this.statisticalUnits = statisticalUnits;
	}

	public List<Statistic> getStatisticalUnits() {
		return statisticalUnits;
	}

	/**
	 * @param statisticName
	 */
	public AggregatorGraphModel peformStatistic(String statisticName) {
		for (Statistic statistic : statisticalUnits) {
			if (statistic.getStatisticParameters().getUnitName().equals(statisticName)) {
				return performStatiticalPipeline(statistic);
			}
		}
		return null;
	}

	private AggregatorGraphModel performStatiticalPipeline(Statistic statistic) {
		statistic.setEntryHandler(entryHandler);
		Boolean success = invoke(statistic);
		log.info("Statistic {} succedded {}",statistic.getStatisticParameters().getUnitName(),success);
		if (success) {
			// now send through post processor
			statistic.postProcess();
			try{
			    AggregatorGraphModel graphModel = statistic.constructGraphModel();
			    statistic.reset();
			    return graphModel;
			}
			catch(Exception e){
			    //must catch this error here, so we can clear the observations that the statistic has generated
			    statistic.reset();
			    log.error("Problem constructing graph model for statistic {}",statistic.getStatisticParameters().getUnitName(),e);
			    e.printStackTrace();
			    return null;
			}
		}
		/* always reset the observationseries for the statistic, so the next execution is not
		*  an accumulation of the ones before it
		*/
		statistic.reset();
		return null;
	}

	/**
	 * @param statistic
	 */
	private Boolean invoke(Statistic statistic) {
		if (this.getEntryHandler() != null)
			log.debug("Working off " + this.getEntryHandler().getNumberOfEntries() + " entries");

		/* stop processing if there are no valid entries */
		if (this.getEntryHandler() == null || this.getEntryHandler().getNumberOfEntries() == 0) {
			log.error("Not enough entries to perform statistic countEntryPerInterval");
			return false;
		}

		try {
			List<MethodParameter> params = statistic.getStatisticParameters().getMethodParams();
			List<Series> listOfSeries = statistic.getStatisticParameters().getSeries();
			boolean success = true;
			for (Series series : listOfSeries){
				String whereClause=series.computeComparisonAsSQL();				
				log.debug("statistical to invoke {}",statistic);
				success = statistic.performStatistic(params, whereClause);
			}
			return success;
		} catch (Exception e) {
			log.error("Failed to invoke statistics {} -> {}",statistic.getStatisticParameters().getUnitName(),e.getMessage());
			e.printStackTrace();
		}
		return false;

	}


	/**
	 * updates a statistical unit based on the values in the
	 * <code>statisticalUnitInformation</code> parameter Not a very good primary
	 * key (unit name) should be something else
	 *
	 * @param statisticalUnitInformation
	 */
	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
		log.info("Updating statistical unit {} ", statisticalUnitInformation.getStatisticParameters().getUnitName());
		Statistic toUpdate = null;
		for (Statistic statistic : statisticalUnits) {
			if (statistic.getStatisticParameters().getUnitName()
					.equals(statisticalUnitInformation.getStatisticParameters().getUnitName()))
				toUpdate = statistic;
		}
		log.debug("Found Statistic {} to update", toUpdate);
		update(toUpdate, statisticalUnitInformation);

	}

	/**
	 * Updates the statistical parameters of the statistic, does not yet handle the post processors
	 *
	 * @param statistic
	 * @param statisticalUnitInformation
	 */
	private void update(Statistic statistic, StatisticalUnitInformation statisticalUnitInformation) {
		statistic.setStatisticParameters(statisticalUnitInformation.getStatisticParameters());
	}

	/**
	 *
	 * @param entryHandler
	 */
	public void setEntryHandler(EntryHandler entryHandler) {
		this.entryHandler = entryHandler;
	}

	/**
	 *
	 * @return
	 */
	public EntryHandler getEntryHandler() {
		return entryHandler;
	}

}
