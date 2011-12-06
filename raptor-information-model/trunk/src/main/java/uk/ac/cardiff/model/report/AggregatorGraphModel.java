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
package uk.ac.cardiff.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * based on the Apache Trinidad ChartModel, for compatibility. However, does not extend the Apache Trinidad Model so as to remain view agnostic.
 */

public class AggregatorGraphModel implements Serializable {

    /** Class logger. */
    private static final long serialVersionUID = 5372664739350074383L;

    /** The list of <code>String</code> labels for the groups. */
    private List<String> groupLabels;

    /** The list of <code>String</code> labels for each series. */
    private List<String> seriesLabels;

    /** The list of <code>Double</code> values assigned to each series. */
    private List<List<Double>> chartValues;

    /** A class that holds presentation information for this graph model. */
    private Presentation presentation;

    /**
     * Instantiates a new aggregator graph model.
     */
    public AggregatorGraphModel() {
        seriesLabels = new ArrayList<String>();
    }

    /**
     * Gets the group labels.
     * 
     * @return the group labels
     */
    public List<String> getGroupLabels() {
        return groupLabels;

    }

    /**
     * Adds the group label.
     * 
     * @param label
     *            the label
     */
    public void addGroupLabel(String label) {
        if (getGroupLabels() == null)
            setGroupLabels(new ArrayList<String>());
        getGroupLabels().add(label);
    }

    /**
     * Adds the series label.
     * 
     * @param label
     *            the label
     */
    public void addSeriesLabel(String label) {
        if (getSeriesLabels() == null)
            setSeriesLabels(new ArrayList<String>());
        getSeriesLabels().add(label);
    }

    /**
     * Sort group labels alphabetically.
     */
    public void sortGroupLabelsAlphabetically() {
        Collections.sort(groupLabels);

    }

    /**
     * Gets the series labels.
     * 
     * @return the series labels
     */
    public List<String> getSeriesLabels() {
        return seriesLabels;

    }

    /**
     * Adds the group value.
     * 
     * @param values
     *            the values
     */
    public void addGroupValue(List<Double> values) {
        if (chartValues == null)
            setChartValues(new ArrayList<List<Double>>());
        chartValues.add(values);
    }

    /**
     * Gets the y values.
     * 
     * @return the y values
     */
    public List<List<Double>> getYValues() {
        return chartValues;
    }

    /**
     * Sets the y values.
     * 
     * @param values
     *            the new y values
     */
    public void setYValues(List<List<Double>> values) {

    }

    /**
     * Sets the group labels.
     * 
     * @param groupLabels
     *            the new group labels
     */
    public void setGroupLabels(List<String> groupLabels) {
        this.groupLabels = groupLabels;
    }

    /**
     * Sets the series labels.
     * 
     * @param seriesLabels
     *            the new series labels
     */
    public void setSeriesLabels(List<String> seriesLabels) {
        this.seriesLabels = seriesLabels;
    }

    /**
     * Sets the chart values.
     * 
     * @param chartValues
     *            the new chart values
     */
    public void setChartValues(List<List<Double>> chartValues) {
        this.chartValues = chartValues;
    }

    /**
     * Gets the chart values.
     * 
     * @return the chart values
     */
    public List<List<Double>> getChartValues() {
        return chartValues;
    }

    /**
     * Sets the presentation.
     * 
     * @param presentation
     *            the new presentation
     */
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    /**
     * Gets the presentation.
     * 
     * @return the presentation
     */
    public Presentation getPresentation() {
        return presentation;
    }

}
