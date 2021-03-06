/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.ResourceCategory;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet.TimeRange;

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

    /**
     * The event type (classname) used to select the correct dashboard statistics to display.
     */
    private String eventType;

    /** The type of resource. */
    private ResourceCategory resourceCategory;

    /**
     * Defines a number of default values for the start model so the UI is seeded with initial values. These are:
     * <ol>
     * <li>statsRangeSelector = TimeRange.TODAY</li>
     * <li>eventType = "uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent"</li>
     * <li>resourceCategory = resourceCategory.ALL</li>
     * </ol>
     */
    public StartModel() {
        statsRangeSelector = TimeRange.TODAY;
        eventType = "uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent";
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

    public void setStartStatistics(StartStatistics startStatistics) {
        this.startStatistics = startStatistics;
    }

    public StartStatistics getStartStatistics() {
        return startStatistics;
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

    /**
     * @param eventType
     *            the eventType to set
     */
    public void setEventType(String eventType) {
        log.debug("Setting event type dashboard: " + eventType);
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

}
