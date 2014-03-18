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
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class StatisticFunctionType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5487773795974762469L;

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(StatisticFunctionType.class);

    /**
     * Statistic *should* have a friendly name set for later display through a suitable UI.
     */
    private String friendlyName;

    /**
     * A human readable description of what the statistic type does. This *should* be set.
     */
    private String description;

    /**
     * The actual class that this type refers to, must extend the {@link BaseStatistic} class, otherwise its not valid.
     */
    private String statisticClass;

    /**
     * A list of the event types (fully qualified class names) this statistic function can be used with.
     */
    private List<String> appliesToEventTypes;

    /**
     * @return Returns the friendlyName.
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * @param friendlyName
     *            The friendlyName to set.
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the statisticClass.
     */
    public String getStatisticClass() {
        return statisticClass;
    }

    /**
     * @param statisticClass
     *            The statisticClass to set.
     */
    public void setStatisticClass(String statisticClass) {
        log.info("Registered the statistic type [{}]", statisticClass);
        this.statisticClass = statisticClass;
    }

    /**
     * @return Returns the appliesToEventTypes.
     */
    public List<String> getAppliesToEventTypes() {
        return appliesToEventTypes;
    }

    /**
     * @param appliesToEventTypes
     *            The appliesToEventTypes to set.
     */
    public void setAppliesToEventTypes(List<String> appliesToEventTypes) {
        this.appliesToEventTypes = appliesToEventTypes;
    }

}
