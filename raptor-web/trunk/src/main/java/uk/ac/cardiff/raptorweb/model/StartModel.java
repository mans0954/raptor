/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;



import org.apache.log4j.Logger;

import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */
public class StartModel implements Serializable{

	static Logger log = Logger.getLogger(StartModel.class);

	private int numberOfAuthenticationsPer;

	public void setNumberOfAuthenticationsPer(int numberOfAuthenticationsPer) {
	    this.numberOfAuthenticationsPer = numberOfAuthenticationsPer;
	}

	public int getNumberOfAuthenticationsPer() {
	    return numberOfAuthenticationsPer;
	}




}
