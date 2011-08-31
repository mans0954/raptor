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
import uk.ac.cardiff.raptor.attribute.filtering.match.MatchRule;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * A naive implementation of an attributeRule.
 * 
 * 
 */
public abstract class BaseAttributeRule {

    /** The permit value rule. */
    private MatchRule permitValueRule;

    /** The deny value rule. */
    private MatchRule denyValueRule;

    /** The <code>Class</code> type that this attribute rule applies to. */
    private Class<?> eventType;

    /**
     * Abstract method that Filters an attribute. This *must* be implemented by all concrete classes.
     * 
     * @param event the event
     * @param metadata the metadata
     * @throws AttributeFilterException the attribute filter exception
     */
    public abstract void filterAttribute(Event event, ServiceMetadata metadata) throws AttributeFilterException;

    /**
     * Whether the attribute rule should be applied to the class <code>eventType</code>.
     * 
     * @param eventType the event type
     * @return true if this attribute rule is enabled, and should be applied to the input class type.
     */
    public final boolean shouldApply(final Class<?> eventType) {
        final boolean enabled = getDenyValueRule().isEnabled();
        final boolean classEquivelent = (this.eventType == eventType);
        if (enabled && classEquivelent) {
            return true;
        }
        return false;

    }

    /**
     * Whether the <code>Event</code> has the attribute <code>attributeID</code>
     * 
     * @param event the event
     * @param attributeID the attribute id
     * @return true, if successful
     */
    protected final boolean classHasAttribute(final Event event, final String attributeID) {
        return ReflectionHelper.classHasAttribute(event, attributeID);
    }

    /**
     * Gets the value of the attribute <code>attributeID</code> for the class <code>event</code>.
     * 
     * @param event the event
     * @param attributeID the attribute id
     * @return the value for object
     */
    protected final Object getValueForObject(final Event event, final String attributeID) {
        final Object result = ReflectionHelper.getValueFromObject(attributeID, event);
        return result;
    }

    /**
     * Sets the value of the attribute <code>attributeID</code> for the class <code>event</code>.
     * 
     * @param event the event to set the <code>value</code> on
     * @param value the value to set on the <code>event</code>
     * @param attributeID the field name of the field to set the <code>value</code> on
     */
    protected final void setValueForObject(final Event event, final String value, final String attributeID) {
        ReflectionHelper.setValueOnObject(attributeID, value, event);
    }

    /**
     * Sets the permit value rule.
     * 
     * @param permitValueRule the new permit value rule
     */
    public void setPermitValueRule(final MatchRule permitValueRule) {
        this.permitValueRule = permitValueRule;
    }

    /**
     * Gets the permit value rule.
     * 
     * @return the permit value rule
     */
    public MatchRule getPermitValueRule() {
        return permitValueRule;
    }

    /**
     * Sets the deny value rule.
     * 
     * @param denyValueRule the new deny value rule
     */
    public void setDenyValueRule(final MatchRule denyValueRule) {
        this.denyValueRule = denyValueRule;
    }

    /**
     * Gets the deny value rule.
     * 
     * @return the deny value rule
     */
    public MatchRule getDenyValueRule() {
        return denyValueRule;
    }

    /**
     * Sets the event type.
     * 
     * @param eventType the eventType to set
     */
    public void setEventType(final Class<?> eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the event type.
     * 
     * @return the eventType
     */
    public Class<?> getEventType() {
        return eventType;
    }

}
