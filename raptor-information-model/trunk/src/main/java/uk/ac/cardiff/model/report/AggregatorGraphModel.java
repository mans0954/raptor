package uk.ac.cardiff.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * based on the Apache Trinidad ChartModel, for compatibility. However, does not extend the Apache Trinidad Model
 * so as to remain view agnostic.
 */

public class AggregatorGraphModel implements Serializable {

    /** Class logger */
    private static final long serialVersionUID = 5372664739350074383L;

    /** The list of <code>String</code> labels for the groups */
    private List<String> groupLabels;

    /** The list of <code>String</code> labels for each series */
    private List<String> seriesLabels;

    /** The list of <code>Double</code> values assigned to each series */
    private List<List<Double>> chartValues;

    /** A class that holds presentation information for this graph model */
    private Presentation presentation;

    public AggregatorGraphModel() {
        seriesLabels = new ArrayList<String>();
    }

    public List<String> getGroupLabels() {
        return groupLabels;

    }

    public void addGroupLabel(String label) {
        if (getGroupLabels() == null)
            setGroupLabels(new ArrayList<String>());
        getGroupLabels().add(label);
    }

    public void addSeriesLabel(String label) {
        if (getSeriesLabels() == null)
            setSeriesLabels(new ArrayList<String>());
        getSeriesLabels().add(label);
    }

    public void sortGroupLabelsAlphabetically() {
        Collections.sort(groupLabels);

    }

    public List<String> getSeriesLabels() {
        return seriesLabels;

    }

    public void addGroupValue(List<Double> values) {
        if (chartValues == null)
            setChartValues(new ArrayList<List<Double>>());
        chartValues.add(values);
    }

    public List<List<Double>> getYValues() {
        return chartValues;
    }

    public void setYValues(List<List<Double>> values) {

    }

    public void setGroupLabels(List<String> groupLabels) {
        this.groupLabels = groupLabels;
    }

    public void setSeriesLabels(List<String> seriesLabels) {
        this.seriesLabels = seriesLabels;
    }

    public void setChartValues(List<List<Double>> chartValues) {
        this.chartValues = chartValues;
    }

    public List<List<Double>> getChartValues() {
        return chartValues;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Presentation getPresentation() {
        return presentation;
    }

}
