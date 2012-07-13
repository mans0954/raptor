package uk.ac.cardiff.raptorweb.model;

import java.util.Map;

/**
 * This class provides an XML configured mapping between event class type and UI friendly name. Can also be used to define the set of event types that the UI
 * can display e.g. if a mapping is not provided, the even type is not shown.
 * 
 * @author philsmart
 * 
 */
public class EventTypeDisplayMapper {

    /** Map between event class name and friendly name for display. */
    private Map<String, String> eventTypeFriendlyNameMap;

    /**
     * Returns the friendly name of the event type input.
     * 
     * @param eventType
     *            class name of the event type
     * @return the friendly name of the event type class name.
     */
    public String mapEventType(String eventType) {
        return eventTypeFriendlyNameMap.get(eventType);
    }

    /**
     * @param eventTypeFriendlyNameMap
     *            the eventTypeFriendlyNameMap to set
     */
    public void setEventTypeFriendlyNameMap(Map<String, String> eventTypeFriendlyNameMap) {
        this.eventTypeFriendlyNameMap = eventTypeFriendlyNameMap;
    }

    /**
     * @return the eventTypeFriendlyNameMap
     */
    public Map<String, String> getEventTypeFriendlyNameMap() {
        return eventTypeFriendlyNameMap;
    }

}
