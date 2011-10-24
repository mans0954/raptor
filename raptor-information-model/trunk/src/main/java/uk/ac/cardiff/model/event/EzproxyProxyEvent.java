/**
 *
 */
package uk.ac.cardiff.model.event;

import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class EzproxyProxyEvent.
 * 
 * @author philsmart
 */
public class EzproxyProxyEvent extends ProxyEvent {

    /** The session id. */
    private String sessionId;

    /**
     * Instantiates a new ezproxy proxy event.
     */
    public EzproxyProxyEvent() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event
     */
    protected EzproxyProxyEvent(EzproxyProxyEvent event) {
        super(event);
        this.sessionId = event.getSessionId();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the ezproxy proxy event
     */
    public EzproxyProxyEvent copy() {
        return new EzproxyProxyEvent(this);
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
     * @see uk.ac.cardiff.model.event.ProxyEvent#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

}
