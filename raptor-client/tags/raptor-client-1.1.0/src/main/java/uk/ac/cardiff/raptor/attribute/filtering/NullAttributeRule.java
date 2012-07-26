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

package uk.ac.cardiff.raptor.attribute.filtering;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * The Class NullAttributeRule.
 * 
 * @author philsmart
 */
public final class NullAttributeRule extends BaseAttributeRule {

    /** The attribute id. */
    private String attributeId;

    /**
     * 
     * 
     * @see uk.ac.cardiff.raptor.attribute.filtering.BaseAttributeRule#filterAttribute(uk.ac.cardiff.model.event.Event,
     *      uk.ac.cardiff.model.ServiceMetadata)
     */
    public void filterAttribute(final Event event, final ServiceMetadata metadata) {
        final String attributeID = getAttributeId();
        if (classHasAttribute(event, attributeID)) {
            nullAttribute(event, attributeID);
        }
    }

    /**
     * Null the attribute (using reflection) specified by the parameter <code>attributeID</code>.
     * 
     * @param event the event
     * @param attributeID the attribute id
     */
    protected void nullAttribute(final Event event, final String attributeID) {
        ReflectionHelper.nullAttribute(event, attributeID);
    }

    /**
     * Sets the attribute id.
     * 
     * @param attributeId the new attribute id
     */
    public void setAttributeId(final String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Gets the attribute id.
     * 
     * @return the attribute id
     */
    public String getAttributeId() {
        return attributeId;
    }

}
