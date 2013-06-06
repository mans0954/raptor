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

/**
 * 
 * Holds the information necessary to construct and configure a statistical function dynamically.
 */
public class DynamicStatisticalUnitInformation implements Serializable {

    /**
     * Generated serial UID.
     */
    private static final long serialVersionUID = 2008517678715688148L;

    private StatisticFunctionType function;

    private StatisticalUnitInformation statisticUnitInformation;

    /**
     * @return Returns the function.
     */
    public StatisticFunctionType getFunction() {
        return function;
    }

    /**
     * @param function
     *            The function to set.
     */
    public void setFunction(StatisticFunctionType function) {
        this.function = function;
    }

    /**
     * @return Returns the statisticUnitInformation.
     */
    public StatisticalUnitInformation getStatisticUnitInformation() {
        return statisticUnitInformation;
    }

    /**
     * @param statisticUnitInformation
     *            The statisticUnitInformation to set.
     */
    public void setStatisticUnitInformation(StatisticalUnitInformation statisticUnitInformation) {
        this.statisticUnitInformation = statisticUnitInformation;
    }

}
