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
package main.uk.ac.cf.model;

/**
 * This is a poor implementation of an attributeRule as taken from the ShibbolethIDP
 *
 * @author philsmart
 *
 */
public class AttributeRule {

    /* needs to be the exact field name of the attribute */
    //TODO change all these to proper regulated values with OIDs.
    private String attributeID;
    private MatchRule permitValueRule;
    private MatchRule denyValueRule;


    public void setAttributeID(String attributeID) {
	this.attributeID = attributeID;
    }
    public String getAttributeID() {
	return attributeID;
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
