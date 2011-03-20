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
