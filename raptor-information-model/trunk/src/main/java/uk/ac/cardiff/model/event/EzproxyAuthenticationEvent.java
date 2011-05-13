/**
 *
 */
package uk.ac.cardiff.model.event;

import java.lang.reflect.Method;
import java.util.Arrays;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;

/**
 * @author philsmart
 *
 */
public class EzproxyAuthenticationEvent extends AuthenticationEvent{

    private String requesterIp;
    private String sessionId;

    public EzproxyAuthenticationEvent(){
        super();
    }

    protected EzproxyAuthenticationEvent(EzproxyAuthenticationEvent event){
        super(event);
        this.requesterIp = event.getRequesterIp();
        this.sessionId = event.getSessionId();
    }

    /**
     * Copy method. Alternative to clone.
     */
    public EzproxyAuthenticationEvent copy(){
        return new EzproxyAuthenticationEvent(this);
    }

    public void setRequesterIp(String requesterIp) {
	this.requesterIp = requesterIp;
    }

    public String getRequesterIp() {
	return requesterIp;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
	return sessionId;
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
		    builder.append(method.getName() + " [" + object + "],");

		}
	    } catch (Exception e){
		//do nothing
	    }
	}
	builder.append("]");
	return builder.toString();
    }


    /**
     * create a unique hash, with as uniform a distribution as possible
     */
    public int hashCode(){
        int hash = HashCodeUtil.SEED;

        hash = HashCodeUtil.hash(hash,getEventTimeMillis());
        hash = HashCodeUtil.hash(hash,getEventId());
        hash = HashCodeUtil.hash(hash,getAuthenticationType());
        hash = HashCodeUtil.hash(hash,getServiceHost());
        hash = HashCodeUtil.hash(hash,getRequesterIp());
        hash = HashCodeUtil.hash(hash,getSessionId());
        hash = HashCodeUtil.hash(hash,getResourceHost());
        hash = HashCodeUtil.hash(hash,getPrincipalName());
        hash = HashCodeUtil.hash(hash,getEventType());
        hash = HashCodeUtil.hash(hash,getServiceId());
        hash = HashCodeUtil.hash(hash,getResourceId());


        return hash;

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

    public boolean equals(Object obj){
        if ( this == obj ) return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        EzproxyAuthenticationEvent that = (EzproxyAuthenticationEvent)obj;
        boolean areEqual =
          EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) &&
          EqualsUtil.areEqual(this.getEventId(), that.getEventId()) &&
          EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) &&
          EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost()) &&
          EqualsUtil.areEqual(this.getRequesterIp(), that.getRequesterIp()) &&
          EqualsUtil.areEqual(this.getSessionId(), that.getSessionId()) &&
          EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) &&
          EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) &&
          EqualsUtil.areEqual(this.getEventType(), that.getEventType()) &&
          EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) &&
          EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName());

        return areEqual;
    }

}
