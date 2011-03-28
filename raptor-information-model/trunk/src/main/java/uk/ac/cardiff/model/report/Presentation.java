/**
 *
 */
package uk.ac.cardiff.model.report;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class Presentation implements Serializable{

    /* the title of the graph */
    private String graphTitle;

    /* the label displayed on the xAxis*/
    private String xAxisLabel;

    /* the label displayed on the yAxis */
    private String yAxisLabel;

    /* the user can specify a date format per graph if the graph is a time series*/
    private String dateFormat;


    public void setGraphTitle(String graphTitle) {
	this.graphTitle = graphTitle;
    }
    public String getGraphTitle() {
	return graphTitle;
    }
    public void setxAxisLabel(String xAxisLabel) {
	this.xAxisLabel = xAxisLabel;
    }
    public String getxAxisLabel() {
	return xAxisLabel;
    }
    public void setyAxisLabel(String yAxisLabel) {
	this.yAxisLabel = yAxisLabel;
    }
    public String getyAxisLabel() {
	return yAxisLabel;
    }
    public void setDateFormat(String dateFormat) {
	this.dateFormat = dateFormat;
    }
    public String getDateFormat() {
	return dateFormat;
    }

}
