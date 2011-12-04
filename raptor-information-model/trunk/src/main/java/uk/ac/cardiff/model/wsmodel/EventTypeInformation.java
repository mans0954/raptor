package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

public class EventTypeInformation implements Serializable {

    /**
     * Generated SerialUID
     */
    private static final long serialVersionUID = 8560185706003251298L;

    private String eventTypeName;

    private long noOfEvents;

    public EventTypeInformation(String eventType, long count) {
        eventTypeName = eventType;
        noOfEvents = count;
    }

    /**
     * Default constructor.
     */
    public EventTypeInformation() {

    }

    /**
     * @param noOfEvents
     *            the noOfEvents to set
     */
    public void setNoOfEvents(long noOfEvents) {
        this.noOfEvents = noOfEvents;
    }

    /**
     * @return the noOfEvents
     */
    public long getNoOfEvents() {
        return noOfEvents;
    }

    /**
     * @param eventTypeName
     *            the eventTypeName to set
     */
    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    /**
     * @return the eventTypeName
     */
    public String getEventTypeName() {
        return eventTypeName;
    }
}