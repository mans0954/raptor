/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author philsmart
 *
 */
public class ChartOptions implements Serializable{

    private static final long serialVersionUID = 7186218598290177304L;

    static Logger log = LoggerFactory.getLogger(ChartOptions.class);

    private String graphType;
    private String perspective;
    private int xMajorGridCount;
    private int yMajorGridCount;
    private ChartHeight chartHeight;

    public enum ChartHeight {SMALL(700),MEDIUM(1100),LARGE(1700);
	private int heightInPx;
    	ChartHeight(int heightInPx){
    	    this.heightInPx = heightInPx;
    	}
    	int getHeightInPx(){
    	    return heightInPx;
    	}
    }


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
    public void setChartHeight(ChartHeight chartHeight) {
	this.chartHeight = chartHeight;
    }
    /**
     * used for the view to set heights as integers
     * @param chartHeight
     */
    public void setChartHeight(int chartHeight) {
	for (ChartHeight thisHeight : ChartHeight.values()){
	    if (thisHeight.getHeightInPx()==chartHeight) this.chartHeight = thisHeight;
	}
    }
    public int getChartHeight() {
	return chartHeight.getHeightInPx();
    }

}
