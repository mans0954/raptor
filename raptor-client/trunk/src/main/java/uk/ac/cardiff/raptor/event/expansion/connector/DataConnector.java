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

package uk.ac.cardiff.raptor.event.expansion.connector;

import java.util.Map;

/**
 * @author philsmart
 * 
 */
public interface DataConnector {

    /**
     * Use this data connector to resolve attributes about the <code>principal</code>
     * 
     * @param principal the principal name of the subject to resolve attributes about
     * @return a <code>Map</code> of attribute value pairs resolved about the <code>principal</code>
     * @throws AttributeAssociationException
     */
    public Map<String, String> lookup(final String principal) throws AttributeAssociationException;

    /**
     * Initiliases the concrete implementation of this data connector
     */
    public void initialise();

    /**
     * Sets which attributes should be returned by this data connector
     * 
     * @param attributes the set of attributes which should be returned by this data connector
     */
    public void setReturnAttributes(final String[] attributes);

    /**
     * Sets the search template specific to the concrete implementation of the data connector used to query this data
     * connector.
     * 
     * @param searchTemplate the search template specific to the concrete implementation of the data connector used to
     *            query this data connector.
     */
    public void setSearchTemplate(final String searchTemplate);

}
