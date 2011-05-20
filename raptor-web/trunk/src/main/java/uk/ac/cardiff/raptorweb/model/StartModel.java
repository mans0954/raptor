/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.EventType;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.ResourceCategory;

/**
 * @author philsmart
 * 
 */
public class StartModel implements Serializable {

    /** Generated Serial UID */
    private static final long serialVersionUID = 6795818266913419538L;

    /** The Class logger */
    private final Logger log = LoggerFactory.getLogger(StartModel.class);

    private Capabilities attachedMUACapabilities;

    /** Holds the statistics used by the dashboard on the front page */
    private StartStatistics startStatistics;

    private TimeRange statsRangeSelector;

    public enum TimeRange {
        TODAY, LASTWEEK, LASTMONTH, LASTYEAR
    }

    public enum EventType {
        /** A Shibboleth Authentication Event Type */
        SHIBBOLETH_AUTHENTICATION,
        /** An Ezproxy Authentication Event Type */
        EZPROXY_AUTHENTICATION;
    }

    private EventType eventType;

    private ResourceCategory resourceCategory;

    public StartModel() {
        statsRangeSelector = TimeRange.TODAY;
        eventType = EventType.SHIBBOLETH_AUTHENTICATION;
        resourceCategory = resourceCategory.ALL;
    }

    public void setAttachedMUACapabilities(Capabilities attachedMUACapabilities) {
        this.attachedMUACapabilities = attachedMUACapabilities;
    }

    public Capabilities getAttachedMUACapabilities() {
        return attachedMUACapabilities;
    }

    /**
     * Sets the statsRangeSelector as a String which is mapped to the correct enum type (used mainly by view components that do not deal with enums well)
     * 
     * @param timeRange
     */
    public void setStatsRangeSelectorString(String timeRange) {
        for (TimeRange time : TimeRange.values()) {
            if (time.toString().equals(timeRange))
                statsRangeSelector = time;
        }
    }

    public TimeRange getStatsRangeSelector() {
        return statsRangeSelector;
    }

    /**
     * Gets the statsRangeSelector value as a string (used mainly by view components that do not deal with enums well)
     * 
     * @return
     */
    public String getStatsRangeSelectorString() {
        return statsRangeSelector.toString();
    }

    public String getEventTypeString() {
        return eventType.toString();
    }

    public void setEventTypeString(String eventTypeString) {
        for (EventType type : EventType.values()) {
            if (type.toString().equals(eventTypeString))
                eventType = type;
        }
    }

    public void setStartStatistics(StartStatistics startStatistics) {
        this.startStatistics = startStatistics;
    }

    public StartStatistics getStartStatistics() {
        return startStatistics;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    /**
     * @param resourceCategory
     *            the resourceCategory to set
     */
    public void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    /**
     * @return the resourceCategory
     */
    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategoryString(String resourceCategory) {
        for (ResourceCategory type : ResourceCategory.values()) {
            if (type.toString().equals(resourceCategory)) {
                this.resourceCategory = type;
            }
        }
    }

    public String getResourceCategoryString() {
        return resourceCategory.toString();
    }

}
