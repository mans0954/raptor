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

package uk.ac.cardiff.raptorweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.DynamicStatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.CurrentTimeRanges;
import uk.ac.cardiff.raptorweb.model.NoSuchTimeRangeException;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.StatisticalUnitInformationView;
import uk.ac.cardiff.raptorweb.model.wizard.GraphSet;
import uk.ac.cardiff.raptorweb.model.wizard.GraphWizardModel;
import uk.ac.cardiff.raptorweb.service.GraphWizardService;

/**
 *
 */
public class GraphWizardServiceImpl implements GraphWizardService {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(GraphWizardServiceImpl.class);

    /** RaptorWeb's main engine class for dealing with all service requests */
    private RaptorWebEngine webEngine;

    /** The chart processor which converts Aggregator Charts into RaptorWeb charts for outputting */
    private ChartProcessor chartProcessor;

    /** {@inheritDoc} */
    @Override
    public void computeCurrentTimeRange(GraphWizardModel model) {
        log.info("Computing current time range from selected time range [{}]", model.getQualitativeTimeRangeAsString());
        CurrentTimeRanges computedRanges = new CurrentTimeRanges(true);

        try {
            DateTime start = computedRanges.getStartTime(model.getQualitativeTimeRange().getTimeRange());
            DateTime end = computedRanges.getEndTime(model.getQualitativeTimeRange().getTimeRange());
            model.setCurrentEndTimeActual(end);
            model.setCurrentStartTimeActual(start);
        } catch (NoSuchTimeRangeException e) {
            log.error("Can not set time range, this should not really happen.");
        }

    }

    @Override
    public void addProcessorToSelectedStatistic(GraphWizardModel model) {
        ProcessorInformation processorToAdd = model.getProcessorToAdd();
        log.debug("Adding processor [{} with parameters {}]", processorToAdd.getFriendlyName(),
                (processorToAdd.getMethodParameters() != null));

        if (processorToAdd.getMethodParameters() != null) {
            for (MethodParameter methodParameter : processorToAdd.getMethodParameters()) {
                log.debug("Parameter [{},{},{}]",
                        new Object[] {methodParameter.getParameterName(), methodParameter.getValue(),
                                methodParameter.getValue().getClass()});
            }
        }
        model.getSelectedGraphSet().getStatisticalUnitInformation().getStatisticalUnitInformation().getPostprocessors()
                .add(processorToAdd);
    }

    @Override
    public void removePostProcessorFromSelectedStatistic(GraphWizardModel model) {
        model.getSelectedGraphSet().getStatisticalUnitInformation().getStatisticalUnitInformation().getPostprocessors()
                .remove(model.getSelectedPostProcessor());
    }

    @Override
    public void generateExcelReport(GraphWizardModel model) {
        // if (model.getCurrentTableGraph() != null)
        // webEngine.generateReport(websession, "excel");
    }

    @Override
    public void generateCSVReport(GraphWizardModel model) {
        // if (model.getCurrentTableGraph() != null)
        // webEngine.generateReport(websession, "csv");
    }

    @Override
    public void generatePDFReport(GraphWizardModel model) {
        // if (model.getCurrentTableGraph() != null)
        // webEngine.generateReport(websession, "pdf");
    }

    /**
     * loads the reports from the download directory into the graphmodel
     * 
     * @param model
     */
    @Override
    public void loadSavedReports(GraphWizardModel model) {
        // webEngine.loadSavedReports(model);
    }

    @Override
    public void addSeriesToSelectedStatistic(GraphWizardModel model) {
        model.getSelectedGraphSet().getStatisticalUnitInformation().addSeries();

    }

    @Override
    public void addFilterToSelectedSeries(GraphWizardModel model) {
        model.getSelectedGraphSet().getStatisticalUnitInformation().addFilterToSeries(model.getSelectedSeries());
    }

    /**
     * Populates the suggestion values that assist users in selecting certain statistical parameters.
     * 
     * @param model the model to set suggestion values on.
     */
    @Override
    public void populateSuggestionValues(GraphWizardModel model) {

        model.setSuggestionValues(webEngine.getCapabilitiesOfCurrentlyAttachedEndpoint().getSuggestionValues());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.ac.cardiff.raptorweb.service.GraphService#removeSeriesFromSelectedStatistic(uk.ac.cardiff.raptorweb.model.
     * WebSession)
     */
    @Override
    public void removeSeriesFromSelectedStatistic(GraphWizardModel model) {
        model.getSelectedGraphSet().getStatisticalUnitInformation().removeSeries(model.getSelectedSeries());

    }

