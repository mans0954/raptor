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
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jfree.chart.axis.CategoryLabelPositions;
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
    private OrientationType orientation;
    private int imageWidth;
    private int imageHeight;   
    
    private GraphPresentation graphPresentation;
    private LabelPositionType xLabelPosition;
    
    /* the orientation of labels, mapped to JFreeChart <code>CategoryLabelPositions</code> */
    public enum LabelPositionType{
	UP_45(CategoryLabelPositions.UP_45, "45 Degree Up"), UP_90(CategoryLabelPositions.UP_90, "90 Degree Up"),
	DOWN_45(CategoryLabelPositions.DOWN_45, "45 Degree Down"),DOWN_90(CategoryLabelPositions.DOWN_90, "90 Degree Down"),
	STANDARD(CategoryLabelPositions.STANDARD, "Standard");
	
	private CategoryLabelPositions labelPosition;
	private String label;
	
	LabelPositionType(CategoryLabelPositions labelPosition, String label){
	    this.setLabelPosition(labelPosition);
	}

	public void setLabel(String label) {
	    this.label = label;
	}

	public String getLabel() {
	    return label;
	}

	public void setLabelPosition(CategoryLabelPositions labelPosition) {
	    this.labelPosition = labelPosition;
	}

	public CategoryLabelPositions getLabelPosition() {
	    return labelPosition;
	}
	
    }
    
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
	LINE("Line Graph"), BAR("Bar Graph"),AREA("Area Graph"), 
	BAR3D ("3D Bar Graph"), LINE3D ("3D Line Graph");

	private final String label;

	private ChartType(String label) {
	    this.label = label;
	}

	public String getLabel() {
	    return this.label;
	}

    }
    
    public SelectItem[] getLabelPositionsTypeList() {
	SelectItem[] items = new SelectItem[LabelPositionType.values().length];
	int i = 0;
	for (LabelPositionType t : LabelPositionType.values()) {
	    items[i++] = new SelectItem(t, t.getLabel());
	}
	return items;
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

    public void setxLabelPosition(LabelPositionType xLabelPosition) {
	this.xLabelPosition = xLabelPosition;
    }

    public LabelPositionType getxLabelPosition() {
	return xLabelPosition;
    }


}
