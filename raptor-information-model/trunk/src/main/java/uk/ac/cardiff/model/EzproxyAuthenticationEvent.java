/**
 *
 */
package uk.ac.cardiff.model;

/**
 * @author philsmart
 *
 */
public class EzproxyAuthenticationEvent {

    private String requesterIP;

    public void setRequesterIP(String requesterIP) {
	this.requesterIP = requesterIP;
    }

    public String getRequesterIP() {
	return requesterIP;
    }

}
