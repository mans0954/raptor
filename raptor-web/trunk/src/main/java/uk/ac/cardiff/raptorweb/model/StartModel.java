/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */
public class StartModel implements Serializable{

	static Logger log = LoggerFactory.getLogger(StartModel.class);

	private double numberOfAuthenticationsPer;
	private double numberOfUniqueAuthenticationsPer;
	private RaptorTableChartModel topFiveResouces;
	private RaptorTableChartModel bottomFiveResouces;

	public void setNumberOfAuthenticationsPer(double numberOfAuthenticationsPer) {
	    this.numberOfAuthenticationsPer = numberOfAuthenticationsPer;
	}

	public double getNumberOfAuthenticationsPer() {
	    return numberOfAuthenticationsPer;
	}

	public String getNumberOfAuthenticationsPerFormatted(){
	    return formatDoubleWithCommas(numberOfAuthenticationsPer);
	}

	private String formatDoubleWithCommas(double number){
	    DecimalFormat df = new DecimalFormat();
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setGroupingSeparator(',');
	    df.setDecimalFormatSymbols(dfs);
	    String formattedNumber = df.format((int)number);
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




}
