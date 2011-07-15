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
import uk.ac.cardiff.raptor.runtimeutils.ReflectionException;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptormua.engine.statistics.ObservationSeries;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticalUnitException;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;

public class GroupByCountDistinct extends Statistic {

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(GroupByCountDistinct.class);

	public Boolean performStatistic(List<MethodParameter> methodParams, String sqlWhere) throws StatisticalUnitException {

		if (methodParams.size() != 2)
			throw new StatisticalUnitException("incorrect method parameters");

		String groupByField = methodParams.get(0).getValue();
		String countDistinctField = methodParams.get(1).getValue();

		log.debug("Performing GroupByCountDistinct Statistical Operation");
		log.debug("Params for method:  {},{}", this.getClass().getSimpleName(), statisticParameters.getUnitName());
		log.debug("Grouping field: {}, count By distinct field {}", groupByField, countDistinctField);

		DateTime start = startingTime();
		DateTime end = endingTime();
		log.debug("groupByFrequency between [start:{}] [end:{}]", start, end);

		String tableName = statisticParameters.getEventType().getHibernateSimpleClassName();

		log.debug("Select {}, tableName {}", groupByField, tableName);

		String resourceCategoryFilter = statisticParameters.getResourceCategory().getSql();
		log.debug("Resource Category Filter {}",resourceCategoryFilter);

		String query="";

		if (sqlWhere.equals("")) {
			query = "select " + groupByField + ",count(distinct " + countDistinctField + ") from " + tableName + " where (eventTime between ?" +
			" and ?) and resourceIdCategory "+resourceCategoryFilter+" group by (" + groupByField + ")";
		} else {
			query = "select " + groupByField + ",count(distinct " + countDistinctField + ") from " + tableName + " where (eventTime between ?" +
			" and ?) and resourceIdCategory "+resourceCategoryFilter+" and "+sqlWhere+" group by (" + groupByField + ")";
		}

		Object[] params = new Object[]{start,end};

		List results = getEntryHandler().query(query,params);

		ArrayList<Group> groups = new ArrayList<Group>();
		int testCount = 0;
		for (Object result : results) {
			Object[] resultAsArray = (Object[]) result;
			Group group = new Group();
			group.setValue((Long) resultAsArray[1]);
			group.setGroupName((String) resultAsArray[0]);
			groups.add(group);
			testCount += group.getValue();
		}

		log.debug("Entries: {}, total in buckets:{} ", this.getEntryHandler().getNumberOfEvents(), testCount);

		if (groups.size() == 0)
			return false;

		// finished successfully, no exception thrown
		ObservationSeries series = new ObservationSeries();
		series.setObservations(groups.toArray(new Group[0]));
		getObservationSeries().add(series);

		return true;

	}

	@Override
	public void setStatisticParameters(StatisticParameters statisticParameters) {
		List<MethodParameter> methodParams = statisticParameters.getMethodParams();
		if (methodParams.size() == 2) {
			methodParams.get(0).setParameterName("Group By Field");
			methodParams.get(0).setParameterType(ParameterType.FIELD);
			methodParams.get(1).setParameterName("Count Distinct Field");
			methodParams.get(1).setParameterType(ParameterType.FIELD);

		} else {
			log.error("Unable to set parameter type for statistic {}, incorrect number of parameters", this.getClass().getSimpleName());
		}
		this.statisticParameters = statisticParameters;

	}

}
