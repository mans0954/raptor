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

package uk.ac.cardiff.raptorweb.model.wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptorweb.model.ChartOptions;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet.TimeRange;

/**
 * Holds information supplied during graph wizard construction.
 */
public class GraphWizardModel implements Serializable {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(GraphWizardModel.class);

    /**
     * Default serial UID.
     */
    private static final long serialVersionUID = 7633246506752390440L;

    public enum QualitativeTimeRange {
        PREVIOUS_MONTH("Last Month", TimeRange.PREVIOUSMONTH), LAST_WEEK("Today Minus One Week", TimeRange.LASTWEEK),
        LAST_MONTH("Today Minus One Month", TimeRange.LASTMONTH),
        LAST_YEAR("Today Minus One Year", TimeRange.LASTYEAR), SCONUL_YEAR("This Sconul Year", TimeRange.SCONULYEAR), TODAY("Today", TimeRange.TODAY);

        private final String friendlyName;

        private final TimeRange timeRange;

        private QualitativeTimeRange(final String friendlyName, final TimeRange timeRange) {
            this.timeRange = timeRange;
            this.friendlyName = friendlyName;
        }

        /**
         * @return Returns the friendlyName.
         */
        public String getFriendlyName() {
            return friendlyName;
        }

        /**
         * @return Returns the timeRange.
         */
        public TimeRange getTimeRange() {
            return timeRange;
        }
    }

    /**
     * Time range
     */
    private QualitativeTimeRange qualitativeTimeRange;

    /**
     * The actual start date and time for the currently selected time range (computed).
     */
    private DateTime currentStartTimeActual;

    /**
     * The actual start date and time for the currently selected time range (computed).
     */
    private DateTime currentEndTimeActual;

    /**
     * A temporary variable to store the currently selected event type on the UI.
     */
    private String tmpSelectedEventType;

    /**
     * A temporary variable to store the currently selected graph set / event type to remove.
     */
    private GraphSet tmpGraphSetToRemove;

    /**
     * Set of chart options selectable by the user.
     */
    private ChartOptions chartOptions;

    /**
     * The titles (common to all graphs) is stored here before being added to the returned graph model.
     */
    private String graphTitle;

    /**
     * A list of {@link StatisticFunctionType}s that are used to select (dynamically) the type of graph function the
     * required to construct a graph.
     */
    private List<StatisticFunctionType> statisticFunctionTypes;

    /**
     * How to layout the results page e.g. graphs next to each other or ontop.
     */
    private String displayLayout;

    /**
     * List of {@link GraphSet}s. Each graphset holds all the information required to build both tabular and graphical
     * versions of a graph result.
     */
    private List<GraphSet> graphSets;

    /**
     * Currently selected graphset to work off.
     */
    private GraphSet selectedGraphSet;

    /** The suggestion values used by the UI to assist user input. */
    private SuggestionValues suggestionValues;

    /** The selected post processor, as a reference for removal */
    private ProcessorInformation selectedPostProcessor;

    /** Record for holding information about a new processor to add */
    private ProcessorInformation processorToAdd;

    /** The filename to use for report downloads. */
    private String downloadFilename;

    /**
     * The currently selected series under modification in the series-selector pages.
     */
    private Series selectedSeries;

    /**
     * A formatted string representation of when this wizard model was saved (if ever).
     */
    private String dateSavedFormatted;

    /**
     * Date this was last modified.
     */
    private String dateModifiedFormatted;

    /**
     * Who created this report.
     */
    private String createdBy;

    /**
     * If set to true, all selection stages should be skipped and the graph(s) should be run straight away.
     */
    private boolean runImmediatly = false;

    public GraphWizardModel() {
        super();
        qualitativeTimeRange = QualitativeTimeRange.LAST_WEEK;
        displayLayout = "ONTOP";
        initChartOptions();
    }

