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

import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.model.wsmodel.MethodParameterNotOfRequiredTypeException;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.raptormua.engine.statistics.BaseStatistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticalUnitException;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.engine.statistics.records.ObservationSeries;

public class JuspStatisticalFunction extends BaseStatistic {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(JuspStatisticalFunction.class);

    private final String[] REPORT_TOTALS = new String[] {"jr1Downloads", "jr1aDownloads"};

    @Override
    public Boolean performStatistic(List<MethodParameter> methodParams, String sqlWhere)
            throws StatisticalUnitException {

        if (methodParams.size() != 1)
            throw new StatisticalUnitException("incorrect method parameters");

        log.debug("Performing JuspStatisticalFunction Statistical Operation");
        log.debug("Params for method:  {},{}", this.getClass().getSimpleName(), statisticParameters.getUnitName());

        String serviceId;
        try {
            serviceId = methodParams.get(0).getValue(String.class);
        } catch (MethodParameterNotOfRequiredTypeException e) {
            throw new StatisticalUnitException(e);
        }

        DateTime start = startingTime();
        DateTime end = endingTime();
        log.debug("groupBy between [start:{}] [end:{}]", start, end);
        String tableName = statisticParameters.getEventType();

        for (String report : REPORT_TOTALS) {

            String query =
                    "select resourceId, " + report + " from " + tableName
                            + " where serviceId=? and (eventTime between ? and ?)";

            String resourceCategoryFilter = statisticParameters.getResourceCategory().getSql();
            log.debug("Resource Category Filter {}", resourceCategoryFilter);

            Object[] params = new Object[] {serviceId, start, end};

            List results = getEventHandler().query(query, params);

            ArrayList<Group> groups = new ArrayList();
            for (Object result : results) {
                Object resultasObject = result;
                log.trace("result [{},{}]", result, result.getClass());
                if (resultasObject instanceof Object[]) {
                    Object[] resultAsArray = (Object[]) resultasObject;
                    Group group = new Group();
                    group.setValue(Integer.parseInt(resultAsArray[1].toString()));
                    if (resultAsArray[0] != null) {
                        group.setGroupName((String) resultAsArray[0]);
                    } else {
                        group.setGroupName("NA");
                    }
                    groups.add(group);
                }

            }

            if (groups.size() == 0)
                return false;

            // finished successfully, no exception thrown
            ObservationSeries series = new ObservationSeries();
            series.setObservations(groups.toArray(new Group[0]));
            getObservationSeries().add(series);
        }

        // finished successfully, no exception thrown
        return true;

    }

    @Override
    public void setStatisticParameters(StatisticParameters statisticParameters) {
        /*
         * allow for runtime construction of statisticParameters by creating a default set if none exist. This could be
         * forced in some way.
         */
        if (statisticParameters == null) {
            statisticParameters = createDefaultParameters();
        }

        List<MethodParameter> methodParams = statisticParameters.getMethodParams();
        if (methodParams.size() == 1) {
            methodParams.get(0).setParameterName("Institution");
            methodParams.get(0).setParameterType(ParameterType.VALUE);
        } else {
            log.error("Unable to set parameter type for statistic {}, incorrect number of parameters", this.getClass()
                    .getSimpleName());
        }
        this.statisticParameters = statisticParameters;

    }

    /**
     * finds the most commonly occurring JUSP institution Id - which is stored as service Id.
     */
    private String addJuspInstitutionIdSuggestion() {

        try {
            String query = "Select serviceId from JuspAggregatedEvent group by serviceId order by count(*) desc ";
            Object result = getEventHandler().queryForceUnique(query, null);
            log.debug("Institution Id has result: {}, {}", result, result.getClass());
            if (result instanceof String) {
                String resultString = (String) result;
                log.debug("Found most common JUSP institution Id as [{}]", resultString);
                return resultString;
            }
        } catch (Throwable e) {
            log.warn("Could not automatically set most common Jusp Institution - not a critical error - {}", e);
        }
        log.warn("Did not find most common JUSP institution Id");
        return "unknown";

    }

    private StatisticParameters createDefaultParameters() {
        StatisticParameters parameters = new StatisticParameters();
        parameters.setStatisticType("User");

        MethodParameter param = new MethodParameter();
        param.setValue(addJuspInstitutionIdSuggestion());
        List<MethodParameter> params = new ArrayList<MethodParameter>();
        params.add(param);
        parameters.setMethodParams(params);
        Series defaultSeries = new Series();
        defaultSeries.setSeriesLabel("JR1 Downloads");
        defaultSeries.setDummySeries(false);
        Series defaultSeriesJr1a = new Series();
        defaultSeriesJr1a.setSeriesLabel("JR1a Downloads");
        defaultSeriesJr1a.setDummySeries(true);
        List<Series> allSeries = new ArrayList<Series>();
        allSeries.add(defaultSeries);
        allSeries.add(defaultSeriesJr1a);
        parameters.setSeries(allSeries);
        return parameters;

    }

}
