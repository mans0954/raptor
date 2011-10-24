/**
 *
 */
package uk.ac.cardiff.model.event;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;
import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class EzproxyAuthenticationEvent.
 * 
 * @author philsmart
 */
public class EzproxyAuthenticationEvent extends AuthenticationEvent {

    /** The requester ip. */
    private String requesterIp;

    /** The session id. */
    private String sessionId;

    /**
     * Instantiates a new ezproxy authentication event.
     */
    public EzproxyAuthenticationEvent() {
        super();
    }

    /**
     * Instantiates a new ezproxy authentication event.
     * 
     * @param event
     *            the event
     */
    protected EzproxyAuthenticationEvent(EzproxyAuthenticationEvent event) {
        super(event);
        this.requesterIp = event.getRequesterIp();
        this.sessionId = event.getSessionId();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the ezproxy authentication event
     */
    public EzproxyAuthenticationEvent copy() {
        return new EzproxyAuthenticationEvent(this);
    }

    /**
     * Sets the requester ip.
     * 
     * @param requesterIp
     *            the new requester ip
     */
    public void setRequesterIp(String requesterIp) {
        this.requesterIp = requesterIp;
    }

    /**
     * Gets the requester ip.
     * 
     * @return the requester ip
     */
    public String getRequesterIp() {
        return requesterIp;
    }

    /**
     * Sets the session id.
     * 
     * @param sessionId
     *            the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the session id.
     * 
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.model.event.Event#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * create a unique hash, with as uniform a distribution as possible.
     * 
     * @return the int
     */
    public int hashCode() {
        int hash = HashCodeUtil.SEED;

        hash = HashCodeUtil.hash(hash, getEventTimeMillis());
        hash = HashCodeUtil.hash(hash, getEventId());
        hash = HashCodeUtil.hash(hash, getAuthenticationType());
        hash = HashCodeUtil.hash(hash, getServiceHost());
        hash = HashCodeUtil.hash(hash, getRequesterIp());
        hash = HashCodeUtil.hash(hash, getSessionId());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());

        return hash;

    }

    /**
     * For hibernate, so the hashcode can be persisted.
     * 
     * @return the hash code
     */
    public int getHashCode() {
        return hashCode();
    }

    /**
     * For hibernate, does nothing as the hascode is computed on the fly from the <code>hashCode</code> method.
     * 
     * @param hashCode
     *            the new hash code
     */
    public void setHashCode(int hashCode) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        EzproxyAuthenticationEvent that = (EzproxyAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getRequesterIp(), that.getRequesterIp()) && EqualsUtil.areEqual(this.getSessionId(), that.getSessionId())
                && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId())
                && EqualsUtil.areEqual(this.getEventType(), that.getEventType()) && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId())
                && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName());

        return areEqual;
    }

}