    public void initialiseNewProcessorAdd() {
        processorToAdd = new ProcessorInformation();

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
     * Set sensible defaults for the set of chart options.
     */
    private void initChartOptions() {
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
    }

    /**
     * Returns the number of columns to display. This is based on the number of event types and hence constructed
     * graphSets. If a vertical display layout is used (ONTOP), the number of columns is fixed as 1. If a horizontal
     * layout has been chosen (SIDEBYSIDE), the number of columns is the size of the number of graphSets.
     * 
     * @return
     */
    public int getResultColumns() {
        if (displayLayout.equalsIgnoreCase("ONTOP")) {
            return 1;
        } else if (displayLayout.equalsIgnoreCase("SIDEBYSIDE")) {
            return graphSets.size();
        }
        return 0;
    }

    /**
     * Returns the number of rows to display. This is based on the number of event types and hence constructed
     * graphSets. If a vertical display layout is used (ONTOP), the number of rows is the size of the number of
     * graphSets. If a horizontal layout has been choosen (SIDEBYSIDE), the number of rows is fixed as 1.
     * 
     * @return
     */
    public int getResultRows() {
        if (displayLayout.equalsIgnoreCase("ONTOP")) {
            return graphSets.size();
        } else if (displayLayout.equalsIgnoreCase("SIDEBYSIDE")) {
            return 1;
        }
        return 0;
    }

    /**
     * Find all fields for the currently selected event type
     * 
     * @return
     */
    public List<String> getPossibleFieldNameValues() {
        // TODO this should not be a partially hard coded list.

        String[] eventTypesForSelection = new String[graphSets.size() + 2];
        int i = 0;
        for (GraphSet type : graphSets) {
            eventTypesForSelection[i++] = type.getEventType();
        }
        eventTypesForSelection[i++] = "uk.ac.cardiff.model.event.AuthenticationEvent";
        eventTypesForSelection[i++] = "uk.ac.cardiff.model.event.Event";
        log.trace("Looking for possible statistical unit information for event type [{}]", eventTypesForSelection);
        return suggestionValues.getPossibleFieldNameValuesList(eventTypesForSelection);

    }

    public ArrayList<String> autocompleteFieldValues(Object suggest) {
        if (selectedSeries.getComparisonPredicate().getFieldName() == null
                || selectedSeries.getComparisonPredicate().getFieldName().equals("")) {
            log.warn("No field values to return, is field selected?");
            ArrayList<String> dummy = new ArrayList<String>();
            dummy.add("No field selected");
            return dummy;
        }
        String pref = (String) suggest;
        ArrayList<String> possibles = new ArrayList<String>();
        List<String> allPossibles =
                suggestionValues.autocomplete(selectedSeries.getComparisonPredicate().getFieldName());
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
     * Gets the {@link QualitativeTimeRange} enum as an array of {@link SelectItem}.
     * 
     * @return
     */
    public SelectItem[] getQualitativeTimeRangeSelectItems() {
        SelectItem[] items = new SelectItem[QualitativeTimeRange.values().length];
        int i = 0;
        for (QualitativeTimeRange range : QualitativeTimeRange.values()) {
            items[i++] = new SelectItem(range.name(), range.getFriendlyName());
        }
        return items;
    }

    /**
     * @return Returns the qualitativeTimeRange.
     */
    public String getQualitativeTimeRangeAsString() {
        if (qualitativeTimeRange == null) {
            return "";
        }
        return qualitativeTimeRange.name();
    }

    public void setQualitativeTimeRangeAsString(String qualitativeTimeRangeString) {
        qualitativeTimeRange = QualitativeTimeRange.valueOf(qualitativeTimeRangeString);
        log.debug("Set QualitativeTimeRangeString = [{}], QualitativeTimeRange = [{}]", qualitativeTimeRangeString,
                qualitativeTimeRange);
    }

    /**
     * Adds the currently select (tmpSelectedEventType) to the selectedEventTypes set.
     */
    public void saveEventType() {
        if (graphSets == null) {
            graphSets = new ArrayList<GraphSet>();
        }
        GraphSet graphSet = new GraphSet();
        graphSet.setEventType(tmpSelectedEventType);
        graphSet.setStatisticFunctionTypes(statisticFunctionTypes);
        graphSets.add(graphSet);
    }

    /**
     * Removes the selected event type from the list.
     */
    public void removeEventType() {
        graphSets.remove(tmpGraphSetToRemove);
    }

    /**
     * @return Returns the qualitativeTimeRange.
     */
    public QualitativeTimeRange getQualitativeTimeRange() {
        return qualitativeTimeRange;
    }

    /**
     * @param qualitativeTimeRange The qualitativeTimeRange to set.
     */
    public void setQualitativeTimeRange(QualitativeTimeRange qualitativeTimeRange) {
        this.qualitativeTimeRange = qualitativeTimeRange;
    }

    /**
     * @return Returns the currentStartTimeActual.
     */
    public DateTime getCurrentStartTimeActual() {
        return currentStartTimeActual;
    }

    /**
     * @return Returns the currentEndTimeActual.
     */
    public DateTime getCurrentEndTimeActual() {
        return currentEndTimeActual;
    }

    /**
     * @param currentStartTimeActual The currentStartTimeActual to set.
     */
    public void setCurrentStartTimeActual(DateTime currentStartTimeActual) {
        this.currentStartTimeActual = currentStartTimeActual;
    }

    /**
     * @return Returns the currentEndTimeActual.
     */
    public String getCurrentEndTimeActualFormatted() {
        return currentEndTimeActual.toString("EEE dd MMM YYYY");
    }

    /**
     * @return Returns the currentEndTimeActual.
     */
    public String getCurrentStartTimeActualFormatted() {
        return currentStartTimeActual.toString("EEE dd MMM YYYY");
    }

    /**
     * @param currentEndTimeActual The currentEndTimeActual to set.
     */
    public void setCurrentEndTimeActual(DateTime currentEndTimeActual) {
        this.currentEndTimeActual = currentEndTimeActual;
    }

    /**
     * @return Returns the tmpSelectedEventType.
     */
    public String getTmpSelectedEventType() {
        return tmpSelectedEventType;
    }

    /**
     * @param tmpSelectedEventType The tmpSelectedEventType to set.
     */
    public void setTmpSelectedEventType(String tmpSelectedEventType) {
        log.debug("Setting tmp selected event type = [{}]", tmpSelectedEventType);
        this.tmpSelectedEventType = tmpSelectedEventType;
    }

    /**
     * @return Returns the chartOptions.
     */
    public ChartOptions getChartOptions() {
        return chartOptions;
    }

    /**
     * @param chartOptions The chartOptions to set.
     */
    public void setChartOptions(ChartOptions chartOptions) {
        this.chartOptions = chartOptions;
    }

    /**
     * @return Returns the statisticFunctionTypes.
     */
    public List<StatisticFunctionType> getStatisticFunctionTypes() {
        return statisticFunctionTypes;
    }

    /**
     * Adds the List of {@link StatisticFunctionType}s, also checks if any graphSet requires this property set - so as
     * to cater for loaded reports.
     * 
     * @param statisticFunctionTypes The statisticFunctionTypes to set.
     */
    public void setStatisticFunctionTypes(List<StatisticFunctionType> statisticFunctionTypes) {
        if (statisticFunctionTypes != null) {
            log.trace("Setting the statistic function types with size of {}", statisticFunctionTypes.size());
        }
        this.statisticFunctionTypes = statisticFunctionTypes;
        if (graphSets != null) {
            for (GraphSet graphSet : graphSets) {
                if (graphSet.getStatisticFunctionTypes() == null) {
                    graphSet.setStatisticFunctionTypes(statisticFunctionTypes);
                }
            }
        }
    }

    /**
     * @return Returns the displayLayout.
     */
    public String getDisplayLayout() {
        return displayLayout;
    }

    /**
     * @param displayLayout The displayLayout to set.
     */
    public void setDisplayLayout(String displayLayout) {
        this.displayLayout = displayLayout;
    }

    /**
     * @return Returns the graphSets.
     */
    public List<GraphSet> getGraphSets() {
        return graphSets;
    }

    /**
     * @param graphSets The graphSets to set.
     */
    public void setGraphSets(List<GraphSet> graphSets) {
        this.graphSets = graphSets;
    }

    /**
     * @return Returns the selectedSeries.
     */
    public Series getSelectedSeries() {
        return selectedSeries;
    }

    /**
     * @param selectedSeries The selectedSeries to set.
     */
    public void setSelectedSeries(Series selectedSeries) {
        this.selectedSeries = selectedSeries;
    }

    /**
     * @return Returns the suggestionValues.
     */
    public SuggestionValues getSuggestionValues() {
        return suggestionValues;
    }

    /**
     * @param suggestionValues The suggestionValues to set.
     */
    public void setSuggestionValues(SuggestionValues suggestionValues) {
        log.trace("Setting suggestion values as object [{}]", suggestionValues);
        this.suggestionValues = suggestionValues;
    }

    /**
     * @return Returns the graphTitle.
     */
    public String getGraphTitle() {
        return graphTitle;
    }

    /**
     * @param graphTitle The graphTitle to set.
     */
    public void setGraphTitle(String graphTitle) {
        this.graphTitle = graphTitle;
    }

    /**
     * @return Returns the downloadFilename.
     */
    public String getDownloadFilename() {
        return downloadFilename;
    }

    /**
     * @param downloadFilename The downloadFilename to set.
     */
    public void setDownloadFilename(String downloadFilename) {
        this.downloadFilename = downloadFilename;
    }

    /**
     * @return Returns the tmpGraphSetToRemove.
     */
    public GraphSet getTmpGraphSetToRemove() {
        return tmpGraphSetToRemove;
    }

    /**
     * @param tmpGraphSetToRemove The tmpGraphSetToRemove to set.
     */
    public void setTmpGraphSetToRemove(GraphSet tmpGraphSetToRemove) {
        this.tmpGraphSetToRemove = tmpGraphSetToRemove;
    }

    /**
     * @return Returns the selectedGraphSet.
     */
    public GraphSet getSelectedGraphSet() {
        return selectedGraphSet;
    }

    /**
     * @param selectedGraphSet The selectedGraphSet to set.
     */
    public void setSelectedGraphSet(GraphSet selectedGraphSet) {
        log.debug("Selected graphSet is [{}], of statistic type [{}]", selectedGraphSet,
                selectedGraphSet.getSelectedStatisticFunctionTypeString());
        this.selectedGraphSet = selectedGraphSet;
    }

    /**
     * @return Returns the dateSavedFormatted.
     */
    public String getDateSavedFormatted() {
        return dateSavedFormatted;
    }

    /**
     * @param dateSavedFormatted The dateSavedFormatted to set.
     */
    public void setDateSavedFormatted(String dateSavedFormatted) {
        this.dateSavedFormatted = dateSavedFormatted;
    }

    /**
     * @return Returns the selectedPostProcessor.
     */
    public ProcessorInformation getSelectedPostProcessor() {
        return selectedPostProcessor;
    }

    /**
     * @param selectedPostProcessor The selectedPostProcessor to set.
     */
    public void setSelectedPostProcessor(ProcessorInformation selectedPostProcessor) {
        this.selectedPostProcessor = selectedPostProcessor;
    }

    /**
     * @return Returns the processorToAdd.
     */
    public ProcessorInformation getProcessorToAdd() {
        return processorToAdd;
    }

    /**
     * @param processorToAdd The processorToAdd to set.
     */
    public void setProcessorToAdd(ProcessorInformation processorToAdd) {
        this.processorToAdd = processorToAdd;
    }

    /**
     * @return Returns the runImmediatly.
     */
    public boolean isRunImmediatly() {
        return runImmediatly;
    }

    /**
     * @param runImmediatly The runImmediatly to set.
     */
    public void setRunImmediatly(boolean runImmediatly) {
        this.runImmediatly = runImmediatly;
    }

    /**
     * @return Returns the dateModifiedFormatted.
     */
    public String getDateModifiedFormatted() {
        return dateModifiedFormatted;
    }

    /**
     * @param dateModifiedFormatted The dateModifiedFormatted to set.
     */
    public void setDateModifiedFormatted(String dateModifiedFormatted) {
        this.dateModifiedFormatted = dateModifiedFormatted;
    }

    /**
     * @return Returns the createdBy. Returns "unknown" if createdBy is null.
     */
    public String getCreatedBy() {
        if (createdBy == null) {
            return "unknown";
        }
        return createdBy;
    }

    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
