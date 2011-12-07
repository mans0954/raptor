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
 * Wraps a <code>StatiticalUnitInformation</code> for the view component, adding a 
 * <code>boolean</code> value which tells the view if the statistic is currently active
 * 
 * @author philsmart
 *
 */
public class StatisticalUnitInformationView implements Serializable{

    private static final long serialVersionUID = 4655547623449270295L;

    /** Whether this statistic is selected*/
    private boolean selected;
    
    /** The wrapped statistical unit information */
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
