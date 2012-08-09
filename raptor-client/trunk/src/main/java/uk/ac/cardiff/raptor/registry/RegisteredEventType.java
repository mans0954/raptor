
package uk.ac.cardiff.raptor.registry;

import uk.ac.cardiff.model.event.Event;

/**
 * A concrete event type used in Raptor.
 * 
 * @author philsmart
 * 
 */
public class RegisteredEventType {

    /**
     * The Class type of the event, which must be a type of {@link Event}.
     */
    private Class<? extends Event> eventType;

    /**
     * Is this class a concrete class e.g. is it actually instantiated with event information.
     */
    private boolean concrete;

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(Class<? extends Event> eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public Class<? extends Event> getEventType() {
        return eventType;
    }

    /**
     * @param concrete the concrete to set
     */
    public void setConcrete(boolean concrete) {
        this.concrete = concrete;
    }

    /**
     * @return the concrete
     */
    public boolean isConcrete() {
        return concrete;
    }

}
