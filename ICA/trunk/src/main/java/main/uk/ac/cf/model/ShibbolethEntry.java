/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public class ShibbolethEntry extends AuthenticationEntry{
	private String requestPath;
	private String requestID;
	private String messageProfileId;
	private String responseBinding;
	private String requestBinding;
	private String principleName;



	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getRequestPath() {
		return requestPath;
	}


	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getRequestID() {
		return requestID;
	}

	public void setResponseBinding(String responseBinding) {
		this.responseBinding = responseBinding;
	}
	public String getResponseBinding() {
		return responseBinding;
	}

	public void setRequestBinding(String requestBinding) {
		this.requestBinding = requestBinding;
	}
	public String getRequestBinding() {
		return requestBinding;
	}
	public void setMessageProfileId(String messageProfileId) {
		this.messageProfileId = messageProfileId;
	}
	public String getMessageProfileId() {
		return messageProfileId;
	}
	public void setPrincipleName(String principleName) {
		this.principleName = principleName;
	}
	public String getPrincipleName() {
		return principleName;
	}

}
