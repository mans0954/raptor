/**
 *
 */
package uk.ac.cardiff.model;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class Series implements Serializable{


    /* The textual description of the series, as attached to the x-axis */
    private String seriesLabel;
    /* A formatted textual description of the series, typically formatted by the logic of the authentication statistic*/
    private String SeriesLabelFormatted;


    public void setSeriesLabel(String seriesLabel) {
	this.seriesLabel = seriesLabel;
    }

    public String getSeriesLabel() {
	return seriesLabel;
    }

    public void setSeriesLabelFormatted(String seriesLabelFormatted) {
	SeriesLabelFormatted = seriesLabelFormatted;
    }

    public String getSeriesLabelFormatted() {
	return SeriesLabelFormatted;
    }




}
