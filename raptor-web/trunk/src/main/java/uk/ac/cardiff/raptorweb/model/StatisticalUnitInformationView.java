/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.List;

import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.sql.ComparisonPredicate;
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
    public void addFilterToSeries(Series selectedSeries) {
	List<Series> seriesList = statisticalUnitInformation.getStatisticParameters().getSeries();
	for (Series series: seriesList){
	    if (series == selectedSeries){
		if (series.getComparisonPredicate()==null)
		    series.setComparisonPredicate(new ComparisonPredicate());
	    }
	}
	
    }
    public void removeFilterFromSeries(Series selectedSeries) {
	List<Series> seriesList = statisticalUnitInformation.getStatisticParameters().getSeries();
	for (Series series: seriesList){
	    if (series == selectedSeries){
		series.setComparisonPredicate(null);
	    }
	}
	
    }

}
