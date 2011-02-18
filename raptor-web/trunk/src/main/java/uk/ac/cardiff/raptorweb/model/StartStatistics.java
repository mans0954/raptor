package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartStatistics implements Serializable{

    static Logger log = LoggerFactory.getLogger(StartStatistics.class);

    private double numberOfAuthenticationsPer;
    private double numberOfUniqueAuthenticationsPer;
    private RaptorTableChartModel topFiveResouces;
    private RaptorTableChartModel bottomFiveResouces;
    private RaptorJFreeChartModel headlineGraph;
    private RaptorTableChartModel topFiveUniqueUsersPerSP;

    /* the time at which these values were computed */
    private DateTime accurateOf;

    public StartStatistics(){
	topFiveResouces=null;
	bottomFiveResouces=null;
    }

    public void setNumberOfAuthenticationsPer(double numberOfAuthenticationsPer) {
	this.numberOfAuthenticationsPer = numberOfAuthenticationsPer;
    }

    public double getNumberOfAuthenticationsPer() {
	return numberOfAuthenticationsPer;
    }

    public String getNumberOfAuthenticationsPerFormatted() {
	return formatDoubleWithCommas(numberOfAuthenticationsPer);
    }

    private String formatDoubleWithCommas(double number) {
	DecimalFormat df = new DecimalFormat();
	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	dfs.setGroupingSeparator(',');
	df.setDecimalFormatSymbols(dfs);
	String formattedNumber = df.format((int) number);
	return formattedNumber;
    }

    public void setNumberOfUniqueAuthenticationsPer(double numberOfUniqueAuthenticationsPer) {
	this.numberOfUniqueAuthenticationsPer = numberOfUniqueAuthenticationsPer;
    }

    public double getNumberOfUniqueAuthenticationsPer() {
	return numberOfUniqueAuthenticationsPer;
    }

    public String getNumberOfUniqueAuthenticationsPerFormatted() {
	return formatDoubleWithCommas(numberOfUniqueAuthenticationsPer);
    }

    public void setTopFiveResouces(RaptorTableChartModel topFiveResouces) {
	this.topFiveResouces = topFiveResouces;
    }

    public RaptorTableChartModel getTopFiveResouces() {
	return topFiveResouces;
    }

    public void setBottomFiveResouces(RaptorTableChartModel bottomFiveResouces) {
	this.bottomFiveResouces = bottomFiveResouces;
    }

    public RaptorTableChartModel getBottomFiveResouces() {
	return bottomFiveResouces;
    }

    public void setHeadlineGraph(RaptorJFreeChartModel headlineGraph) {
	this.headlineGraph = headlineGraph;
    }

    public RaptorJFreeChartModel getHeadlineGraph() {
	return headlineGraph;
    }

    public void setAccurateOf(DateTime accurateOf) {
	this.accurateOf = accurateOf;
    }

    public DateTime getAccurateOf() {
	return accurateOf;
    }

    public String getAccurateOfFormatted() {
	if (accurateOf!=null)
	    return accurateOf.toString("dd/MM/yyyy HH:mm:ss");
	return "not available";
    }

    public void setTopFiveUniqueUsersPerSP(RaptorTableChartModel topFiveUniqueUsersPerSP) {
	this.topFiveUniqueUsersPerSP = topFiveUniqueUsersPerSP;
    }

    public RaptorTableChartModel getTopFiveUniqueUsersPerSP() {
	return topFiveUniqueUsersPerSP;
    }

}
