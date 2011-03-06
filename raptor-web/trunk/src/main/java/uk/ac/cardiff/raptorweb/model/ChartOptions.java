/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author philsmart
 * 
 */
public class ChartOptions implements Serializable {

    private static final long serialVersionUID = 7186218598290177304L;

    static Logger log = LoggerFactory.getLogger(ChartOptions.class);

    private ChartType graphType;
    private String perspective;
    private int xMajorGridCount;
    private int yMajorGridCount;
    private ChartHeight chartHeight;
    private OrientationType orientation;
    private int imageWidth;
    private int imageHeight;
    private GraphPresentation graphPresentation;
    

    /* options for how the graph is displayed */
    public enum GraphPresentation {
	FANCY(true), FRONT(false);
	private boolean legend;

	GraphPresentation(boolean legend) {
	    this.legend = legend;
	}
	
	public boolean getLegend(){
	    return legend;
	}
    }

    public enum ChartHeight {
	SMALL(700), MEDIUM(1100), LARGE(1700);
	private int heightInPx;

	ChartHeight(int heightInPx) {
	    this.heightInPx = heightInPx;
	}

	int getHeightInPx() {
	    return heightInPx;
	}
    }
    
    public enum OrientationType {
	HORIZONTAL("Horizontal"), VERTICAL("Vertical");

	private final String label;

	private OrientationType(String label) {
	    this.label = label;
	}

	public String getLabel() {
	    return this.label;
	}

    }

    public enum ChartType {
	LINE("Line Graph"), BAR("Bar Graph"), PIE("Pie Chart"),AREA("Area Graph"), 
	BAR3D ("3D Bar Graph"), LINE3D ("3D Line Graph");

	private final String label;

	private ChartType(String label) {
	    this.label = label;
	}

	public String getLabel() {
	    return this.label;
	}

    }
    
    public SelectItem[] getOrientationTypeList() {
	SelectItem[] items = new SelectItem[OrientationType.values().length];
	int i = 0;
	for (OrientationType t : OrientationType.values()) {
	    items[i++] = new SelectItem(t, t.getLabel());
	}
	return items;
    }

    public SelectItem[] getGraphTypeList() {
	SelectItem[] items = new SelectItem[ChartType.values().length];
	int i = 0;
	for (ChartType g : ChartType.values()) {
	    items[i++] = new SelectItem(g, g.getLabel());
	}
	return items;
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
     * 
     * @param chartHeight
     */
    public void setChartHeight(int chartHeight) {
	for (ChartHeight thisHeight : ChartHeight.values()) {
	    if (thisHeight.getHeightInPx() == chartHeight)
		this.chartHeight = thisHeight;
	}
    }

    public int getChartHeight() {
	return chartHeight.getHeightInPx();
    }

    public void setGraphType(ChartType graphType) {
	log.debug("Setting graph type {}",graphType);
	this.graphType = graphType;
    }

    public ChartType getGraphType() {
	return graphType;
    }

    public void setOrientation(OrientationType orientation) {
	this.orientation = orientation;
    }

    public OrientationType getOrientation() {
	return orientation;
    }

    public void setImageWidth(int imageWidth) {
	this.imageWidth = imageWidth;
    }

    public int getImageWidth() {
	return imageWidth;
    }

    public void setImageHeight(int imageHeight) {
	this.imageHeight = imageHeight;
    }

    public int getImageHeight() {
	return imageHeight;
    }

    public void setGraphPresentation(GraphPresentation graphPresentation) {
	this.graphPresentation = graphPresentation;
    }

    public GraphPresentation getGraphPresentation() {
	return graphPresentation;
    }

}
