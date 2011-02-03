/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 *
 */
public class RaptorJFreeChartModel implements Serializable {
    static Logger log = LoggerFactory.getLogger(RaptorJFreeChartModel.class);

    private File chartLocation;
    private File relativeChartLocation;

    public void setChartLocation(File chartLocation) {
	this.chartLocation = chartLocation;
    }

    public File getChartLocation() {
	try {
	    log.debug("Returning jpg file {}",chartLocation.getCanonicalPath());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
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


}
