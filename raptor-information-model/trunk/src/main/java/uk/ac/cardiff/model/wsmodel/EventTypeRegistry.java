package uk.ac.cardiff.model.wsmodel;

import java.util.List;

/**
 * 
 * A register that holds information about the types of events this component can utilize. This should be a singleton class, and can be accessed statically.
 * 
 */
public class EventTypeRegistry {

    private List<EventType> eventTypes;

    /**
     * @param eventTypes
     *            the eventTypes to set
     */
    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    /**
     * @return the eventTypes
     */
    public List<EventType> getEventTypes() {
        return eventTypes;
    }

}
