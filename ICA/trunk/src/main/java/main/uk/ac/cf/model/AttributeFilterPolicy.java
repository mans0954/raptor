/**
 *
 */
package main.uk.ac.cf.model;

import java.util.List;

/**
 * @author philsmart
 *
 */
public class AttributeFilterPolicy {

    private String policyName;

    private List<AttributeRule> attributeRules;

    public void setAttributeRules(List<AttributeRule> attributeRules) {
	this.attributeRules = attributeRules;
    }

    public List<AttributeRule> getAttributeRules() {
	return attributeRules;
    }

    public void setPolicyName(String policyName) {
	this.policyName = policyName;
    }

    public String getPolicyName() {
	return policyName;
    }



}
