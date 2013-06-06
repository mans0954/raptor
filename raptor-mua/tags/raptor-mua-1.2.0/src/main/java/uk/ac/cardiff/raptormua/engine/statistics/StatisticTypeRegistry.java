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

import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;

/**
 * Handles the *types* of available statistical functions - as configured in XML. This captures the types of statistics
 * that can be performed, as opposed to the {@link StatisticHandler} which stores concrete statistical instances of
 * those types configured in XML. This handler can therefore be used to invoke custom functions at runtime e.g. through
 * the SOAP endpoint. This is important as it allows the web interface to construct and then send new instances of
 * statistical units to the MUA, as opposed to having to call pre-configured ones.
 */
public interface StatisticTypeRegistry {

    /**
     * gets the list of {@link StatisticFunctionType}s registered.
     * 
     * @return
     */
    public List<StatisticFunctionType> getStatisticTypes();

}
