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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 *
 */
public class RaptorJFreeChartModel implements Serializable {

    private static final long serialVersionUID = -3533889063143775041L;

    static Logger log = LoggerFactory.getLogger(RaptorJFreeChartModel.class);

    private File chartLocation;
    private File relativeChartLocation;
    private JFreeChart chart;

    public void setChartLocation(File chartLocation) {
	this.chartLocation = chartLocation;
    }

    public File getChartLocation() {
	return chartLocation;
    }

    public void setRelativeChartLocation(File relativeChartLocation) {
	this.relativeChartLocation = relativeChartLocation;
    }

    public File getRelativeChartLocation() {
	return relativeChartLocation;
    }

    public String getRelativeChartLocationPath(){
	try {
	    return relativeChartLocation.getCanonicalPath();
	} catch (IOException e) {
	   log.error("Could not get relative chart location for {}",relativeChartLocation);
	}
	return "";
    }

    public void setChart(JFreeChart chart) {
	this.chart = chart;
    }

    public JFreeChart getChart() {
	return chart;
    }


}
