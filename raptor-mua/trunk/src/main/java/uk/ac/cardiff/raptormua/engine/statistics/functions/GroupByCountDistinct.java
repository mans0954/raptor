package uk.ac.cardiff.raptormua.engine.statistics.functions;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.raptormua.engine.statistics.ObservationSeries;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;
import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;

public class GroupByCountDistinct extends Statistic{
	
	static Logger log = LoggerFactory.getLogger(GroupByCountDistinct.class);
	
	public Boolean performStatistic(ArrayList<MethodParameter> methodParams, String sqlWhere) throws StatisticalUnitException {
		
		if (methodParams.size()!=2)
			throw new StatisticalUnitException("incorrect method parameters");
		
		String groupByField = methodParams.get(0).getValue();
		String countDistinctField = methodParams.get(1).getValue();
		
		log.debug("Performing groupByFrequency Statistical Operation");
		log.debug("Params for method:  {},{}", this.getClass().getSimpleName(), statisticParameters.getUnitName());
		log.debug("Grouping field: {}, count By distinct field {}", groupByField, countDistinctField);

		DateTime start = startingTime();
		DateTime end = endingTime();
		log.debug("groupByFrequency between [start:{}] [end:{}]", start, end);
		//need to find the lowest level class from which to perform these statistics
		String tableName = ReflectionHelper.determineSubclassForMethods(groupByField,countDistinctField);

		log.debug("Select {}, tableName {}", groupByField, tableName);
		List results = getEntryHandler().query(
				"select " + groupByField + ",count(distinct "+countDistinctField+") from " + tableName + " where (eventTime between '" + start
						+ "' and '" + end + "') group by (" + groupByField + ")");


		ArrayList<Group> groups = new ArrayList();
		int testCount = 0;
		for (Object result : results) {
			Object[] resultAsArray = (Object[]) result;
			Group group = new Group();
			group.setValue((Integer) resultAsArray[1]);
			group.setGroupName((String) resultAsArray[0]);
			groups.add(group);
			testCount += group.getValue();
		}


		log.debug("Entries: {}, total in buckets:{} ", this.getEntryHandler().getNumberOfEntries(), testCount);


		if (groups.size() == 0)
			return false;

		// finished successfully, no exception thrown
		ObservationSeries series=  new ObservationSeries();
		series.setObservations(groups.toArray(new Group[0]));
		getObservationSeries().add(series);

		return true;

	}

	@Override
	public void setStatisticParameters(StatisticParameters statisticParameters) {
		List<MethodParameter> methodParams = statisticParameters.getMethodParams();
		if (methodParams.size()==2){
			methodParams.get(0).setParameterName("Group By Field");
			methodParams.get(0).setParameterType(ParameterType.FIELD);			
			methodParams.get(1).setParameterName("Count Distinct Field");
			methodParams.get(1).setParameterType(ParameterType.FIELD);
			
		}
		else{
			log.error("Unable to set parameter type for statistic {}, incorrect number of parameters",this.getClass().getSimpleName());
		}
		this.statisticParameters = statisticParameters;
		
	}

}
