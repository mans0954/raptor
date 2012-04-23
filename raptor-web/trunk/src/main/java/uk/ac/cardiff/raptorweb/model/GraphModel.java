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
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.EventTypeInformation;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;

public class GraphModel implements Serializable {

    /** Generated SerialVersionUID */
    private static final long serialVersionUID = -2803349385469406219L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(GraphModel.class);

    /** The raw graph model, for later reconstruction */
    private AggregatorGraphModel rawGraphModel;

    /** The current graph (as the internal Raptor model) for display */
    private RaptorGraphModel currentGraph;

    /** The current set of graph options applied to the <code>currentGraph</code> */
    private ChartOptions chartOptions;

    /** The current table relating to the <code>selectedStatisticalUnit</code> */
    private RaptorTableChartModel currentTableGraph;

    /** The currently selected statistical unit */
    private StatisticalUnitInformationView selectedStatisticalUnit;

    /** A <code>String</code> that holds the results of processing */
    private String processingResult;

    /** Whether to show the control panel on the view */
    private boolean showControlPanel;

    /** The current graph (as an image) to display */
    private RaptorJFreeChartModel currentJFreeGraph;

    /** The size of the control panel - not used */
    private String controlPanelSize;

    /**
     * A <code>List</code> of <code>{@link uk.ac.cardiff.wsmodel.StatisticalUnitInformationView}</code>s that have been acquired from the attached MUA
     */
    private List<StatisticalUnitInformationView> statisticalUnitsForView;

    /** The filename to use for report downloads. */
    private String downloadFilename;

    /** The suggestion values used by the UI to assist user input. */
    private SuggestionValues suggestionValues;

    /** Record for holding information about a new processor to add */
    private ProcessorInformation processorToAdd;

    /** Selected series modal panel */
    private Series selectedSeries;

    /** The selected post processor, as a reference for removal */
    private ProcessorInformation selectedPostProcessor;

    /** List of {@link EventTypeInformation} to assist users in selecting event type information. */
    private List<EventTypeInformation> eventTypes;

    /**
     * Set some sensible defaults for this graphs models chart options
     */
    public GraphModel() {
        chartOptions = new ChartOptions();
        chartOptions.setPerspective("false");
        chartOptions.setGraphType(ChartOptions.ChartType.BAR);
        chartOptions.setxMajorGridCount(-1);
        chartOptions.setyMajorGridCount(-1);
        chartOptions.setOrientation(ChartOptions.OrientationType.VERTICAL);
        chartOptions.setImageWidth(1480);
        chartOptions.setImageHeight(1024);
        chartOptions.setxLabelPosition(ChartOptions.LabelPositionType.UP_90);
        chartOptions.setGraphPresentation(GraphPresentation.FANCY);
        chartOptions.setGraphType(ChartType.BAR3D);

        // create a blank selected statistical unit for display
        selectedStatisticalUnit = new StatisticalUnitInformationView();

    }

    /**
     * Gets a list of event types from the attached MUA's capabilities, and places them inside SelectItems for the UI.
     */
    public List<SelectItem> getEventTypeList() {
        List<SelectItem> eventTypes = new ArrayList<SelectItem>();

        List<EventTypeInformation> eventTypesFromAttached = getEventTypes();
        for (EventTypeInformation eventType : eventTypesFromAttached) {
            if (eventType.getNoOfEvents() > 0) {
                String eventTypeString = eventType.getEventTypeName();
                SelectItem item = new SelectItem();
                String[] classNameSplit = eventTypeString.split("\\.");
                if (classNameSplit.length > 0) {
                    item.setLabel(classNameSplit[classNameSplit.length - 1]);
                } else {
                    item.setLabel(eventTypeString);
                }
                item.setValue(eventTypeString);
                eventTypes.add(item);
            }
        }
        return eventTypes;
    }

    public void initialiseNewProcessorAdd() {
        processorToAdd = new ProcessorInformation();

    }

