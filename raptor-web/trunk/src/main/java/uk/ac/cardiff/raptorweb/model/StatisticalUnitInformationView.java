/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

/**
 * @author philsmart
 *
 */
public class StatisticalUnitInformationView implements Serializable{

    private boolean selected;
    private StatisticalUnitInformation statisticalIUnitInformation;


    public void setSelected(boolean selected) {
	this.selected = selected;
    }
    public boolean isSelected() {
	return selected;
    }
    public void setStatisticalIUnitInformation(StatisticalUnitInformation statisticalIUnitInformation) {
	this.statisticalIUnitInformation = statisticalIUnitInformation;
    }
    public StatisticalUnitInformation getStatisticalIUnitInformation() {
	return statisticalIUnitInformation;
    }

}
