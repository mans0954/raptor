package uk.ac.cardiff.raptormua.engine.statistics.functions;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.raptormua.engine.statistics.ObservationSeries;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;
import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;

public class GroupByFrequency extends Statistic{
	
	static Logger log = LoggerFactory.getLogger(GroupByFrequency.class);
	
	
	/**
	 * Perform the groupByField statistic. This statistic counts the frequency
	 * that each distinct value of the given <code>groupByField</code> occurs in
	 * the entry set. It is the responsibility of this method to throw a
	 * <code>StatisticalUnitException</code> if the code logic fails, or return
	 * false if the statistic functioned correctly, but there are no valid
	 * observations, or true if the statistic succeeds and there are valid
	 * observations.
	 *
	 *
	 * @param groupByField
	 * @return
	 * @throws StatisticalUnitException
	 */
	public Boolean performStatistic(ArrayList<MethodParameter> methodParams, String sqlWhere) throws StatisticalUnitException {
	
		if (methodParams.size()!=1)
			throw new StatisticalUnitException("incorrect method parameters");
		
		String groupByField = methodParams.get(0).getValue();
		
		log.debug("Performing groupByFrequency Statistical Operation");
		log.debug("Params for method:  {},{}", this.getClass().getSimpleName(), statisticParameters.getUnitName());
		log.debug("Grouping field: {}", groupByField);

		DateTime start = startingTime();
		DateTime end = endingTime();
		log.debug("groupByFrequency between [start:{}] [end:{}]", start, end);
		String tableName = ReflectionHelper.findEntrySubclassForMethod(groupByField);
		log.debug("Select {}, tableName {}", groupByField, tableName);
		List results = getEntryHandler().query(
				"select " + groupByField + ",count(*) from " + tableName + " where (eventTime between '" + start
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

		/*
		 * test count should equal the number of entries unless there is a
		 * reminder as this has not been catered for yet.
		 */
		log.debug("Entries: {}, total in buckets:{} ", this.getEntryHandler().getNumberOfEntries(), testCount);

		// add the series label or if none specified, add a default
//		if (statisticParameters.getSeries().getSeriesLabel() == null)
//			statisticParameters.getSeries().setSeriesLabelFormatted("Number of Events Grouped By " + groupByField);
//		else {
//			statisticParameters.getSeries().setSeriesLabelFormatted(statisticParameters.getSeries().getSeriesLabel());
//		}

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
