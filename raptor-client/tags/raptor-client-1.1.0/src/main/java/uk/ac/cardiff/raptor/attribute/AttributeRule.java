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

package uk.ac.cardiff.raptor.attribute;

import uk.ac.cardiff.raptor.attribute.filtering.match.MatchRule;

/*
 * This is a poor implementation of an attributeRule as taken from the ShibbolethIDP. But as this will be replaced by Shib, leave for now
 */
public class AttributeRule {

    /**
     * The name of the field in the {@link uk.ac.cardiff.model.event.Event} or a subclass thereof that this rule applies
     * to e.g principalName.
     */
    private String attributeID;

    /** A permit value rule. */
    private MatchRule permitValueRule;

    /** A deny value rule. */
    private MatchRule denyValueRule;

    /**
     * Sets the attribute id.
     * 
     * @param attributeID the new attribute id
     */
    public void setAttributeID(final String attributeID) {
        this.attributeID = attributeID;
    }

    /**
     * Gets the attribute id.
     * 
     * @return the attribute id
     */
    public String getAttributeID() {
        return attributeID;
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

}