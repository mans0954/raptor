/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

/**
 * @author philsmart
 *
 */
public class Capabilities implements Serializable{

	private List<String> attached;
	private List<String> statisticalServices;
	private String errorMessage;
	private boolean error;
	
	
	public void setStatisticalServices(List<String> statisticalServices) {
		this.statisticalServices = statisticalServices;
	}
	public List<String> getStatisticalServices() {
		return statisticalServices;
	}
	public void setAttached(List<String> attached) {
		this.attached = attached;
	}
	public List<String> getAttached() {
		return attached;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}
	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

}
