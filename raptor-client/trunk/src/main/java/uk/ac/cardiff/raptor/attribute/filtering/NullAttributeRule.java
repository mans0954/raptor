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
package uk.ac.cardiff.raptor.attribute.filtering;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 *
 */
public class NullAttributeRule extends AttributeRule{

    /* needs to be the exact field name of the attribute */
    private String attributeId;


    public void filterAttribute(Event event, ServiceMetadata metadata) {
        String attributeID = getAttributeId();
        if (classHasAttribute(event, attributeID)) {
               nullAttribute(event, attributeID);
        }
    }


    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }
    public String getAttributeId() {
        return attributeId;
    }


}
