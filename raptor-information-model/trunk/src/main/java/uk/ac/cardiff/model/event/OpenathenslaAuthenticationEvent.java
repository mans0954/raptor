/**
 *
 */
package uk.ac.cardiff.model.event;

import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class OpenathenslaAuthenticationEvent.
 * 
 * @author philsmart
 */
public class OpenathenslaAuthenticationEvent extends Event {

    /** The requester ip. */
    private String requesterIP;

    /**
     * Instantiates a new openathensla authentication event.
     */
    public OpenathenslaAuthenticationEvent() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event
     */
    public OpenathenslaAuthenticationEvent(OpenathenslaAuthenticationEvent event) {
        super(event);
        this.requesterIP = event.getRequesterIP();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the openathensla authentication event
     */
    public OpenathenslaAuthenticationEvent copy() {
        return new OpenathenslaAuthenticationEvent(this);
    }

    /**
     * Sets the requester ip.
     * 
     * @param requesterIP
     *            the new requester ip
     */
    public void setRequesterIP(String requesterIP) {
        this.requesterIP = requesterIP;
    }

    /**
     * Gets the requester ip.
     * 
     * @return the requester ip
     */
    public String getRequesterIP() {
        return requesterIP;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.model.event.Event#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

}
