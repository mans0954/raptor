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

    /**
     * Copy constructor.
     *
     * @param event
     */
    public OpenathenslaAuthenticationEvent(OpenathenslaAuthenticationEvent event){
        super(event);
        this.requesterIP = event.getRequesterIP();
    }

    /**
     * Copy method. Alternative to clone.
     */
    public OpenathenslaAuthenticationEvent copy(){
        return new OpenathenslaAuthenticationEvent(this);
    }


    public void setRequesterIP(String requesterIP) {
	this.requesterIP = requesterIP;
    }

    public String getRequesterIP() {
	return requesterIP;
    }

}