    /**
     * Makes a copy of any method parameters from the suggestion values, into the processorToAdd
     */
    public void setupProcessorToAdd() {
        if (processorToAdd != null) {
            log.trace("Finding method parameters for {}", processorToAdd.getFriendlyName());
            List<ProcessorInformation> processorsInformation = suggestionValues.getPossiblePostProcessors();
            for (ProcessorInformation information : processorsInformation) {
                if (information.getFriendlyName().equals(processorToAdd.getFriendlyName())) {
                    processorToAdd.setProcessorClass(information.getProcessorClass());
                    if (information.getMethodParameters() != null) {
                        List<MethodParameter> parameters = new ArrayList<MethodParameter>();
                        for (MethodParameter parameter : information.getMethodParameters()) {
                            MethodParameter parameterNew = new MethodParameter();
                            log.debug("Setup parameter {}", parameter.getParameterName());
                            parameterNew.setParameterName(parameter.getParameterName());
                            parameterNew.setParameterType(parameter.getParameterType());
                            parameterNew.setValueType(parameter.getValueType());
                            parameters.add(parameterNew);
                        }
                        processorToAdd.setMethodParameters(parameters);
                    } else {
                        processorToAdd.setMethodParameters(null);
                    }
                }
            }

        }
    }

    /**
     * Set the old statistical unit to false, update it with the newly selected statisticalunit and set it to selected.
     * 
     * @param selectedStatisticalUnit
     *            the selectedStatisticalUnit to set
     */
    public void setSelectedStatisticalUnit(StatisticalUnitInformationView selectedStatisticalUnit) {
        this.selectedStatisticalUnit.setSelected(false);
        this.selectedStatisticalUnit = selectedStatisticalUnit;
        this.selectedStatisticalUnit.setSelected(true);
    }

    /**
     * Find all fields for the currently selected event type
     * 
     * @return
     */
    public List<String> getPossibleFieldNameValues() {
        // EventType eventType = selectedStatisticalUnit.getStatisticalUnitInformation().getStatisticParameters().getEventType();
        // String[] classFilter = eventType.getClassHierarchy();
        // return (ArrayList<String>) suggestionValues.getPossibleFieldNameValuesList(classFilter);
        return Collections.emptyList();
    }

    public List<String> getPossiblePostProcessorValues() {
        if (suggestionValues == null || suggestionValues.getPossiblePostProcessors() == null) {
            return Collections.emptyList();
        }
        List<ProcessorInformation> processorsInformation = suggestionValues.getPossiblePostProcessors();
        List<String> possibles = new ArrayList<String>();
        for (ProcessorInformation information : processorsInformation) {
            possibles.add(information.getFriendlyName());
        }
        return possibles;

    }

    public ArrayList<String> autocompleteFieldValues(Object suggest) {
        if (selectedSeries.getComparisonPredicate().getFieldName() == null || selectedSeries.getComparisonPredicate().getFieldName().equals("")) {
            log.warn("No field values to return, is field selected?");
            ArrayList<String> dummy = new ArrayList<String>();
            dummy.add("No field selected");
            return dummy;
        }
        String pref = (String) suggest;
        ArrayList<String> possibles = new ArrayList<String>();
        List<String> allPossibles = suggestionValues.autocomplete(selectedSeries.getComparisonPredicate().getFieldName());
        for (String possible : allPossibles) {
            if ((possible != null && possible.toLowerCase().contains(pref.toLowerCase())) || "".equals(pref)) {
                possibles.add(possible);
            }
        }

        /* add something for output if none returned */
        if (possibles.size() == 0) {
            possibles.add("No suggestions");
        }

        Collections.sort(possibles);

        return possibles;
    }

    /**
     * @return the selectedStatisticalUnit
     */
    public StatisticalUnitInformationView getSelectedStatisticalUnit() {
        return selectedStatisticalUnit;
    }

