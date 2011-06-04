/**
 *
 */
package uk.ac.cardiff.model;

import java.util.Date;

/**
 * @author philsmart
 *
 */
public class AdministrativeFunction {

    /* enumeration of the different types of administrative function allowed in the setup page*/
    public static enum AdministrativeFunctionType {REMOVEALL}
    private Date requestCreationTime;
    private String requester;
    private AdministrativeFunctionType administrativeFunction;

    /**
     * Sets the <code>requestCreationTime</code> to be the time this class was instantiated
     */
    public AdministrativeFunction(){
	requestCreationTime = new Date(System.currentTimeMillis());
    }

    public Date getRequestCreationTime() {
	return requestCreationTime;
    }
    public void setRequestCreationTime(Date requestCreationTime){
	this.requestCreationTime = requestCreationTime;
    }
    public void setRequester(String requester) {
	this.requester = requester;
    }
    public String getRequester() {
	return requester;
    }
    public void setAdministrativeFunction(AdministrativeFunctionType administrativeFunction) {
	this.administrativeFunction = administrativeFunction;
    }
    public AdministrativeFunctionType getAdministrativeFunction() {
	return administrativeFunction;
    }


}
