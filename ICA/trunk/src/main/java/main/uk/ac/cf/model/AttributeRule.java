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
