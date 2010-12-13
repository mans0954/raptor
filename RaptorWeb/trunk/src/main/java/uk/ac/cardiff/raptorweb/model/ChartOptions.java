/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * @author philsmart
 *
 */
public class ChartOptions implements Serializable{

    private static final long serialVersionUID = 7186218598290177304L;

    static Logger log = Logger.getLogger(ChartOptions.class);

    private String graphType;
    private String perspective;
    private int xMajorGridCount;
    private int yMajorGridCount;

    public void setGraphType(String graphType) {
	this.graphType = graphType;
    }
    public String getGraphType() {
	return graphType;
    }
    public void setPerspective(String perspective) {
	this.perspective = perspective;
    }
    public String getPerspective() {
	return perspective;
    }
    public void setxMajorGridCount(int xMajorGridCount) {
	this.xMajorGridCount = xMajorGridCount;
    }
    public int getxMajorGridCount() {
	return xMajorGridCount;
    }
    public void setyMajorGridCount(int yMajorGridCount) {
	this.yMajorGridCount = yMajorGridCount;
    }
    public int getyMajorGridCount() {
	return yMajorGridCount;
    }

}
