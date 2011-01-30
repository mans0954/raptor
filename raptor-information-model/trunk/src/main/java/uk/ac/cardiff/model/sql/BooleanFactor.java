package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [<boolean factor>    ::=   [ NOT ] <boolean test>]
 * @author philsmart
 *
 */
public class BooleanFactor implements Serializable{
    
    private boolean not;
    private BooleanTest booleanTest;
    
    public void setNot(boolean not) {
	this.not = not;
    }
    public boolean isNot() {
	return not;
    }
    public void setBooleanTest(BooleanTest booleanTest) {
	this.booleanTest = booleanTest;
    }
    public BooleanTest getBooleanTest() {
	return booleanTest;
    }
    
    
}
