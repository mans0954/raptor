package uk.ac.cardiff.model.event;

public class RadiusAuthenticationEvent extends AuthenticationEvent {
    /*
     * grep for Auth Login OK AND grep for without @ in the line
     * 
     * DAY DATE, principalName,
     */

    private String clientIdentifier;

    /**
     * Copy constructor.
     * 
     * @param event
     *            the {@link RadiusAuthenticationEvent} to copy.
     */
    protected RadiusAuthenticationEvent(RadiusAuthenticationEvent event) {
        super(event);
        this.clientIdentifier = event.clientIdentifier;
    }

    /**
     * @param clientIdentifier
     *            the clientIdentifier to set
     */
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    /**
     * @return the clientIdentifier
     */
    public String getClientIdentifier() {
        return clientIdentifier;
    }
}
