/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cardiff.model.MUAMetadata;
import uk.ac.cardiff.model.StatisticParameters.StatisticType;

/**
 * @author philsmart
 *
 */
public class Capabilities implements Serializable{

	private List<String> attached;
	private List<StatisticalUnitInformation> statisticalServices;
	private String errorMessage;
	private boolean error;
	private MUAMetadata muaMetadata;


	public void setStatisticalServices(List<StatisticalUnitInformation> statisticalServices) {
		this.statisticalServices = statisticalServices;
	}
	/**
	 * Returns all configured statistical units
	 * @return
	 */
	public List<StatisticalUnitInformation> getStatisticalServices() {
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
	/**
	 * @param muaMetadata the muaMetadata to set
	 */
	public void setMuaMetadata(MUAMetadata muaMetadata) {
	    this.muaMetadata = muaMetadata;
	}
	/**
	 * @return the muaMetadata
	 */
	public MUAMetadata getMuaMetadata() {
	    return muaMetadata;
	}

}
