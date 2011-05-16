package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 *  [<boolean test>    ::=   <boolean primary> [ IS [ NOT ] <truth value> ]
 *
 * @author philsmart
 *
 */
public class BooleanTest implements Serializable{

    /** Generated SerialUID*/
    private static final long serialVersionUID = 8864038948485419010L;

    private BooleanPrimary booleanPrimary;

    public void setBooleanPrimary(BooleanPrimary booleanPrimary) {
	this.booleanPrimary = booleanPrimary;
    }

    public BooleanPrimary getBooleanPrimary() {
	return booleanPrimary;
    }
}