    /**
     * @param invokeStatisticalUnit
     */
    public void setCurrentGraph(RaptorGraphModel graph) {
        this.currentGraph = graph;

    }

    /**
     * 
     * @return the <code>RaptorGraphMode</code> associated with this graph view
     */
    public RaptorGraphModel getCurrentGraph() {
        return currentGraph;
    }

    public void setCurrentTableGraph(RaptorTableChartModel currentTableGraph) {
        this.currentTableGraph = currentTableGraph;
    }

    public RaptorTableChartModel getCurrentTableGraph() {
        return currentTableGraph;
    }

    public void setChartOptions(ChartOptions chartOptions) {
        this.chartOptions = chartOptions;
    }

    public ChartOptions getChartOptions() {
        return chartOptions;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setShowControlPanel(boolean showControlPanel) {
        this.showControlPanel = showControlPanel;
    }

    public boolean isShowControlPanel() {
        return showControlPanel;
    }

    public void toggleShowControlPanel() {
        showControlPanel = !showControlPanel;
        controlPanelSize = "30%";
    }

    public void setCurrentJFreeGraph(RaptorJFreeChartModel currentJFreeGraph) {
        this.currentJFreeGraph = currentJFreeGraph;
    }

    public RaptorJFreeChartModel getCurrentJFreeGraph() {
        return currentJFreeGraph;
    }

    public void setControlPanelSize(String controlPanelSize) {
        this.controlPanelSize = controlPanelSize;
    }

    public String getControlPanelSize() {
        return controlPanelSize;
    }

    public void setStatisticalUnitsForView(List<StatisticalUnitInformationView> statisticalUnitsForView) {
        this.statisticalUnitsForView = statisticalUnitsForView;
    }

    public List<StatisticalUnitInformationView> getStatisticalUnitsForView() {
        return statisticalUnitsForView;
    }

    public void setSelectedSeries(Series selectedSeries) {
        this.selectedSeries = selectedSeries;
    }

    public Series getSelectedSeries() {
        return selectedSeries;
    }

    public void setSuggestionValues(SuggestionValues suggestionValues) {
        this.suggestionValues = suggestionValues;
    }

    public SuggestionValues getSuggestionValues() {
        return suggestionValues;
    }

    public void setRawGraphModel(AggregatorGraphModel rawGraphModel) {
        this.rawGraphModel = rawGraphModel;
    }

    public AggregatorGraphModel getRawGraphModel() {
        return rawGraphModel;
    }

    /**
     * @param downloadFilename
     *            the downloadFilename to set
     */
    public void setDownloadFilename(String downloadFilename) {
        this.downloadFilename = downloadFilename;
    }

    /**
     * @return the downloadFilename
     */
    public String getDownloadFilename() {
        return downloadFilename;
    }

    /**
     * @param selectedPostProcessor
     *            the selectedPostProcessor to set
     */
    public void setSelectedPostProcessor(ProcessorInformation selectedPostProcessor) {
        this.selectedPostProcessor = selectedPostProcessor;
    }

    /**
     * @return the selectedPostProcessor
     */
    public ProcessorInformation getSelectedPostProcessor() {
        return selectedPostProcessor;
    }

    /**
     * @param processorToAdd
     *            the processorToAdd to set
     */
    public void setProcessorToAdd(ProcessorInformation processorToAdd) {
        this.processorToAdd = processorToAdd;
    }

    /**
     * @return the processorToAdd
     */
    public ProcessorInformation getProcessorToAdd() {
        return processorToAdd;
    }

    /**
     * @param eventTypes
     *            the eventTypes to set
     */
    public void setEventTypes(List<EventTypeInformation> eventTypes) {
        this.eventTypes = eventTypes;
    }

    /**
     * @return the eventTypes
     */
    public List<EventTypeInformation> getEventTypes() {
        return eventTypes;
    }

}
