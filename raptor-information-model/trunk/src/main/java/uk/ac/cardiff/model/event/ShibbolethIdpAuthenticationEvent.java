/**
 *
 */
package uk.ac.cardiff.model.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;

/**
 * @author philsmart
 *
 */
public class ShibbolethIdpAuthenticationEvent extends AuthenticationEvent{


	private String requestId;
	private String messageProfileId;
	private String responseBinding;
	private String responseId;
	private String requestBinding;
	private String nameIdentifier;
	private String[] assertionId;
	private String[] releasedAttributes;


	public ShibbolethIdpAuthenticationEvent(){
	    super();
	}

	public static ShibbolethIdpAuthenticationEvent newInstance() {
	    return new ShibbolethIdpAuthenticationEvent();
	  }

	/**
	 * Copy constructor
	 *
	 * @param event
	 */
	protected ShibbolethIdpAuthenticationEvent(ShibbolethIdpAuthenticationEvent event){
	    super (event);
	    this.requestId = event.getRequestId();
	    this.messageProfileId = event.getMessageProfileId();
	    this.responseBinding = event.getResponseBinding();
	    this.responseId = event.getResponseId();
	    this.requestBinding = event.getRequestBinding();
	    this.nameIdentifier = event.getNameIdentifier();

	    //shallow copy is OK here, as a new array is created with immutable objects (String).
	    this.assertionId = event.getAssertionId().clone();
	    this.releasedAttributes = event.getReleasedAttributes().clone();
	}

	    /**
	     * Copy method. Alternative to clone.
	     */
	 public ShibbolethIdpAuthenticationEvent copy(){
	      return new ShibbolethIdpAuthenticationEvent(this);
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

	public String toString() {
		Method[] methods = this.getClass().getMethods();
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass() + "@[");
		for (Method method : methods) {
		    try {
			if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
			    this.getClass().getMethod(method.getName(), (Class[]) null);
			    Object object = method.invoke(this, (Object[]) null);
			    if (object instanceof Collection){
				 builder.append(method.getName() + " [" + Arrays.asList(object) + "],");
			    }
			    else if (object.getClass().isArray()){
			        Object[] array = (Object[])object;
			        builder.append(method.getName() + " [" + Arrays.asList(array) + "],");
			    }
			    else{
				builder.append(method.getName() + " [" + object + "],");
			    }
		      }
		    } catch (Exception e){
			//do nothing
		    }
		}
		builder.append("]");
		return builder.toString();
	 }

	@Override
	public boolean equals(Object obj){
	    if ( this == obj ) return true;
	    if((obj == null) || (obj.getClass() != this.getClass()))
		return false;
	    ShibbolethIdpAuthenticationEvent that = (ShibbolethIdpAuthenticationEvent)obj;
	    boolean areEqual =
	      EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) &&
	      EqualsUtil.areEqual(this.getEventId(), that.getEventId()) &&
	      EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) &&
	      EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost()) &&
	      EqualsUtil.areEqual(this.getRequestId(), that.getRequestId()) &&
	      EqualsUtil.areEqual(this.getResponseBinding(), that.getResponseBinding()) &&
	      EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) &&
	      EqualsUtil.areEqual(this.getMessageProfileId(), that.getMessageProfileId()) &&
	      EqualsUtil.areEqual(this.getRequestBinding(), that.getRequestBinding()) &&
	      EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName()) &&
	      EqualsUtil.areEqual(this.getNameIdentifier(), that.getNameIdentifier()) &&
	      EqualsUtil.areEqual(this.getResponseId(), that.getResponseId()) &&
	      EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) &&
	      EqualsUtil.areEqual(this.getEventType(), that.getEventType()) &&
	      EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) &&
	      Arrays.equals(this.getAssertionId(), that.getAssertionId()) &&
	      Arrays.equals(this.getReleasedAttributes(), that.getReleasedAttributes());

	    return areEqual;
	}

	/**
	 * For hibernate, so the hashcode can be persisted
	 * @return
	 */
	public int getHashCode(){
	    return hashCode();
	}

	/**
	 * For hibernate, does nothing as the hascode is computed on the fly
	 * from the <code>hashCode</code> method
	 *
	 * @param hashCode
	 */
	public void setHashCode(int hashCode){

	}


	/**
	 * create a unique hash, with as uniform a distribution as possible
	 */
	@Override
	public int hashCode(){
	    int hash = HashCodeUtil.SEED;

	    hash = HashCodeUtil.hash(hash,getEventTimeMillis());
	    hash = HashCodeUtil.hash(hash,getAuthenticationType());
	    hash = HashCodeUtil.hash(hash,getEventId());
	    hash = HashCodeUtil.hash(hash,getServiceHost());
	    hash = HashCodeUtil.hash(hash,getRequestId());
	    hash = HashCodeUtil.hash(hash,getResponseBinding());
	    hash = HashCodeUtil.hash(hash,getResourceHost());
	    hash = HashCodeUtil.hash(hash,getReleasedAttributes());
	    hash = HashCodeUtil.hash(hash,getMessageProfileId());
	    hash = HashCodeUtil.hash(hash,getRequestBinding());
	    hash = HashCodeUtil.hash(hash,getPrincipalName());
	    hash = HashCodeUtil.hash(hash,getNameIdentifier());
	    hash = HashCodeUtil.hash(hash,getResponseId());
	    hash = HashCodeUtil.hash(hash,getAssertionId());
	    hash = HashCodeUtil.hash(hash,getEventType());
	    hash = HashCodeUtil.hash(hash,getServiceId());
	    hash = HashCodeUtil.hash(hash,getResourceId());
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
