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
package uk.ac.cardiff.model.report;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class Presentation implements Serializable{

    /** Generated Serial UID*/
    private static final long serialVersionUID = -4241388640686604177L;

    /** the title of the graph */
    private String graphTitle;

    /** the label displayed on the xAxis*/
    private String xAxisLabel;

    /** the label displayed on the yAxis */
    private String yAxisLabel;

    /** the user can specify a date format per graph if the graph is a time series*/
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
