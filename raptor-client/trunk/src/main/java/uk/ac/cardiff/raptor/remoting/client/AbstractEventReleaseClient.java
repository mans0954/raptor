
package uk.ac.cardiff.raptor.remoting.client;

import java.util.List;

import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.registry.EndpointRegistry;

public abstract class AbstractEventReleaseClient implements EventReleaseClient {

    /** Whether events should be released. Defaults to true. */
    protected boolean enableEventRelease;

    /** Encapsulation of all endpoints this client can communication with. */
    protected EndpointRegistry endpointRegistry;

    public List<Endpoint> getEndpoints() {
        return endpointRegistry.getEndpoints();
    }

    public boolean isEnabled() {
        return enableEventRelease;
    }

    /**
     * Sets the enable event release.
     * 
     * @param enableEventRelease the enableEventRelease to set
     */
    public void setEnableEventRelease(boolean enableEventRelease) {
        this.enableEventRelease = enableEventRelease;
    }

    /**
     * Checks if is enable event release.
     * 
     * @return the enableEventRelease
     */
    public boolean isEnableEventRelease() {
        return enableEventRelease;
    }

    /**
     * Sets the endpoint registry.
     * 
     * @param endpointRegistry the new endpoint registry
     */
    public void setEndpointRegistry(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }

    /**
     * Gets the endpoint registry.
     * 
     * @return the endpoint registry
     */
    public EndpointRegistry getEndpointRegistry() {
        return endpointRegistry;
    }

}
