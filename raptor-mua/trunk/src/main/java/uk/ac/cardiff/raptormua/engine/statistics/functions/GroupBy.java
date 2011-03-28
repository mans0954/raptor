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

public class GroupBy extends Statistic{
	
	static Logger log = LoggerFactory.getLogger(GroupByCountDistinct.class);

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
	 * @return
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
		String tableName = ReflectionHelper.findEntrySubclassForMethod(groupByField);
		log.debug("Select {}, tableName {}", groupByField, tableName);
		List results = getEntryHandler().query(
				"select " + groupByField + " from " + tableName + " where (eventTime between '" + start + "' and '"
						+ end + "') group by (" + groupByField + ")");

		ArrayList<Group> groups = new ArrayList();
		for (Object result : results) {
			Object resultAsArray = (Object) result;
			Group group = new Group();
			group.setValue(0);
			group.setGroupName((String) resultAsArray);
			groups.add(group);

		}

		// add the series label or if none specified, add a default
//		if (statisticParameters.getSeries().getSeriesLabel() == null)
//			statisticParameters.getSeries().setSeriesLabel("Distinct Values " + groupByField);

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
