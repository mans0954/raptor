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
	private String requestId;
	private String messageProfileId;
	private String responseBinding;
	private String responseId;
	private String requestBinding;
	private String principleName;
	private String nameIdentifier;
	private String[] assertionId;
	private String[] releasedAttributes;



	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getRequestPath() {
		return requestPath;
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
			return getClass().getName()+"@["+getEventTime()+","+requestPath+","+requestBinding+","+requestId+","+responseBinding+","+Arrays.asList(releasedAttributes)+"]";
		else
			return getClass().getName()+"@["+getEventTime()+","+requestPath+","+requestBinding+","+requestId+","+responseBinding+ "]";

	}

	@Override
	public boolean equals(Object obj){
	    if ( this == obj ) return true;
	    if((obj == null) || (obj.getClass() != this.getClass()))
		return false;
	    ShibbolethEntry that = (ShibbolethEntry)obj;
	    boolean areEqual =
	      EqualsUtil.areEqual(this.getEventTime(), that.getEventTime()) &&
	      EqualsUtil.areEqual(this.getAuthNMethod(), that.getAuthNMethod()) &&
	      EqualsUtil.areEqual(this.getRequestHost(), that.getRequestHost()) &&
	      EqualsUtil.areEqual(this.getRequestHostFriendlyName(), that.getRequestHostFriendlyName()) &&
	      EqualsUtil.areEqual(this.getRequestId(), that.getRequestId()) &&
	      EqualsUtil.areEqual(this.getRequestPath(), that.getRequestPath()) &&
	      EqualsUtil.areEqual(this.getResponseBinding(), that.getResponseBinding()) &&
	      EqualsUtil.areEqual(this.getServerHost(), that.getServerHost()) &&
	      EqualsUtil.areEqual(this.getMessageProfileId(), that.getMessageProfileId()) &&
	      EqualsUtil.areEqual(this.getRequestBinding(), that.getRequestBinding()) &&
	      EqualsUtil.areEqual(this.getPrincipleName(), that.getPrincipleName()) &&
	      EqualsUtil.areEqual(this.getServerHostFriendlyName(), that.getServerHostFriendlyName()) &&
	      EqualsUtil.areEqual(this.getNameIdentifier(), that.getNameIdentifier()) &&
	      EqualsUtil.areEqual(this.getResponseId(), that.getResponseId()) &&
	      Arrays.equals(this.getAssertionId(), that.getAssertionId()) &&
	      Arrays.equals(this.getReleasedAttributes(), that.getReleasedAttributes());

	    return areEqual;
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
	    hash = HashCodeUtil.hash(hash,getRequestId());
	    hash = HashCodeUtil.hash(hash,getRequestPath());
	    hash = HashCodeUtil.hash(hash,getResponseBinding());
	    hash = HashCodeUtil.hash(hash,getServerHost());
	    hash = HashCodeUtil.hash(hash,getServerHostFriendlyName());
	    hash = HashCodeUtil.hash(hash,getReleasedAttributes());
	    hash = HashCodeUtil.hash(hash,getMessageProfileId());
	    hash = HashCodeUtil.hash(hash,getRequestBinding());
	    hash = HashCodeUtil.hash(hash,getPrincipleName());
	    hash = HashCodeUtil.hash(hash,getNameIdentifier());
	    hash = HashCodeUtil.hash(hash,getResponseId());
	    hash = HashCodeUtil.hash(hash,getAssertionId());

	    return hash;

	}
	public void setNameIdentifier(String nameIdentifier) {
	    this.nameIdentifier = nameIdentifier;
	}
	public String getNameIdentifier() {
	    return nameIdentifier;
	}
	public void setAssertionId(String[] assertionId) {
	    this.assertionId = assertionId;
	}
	public String[] getAssertionId() {
	    return assertionId;
	}
	public void setResponseId(String responseId) {
	    this.responseId = responseId;
	}
	public String getResponseId() {
	    return responseId;
	}
	public void setRequestId(String requestId) {
	    this.requestId = requestId;
	}
	public String getRequestId() {
	    return requestId;
	}






}
