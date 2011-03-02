/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import uk.ac.cardiff.model.Series;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

/**
 * @author philsmart
 *
 */
public class StatisticalUnitInformationView implements Serializable{

    private static final long serialVersionUID = 4655547623449270295L;

    private boolean selected;
    private StatisticalUnitInformation statisticalUnitInformation;


    public void setSelected(boolean selected) {
	this.selected = selected;
    }
    public boolean isSelected() {
	return selected;
    }
    public void setStatisticalUnitInformation(StatisticalUnitInformation statisticalUnitInformation) {
	this.statisticalUnitInformation = statisticalUnitInformation;
    }
    public StatisticalUnitInformation getStatisticalUnitInformation() {
	return statisticalUnitInformation;
    }
    /**
     * Removes the series from this statistical unit
     * @param selectedSeries
     */
    public void removeSeries(Series selectedSeries) {
	statisticalUnitInformation.getStatisticParameters().getSeries().remove(selectedSeries);

    }
    /**
     * Adds a series to the statistical unit. Sets some default values
     */
    public void addSeries() {
	Series newSeries = new Series();
	newSeries.setSeriesLabel("Please Change This Label");
	statisticalUnitInformation.getStatisticParameters().getSeries().add(newSeries);

    }

}
