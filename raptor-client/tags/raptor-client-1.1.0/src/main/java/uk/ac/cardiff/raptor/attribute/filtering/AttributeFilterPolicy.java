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

import java.util.List;

/**
 * The Class AttributeFilterPolicy.
 */
public class AttributeFilterPolicy {

    /** The policy name. */
    private String policyName;

    /** A <code>List</code> of attribute rules. */
    private List<BaseAttributeRule> attributeRules;

    /**
     * Sets the attribute rules.
     * 
     * @param attributeRules the new attribute rules
     */
    public void setAttributeRules(final List<BaseAttributeRule> attributeRules) {
        this.attributeRules = attributeRules;
    }

    /**
     * Gets the attribute rules.
     * 
     * @return the attribute rules
     */
    public List<BaseAttributeRule> getAttributeRules() {
        return attributeRules;
    }

    /**
     * Sets the policy name.
     * 
     * @param policyName the new policy name
     */
    public void setPolicyName(final String policyName) {
        this.policyName = policyName;
    }

    /**
     * Gets the policy name.
     * 
     * @return the policy name
     */
    public String getPolicyName() {
        return policyName;
    }

}
