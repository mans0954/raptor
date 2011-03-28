/**
 *
 */
package uk.ac.cardiff.model.event;

/**
 * @author philsmart
 *
 */
public class OpenathenslaAuthenticationEvent {

    private String requesterIP;

    public void setRequesterIP(String requesterIP) {
	this.requesterIP = requesterIP;
    }

    public String getRequesterIP() {
	return requesterIP;
    }

}
