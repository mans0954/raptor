/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.ServerMetadata;
import uk.ac.cardiff.model.StatisticParameters.StatisticType;

/**
 * @author philsmart
 *
 */
public class Capabilities implements Serializable{

	/* Generated Serial UID */
    	private static final long serialVersionUID = -4008642148652388534L;


	private List<String> attached;
	private List<StatisticalUnitInformation> statisticalServices;
	private String errorMessage;
	private boolean error;
	private ServerMetadata metadata;
	private SuggestionValues suggestionValues;



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
	public void setSuggestionValues(SuggestionValues suggestionValues) {
	    this.suggestionValues = suggestionValues;
	}
	public SuggestionValues getSuggestionValues() {
	    return suggestionValues;
	}
	public void setMetadata(ServerMetadata metadata) {
	    this.metadata = metadata;
	}
	public ServerMetadata getMetadata() {
	    return metadata;
	}


}
