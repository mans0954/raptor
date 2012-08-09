
package uk.ac.cardiff.raptor.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * All event types used in Raptor are registered here. This class should be a singleton and injected into any class that
 * requires knowledge of 'usable' event types.
 * 
 * @author philsmart
 * 
 */
public final class EventTypeRegistry {

    /**
     * The list of {@link RegisteredEventType}s.
     */
    private List<RegisteredEventType> registeredEventTypes;

    /**
     * Returns only those {@link RegisteredEventType}s from the list <code>registeredEventTypes</code> that are concrete
     * event types (instantiated).
     * 
     * @return a list of concrete registered event types.
     */
    public final List<RegisteredEventType> getAllConcreteEventTypes() {
        List<RegisteredEventType> returnEventTypes = new ArrayList<RegisteredEventType>();
        for (RegisteredEventType eventType : registeredEventTypes) {
            if (eventType.isConcrete()) {
                returnEventTypes.add(eventType);
            }
        }
        return returnEventTypes;
    }

    /**
     * @param registeredEventTypes the registeredEventTypes to set
     */
    public void setRegisteredEventTypes(List<RegisteredEventType> registeredEventTypes) {
        this.registeredEventTypes = registeredEventTypes;
    }

    /**
     * @return an unmodifiable list of the registeredEventTypes
     */
    public List<RegisteredEventType> getRegisteredEventTypes() {
        return Collections.unmodifiableList(registeredEventTypes);
    }

}
