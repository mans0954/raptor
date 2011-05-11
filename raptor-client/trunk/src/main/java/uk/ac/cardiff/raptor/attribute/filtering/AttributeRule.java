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
import uk.ac.cardiff.raptor.attribute.filtering.match.MatchRule;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * This is a poor implementation of an attributeRule.
 *
 * @author philsmart
 *
 */
public abstract class AttributeRule {

    private MatchRule permitValueRule;
    private MatchRule denyValueRule;

    public abstract void filterAttribute(Event event, ServiceMetadata metadata) throws AttributeFilterException;

    protected void nullAttribute(Event event, String attributeID) {
        ReflectionHelper.nullAttribute(event, attributeID);
    }

    protected boolean classHasAttribute(Event event, String attributeID) {
        return ReflectionHelper.classHasAttribute(event, attributeID);
    }

    protected Object getValueForObject(Event event, String attributeID) {
        Object result = ReflectionHelper.getValueFromObject(attributeID, event);
        return result;
    }

    protected void setValueForObject(Event event, String value, String attributeID) {
         ReflectionHelper.setValueOnObject(attributeID, value, event);
    }

    public void setPermitValueRule(MatchRule permitValueRule) {
        this.permitValueRule = permitValueRule;
    }
    public MatchRule getPermitValueRule() {
        return permitValueRule;
    }
    public void setDenyValueRule(MatchRule denyValueRule) {
        this.denyValueRule = denyValueRule;
    }
    public MatchRule getDenyValueRule() {
        return denyValueRule;
    }


}
