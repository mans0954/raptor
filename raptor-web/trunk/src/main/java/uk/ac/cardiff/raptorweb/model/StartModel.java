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

	public void setNumberOfAuthenticationsPer(double numberOfAuthenticationsPer) {
	    this.numberOfAuthenticationsPer = numberOfAuthenticationsPer;
	}

	public double getNumberOfAuthenticationsPer() {
	    return numberOfAuthenticationsPer;
	}
	
	public String getNumberOfAuthenticationsPerFormatted(){
	    log.debug("Getting authentications");
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




}