    @Override
    public void removeSelectedFilterFromSelectedStatistic(GraphWizardModel model) {
        model.getSelectedGraphSet().getStatisticalUnitInformation().removeFilterFromSeries(model.getSelectedSeries());

    }

    /**
     * Live lookup to the MUA to find the {@link StatisticalUnitInformation} for the current
     * {@link StatisticFunctionType} selected. Adds this information back to the graph model.
     * 
     * @param model
     */
    public void lookupStatisticalUnitInformation(GraphWizardModel model) {
        log.trace("Looking up statistical unit information");
        StatisticalUnitInformation information =
                webEngine.lookupStatisticalUnitInformation(model.getSelectedGraphSet()
                        .getSelectedStatisticFunctionType());
        StatisticalUnitInformationView informationView = new StatisticalUnitInformationView();
        informationView.setStatisticalUnitInformation(information);
        model.getSelectedGraphSet().setStatisticalUnitInformation(informationView);
    }

    /**
     * Puts together a list of StatisticalUnitInformation objects from those graph options chosen in the graph wizard.
     * 
     * @param model
     */
    @Override
    public void compileStatisticalUnitInformation(GraphWizardModel model) {
        log.info("Compiling the graph wizard model information into a set of DynamicStatisticalUnitInformation models");

        // compute time ranges again so it is accurate when running.
        computeCurrentTimeRange(model);

        List<DynamicStatisticalUnitInformation> statisticalUnitInformations =
                new ArrayList<DynamicStatisticalUnitInformation>();
        for (GraphSet set : model.getGraphSets()) {
            DynamicStatisticalUnitInformation dynamic = new DynamicStatisticalUnitInformation();
            dynamic.setFunction(set.getSelectedStatisticFunctionType());

            dynamic.setStatisticUnitInformation(set.getStatisticalUnitInformation().getStatisticalUnitInformation());
            log.debug("Has compiled with {} post processors", dynamic.getStatisticUnitInformation().getPostprocessors()
                    .size());
            dynamic.getStatisticUnitInformation().getStatisticParameters().setEndTime(model.getCurrentEndTimeActual());
            dynamic.getStatisticUnitInformation().getStatisticParameters()
                    .setStartTime(model.getCurrentStartTimeActual());
            dynamic.getStatisticUnitInformation().getStatisticParameters().setEventType(set.getEventType());
            statisticalUnitInformations.add(dynamic);
            set.setDynamicStatisticalUnitInformation(dynamic);
        }

    }

    public void invokeStatisticalUnitInformationDynamic(GraphWizardModel model) {
        log.debug("Invoking graph wizard model which is null =  {}", (model == null));
        for (GraphSet set : model.getGraphSets()) {
            if (set.getDynamicStatisticalUnitInformation() == null) {
                log.warn("This graph set had no compiled DynamicStatisticalUnitInformation set, ignoring...");
                continue;
            }
            AggregatorGraphModel results =
                    webEngine.invokeStatisticalUnitDynamic(set.getDynamicStatisticalUnitInformation());

            if (results != null) {
                RaptorTableChartModel table = chartProcessor.constructRaptorTableChartModel(results);
                set.setTableModel(table);
                results.getPresentation().setGraphTitle(model.getGraphTitle());
                set.setGraphModel(chartProcessor.constructJFreeGraph(results, model.getChartOptions()));
            }

        }

    }

    /**
     * @return Returns the webEngine.
     */
    public RaptorWebEngine getWebEngine() {
        return webEngine;
    }

    /**
     * @param webEngine The webEngine to set.
     */
    public void setWebEngine(RaptorWebEngine webEngine) {
        this.webEngine = webEngine;
    }

    /**
     * @return Returns the chartProcessor.
     */
    public ChartProcessor getChartProcessor() {
        return chartProcessor;
    }

    /**
     * @param chartProcessor The chartProcessor to set.
     */
    public void setChartProcessor(ChartProcessor chartProcessor) {
        this.chartProcessor = chartProcessor;
    }
}
