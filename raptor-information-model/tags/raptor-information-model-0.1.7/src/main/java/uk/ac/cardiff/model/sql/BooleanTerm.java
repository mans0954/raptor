package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * Subset of intended BNF [ <boolean term>    ::=
         <boolean factor>
     |     <boolean term> AND <boolean factor>]
 *
 * @author philsmart
 *
 */
public class BooleanTerm implements Serializable{

    /** Generated SerialUID*/
    private static final long serialVersionUID = -9113662911977384592L;

    private BooleanFactor booleanFactor;

    public void setBooleanFactor(BooleanFactor booleanFactor) {
	this.booleanFactor = booleanFactor;
    }

    public BooleanFactor getBooleanFactor() {
	return booleanFactor;
    }

}
