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
package uk.ac.cardiff.raptormua.engine.statistics.functions;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptormua.engine.statistics.ObservationSeries;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;

public class GroupBy extends Statistic{

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(GroupByCountDistinct.class);

	/**
	 * Perform the groupByField statistic. This statistic returns a list of
	 * distinct values of the given <code>groupByField</code> field. In contrast
	 * to the <code>groupByFrequency</code> method, it does not count the
	 * occurrence that each distinct value occurs. It is the responsibility of
	 * this method to throw a <code>StatisticalUnitException</code> if the code
	 * logic fails, or return false if the statistic functioned correctly, but
	 * there are no valid observations, or true if the statistic succeeds and
	 * there are valid observations.
	 *
	 * @param groupByField
	 * @return true if the statistic succeeds successfully
	 * @throws StatisticalUnitException
	 */
	public Boolean  performStatistic(ArrayList<MethodParameter> methodParams, String sqlWhere) throws StatisticalUnitException {

		if (methodParams.size()!=1)
			throw new StatisticalUnitException("incorrect method parameters");

		String groupByField = methodParams.get(0).getValue();

		log.debug("Performing groupByFrequency Statistical Operation");
		log.debug("Params for method:  {},{}", this.getClass().getSimpleName(), statisticParameters.getUnitName());
		log.debug("Grouping field: {}", groupByField);

		DateTime start = startingTime();
		DateTime end = endingTime();
		log.debug("groupBy between [start:{}] [end:{}]", start, end);
		String tableName= statisticParameters.getEventType().getHibernateSimpleClassName();
		log.debug("Select {}, tableName {}", groupByField, tableName);

		String resourceCategoryFilter = statisticParameters.getResourceCategory().getSql();
		log.debug("Resource Category Filter {}",resourceCategoryFilter);

		String query="";

		if (sqlWhere.equals("")) {
			query = "select "+groupByField+" from "+tableName+" where (eventTime between ? and ?) and resourceIdCategory "+resourceCategoryFilter+" group by ("+groupByField+")";
		} else {
			query = "select "+groupByField+" from "+tableName+" where (eventTime between ? and ?) and resourceIdCategory "+resourceCategoryFilter+" and "+sqlWhere+" group by ("+groupByField+")";
		}


		Object[] params = new Object[]{start,end};

		List results = getEntryHandler().query(query,params);

		ArrayList<Group> groups = new ArrayList();
		for (Object result : results) {
			Object resultAsArray = (Object) result;
			Group group = new Group();
			group.setValue(0);
			if (resultAsArray!=null){
			    group.setGroupName((String) resultAsArray);
			}
			else{
			    group.setGroupName("NA");
			}
			groups.add(group);

		}


		if (groups.size() == 0)
			return false;

		// finished successfully, no exception thrown
		ObservationSeries series=  new ObservationSeries();
		series.setObservations(groups.toArray(new Group[0]));
		getObservationSeries().add(series);

		// finished successfully, no exception thrown
		return true;

	}

	@Override
	public void setStatisticParameters(StatisticParameters statisticParameters) {
		List<MethodParameter> methodParams = statisticParameters.getMethodParams();
		if (methodParams.size()==1){
			methodParams.get(0).setParameterName("Group By Field");
			methodParams.get(0).setParameterType(ParameterType.FIELD);
		}
		else{
			log.error("Unable to set parameter type for statistic {}, incorrect number of parameters",this.getClass().getSimpleName());
		}
		this.statisticParameters = statisticParameters;

	}

}
