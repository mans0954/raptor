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

package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

public interface StatisticRegistry {

    /**
     * updates a statistical unit based on the values in the <code>statisticalUnitInformation</code> parameter.
     * 
     * @param statisticalUnitInformation the information used to update the relevant statistic
     */
    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation);

    /**
     * Gets the statistic associated to the <code>statisticName</code> parameter.
     * 
     * @param statisticName - the unit name of the statistic to receive.
     * @return
     */
    public BaseStatistic getStatistic(String statisticName);

    /**
     * Registers a List of statistics with this StatisticsHandler
     * 
     * @param statisticalUnits - the <code>Statistic</code>s to register
     */
    public void setStatisticalUnits(List<BaseStatistic> statisticalUnits);

    /**
     * Returns a <code>List</code> of currently registered statistics
     * 
     * @return a <code>List</code> of currently registered statistics
     */
    public List<BaseStatistic> getStatisticalUnits();

}
