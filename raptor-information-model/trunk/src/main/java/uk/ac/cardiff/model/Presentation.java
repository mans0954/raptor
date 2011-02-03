/**
 *
 */
package uk.ac.cardiff.model;

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

}
