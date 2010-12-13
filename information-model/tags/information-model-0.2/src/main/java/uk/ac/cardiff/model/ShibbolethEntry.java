/**
 *
 */
package uk.ac.cardiff.model;

import java.util.Arrays;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;

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
	private String[] releasedAttributes;



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
	/**
	 * @param releasedAttributes the releasedAttributes to set
	 */
	public void setReleasedAttributes(String[] releasedAttributes) {
	    this.releasedAttributes = releasedAttributes;
	}
	/**
	 * @return the releasedAttributes
	 */
	public String[] getReleasedAttributes() {
	    return releasedAttributes;
	}

	public String toString(){
		if (releasedAttributes!=null || releasedAttributes.length>0)
			return getClass().getName()+"@["+getEventTime()+","+requestPath+","+requestBinding+","+requestID+","+responseBinding+","+Arrays.asList(releasedAttributes)+"]";
		else
			return getClass().getName()+"@["+getEventTime()+","+requestPath+","+requestBinding+","+requestID+","+responseBinding+ "]";

	}

	@Override
	public boolean equals(Object obj){
	    if ( this == obj ) return true;
	    if((obj == null) || (obj.getClass() != this.getClass()))
		return false;

	    ShibbolethEntry that = (ShibbolethEntry)obj;
	    return
	      EqualsUtil.areEqual(this.getEventTime(), that.getEventTime()) &&
	      EqualsUtil.areEqual(this.getAuthNMethod(), that.getAuthNMethod()) &&
	      EqualsUtil.areEqual(this.getRequestHost(), that.getRequestHost()) &&
	      EqualsUtil.areEqual(this.getRequestHostFriendlyName(), that.getRequestHostFriendlyName()) &&
	      EqualsUtil.areEqual(this.getRequestID(), that.getRequestID()) &&
	      EqualsUtil.areEqual(this.getRequestPath(), that.getRequestPath()) &&
	      EqualsUtil.areEqual(this.getResponseBinding(), that.getResponseBinding()) &&
	      EqualsUtil.areEqual(this.getServerHost(), that.getServerHost()) &&
	      EqualsUtil.areEqual(this.getMessageProfileId(), that.getMessageProfileId()) &&
	      EqualsUtil.areEqual(this.getRequestBinding(), that.getRequestBinding()) &&
	      EqualsUtil.areEqual(this.getPrincipleName(), that.getPrincipleName()) &&
	      EqualsUtil.areEqual(this.getServerHostFriendlyName(), that.getServerHostFriendlyName()) &&
	      Arrays.equals(this.getReleasedAttributes(), that.getReleasedAttributes());
	}


	/**
	 * create a unique hash, with as uniform a distribution as possible
	 */
	@Override
	public int hashCode(){
	    int hash = HashCodeUtil.SEED;

	    hash = HashCodeUtil.hash(hash,getEventTime());
	    hash = HashCodeUtil.hash(hash,getAuthNMethod());
	    hash = HashCodeUtil.hash(hash,getRequestHost());
	    hash = HashCodeUtil.hash(hash,getRequestHostFriendlyName());
	    hash = HashCodeUtil.hash(hash,getRequestID());
	    hash = HashCodeUtil.hash(hash,getRequestPath());
	    hash = HashCodeUtil.hash(hash,getResponseBinding());
	    hash = HashCodeUtil.hash(hash,getServerHost());
	    hash = HashCodeUtil.hash(hash,getServerHostFriendlyName());
	    hash = HashCodeUtil.hash(hash,getReleasedAttributes());
	    hash = HashCodeUtil.hash(hash,getMessageProfileId());
	    hash = HashCodeUtil.hash(hash,getRequestBinding());
	    hash = HashCodeUtil.hash(hash,getPrincipleName());

	    return hash;

	}






}
