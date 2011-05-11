/**
 *
 */
package uk.ac.cardiff.model.event;

/**
 * @author philsmart
 *
 */
public class OpenathenslaAuthenticationEvent extends Event{

    private String requesterIP;

    public OpenathenslaAuthenticationEvent(){
        super();
    }
    
    public OpenathenslaAuthenticationEvent(OpenathenslaAuthenticationEvent event){
        super(event);
        this.requesterIP = event.getRequesterIP();
    }
    
    public void setRequesterIP(String requesterIP) {
	this.requesterIP = requesterIP;
    }

    public String getRequesterIP() {
	return requesterIP;
    }

}
