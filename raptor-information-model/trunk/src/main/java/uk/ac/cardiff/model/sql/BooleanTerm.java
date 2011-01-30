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
    
    private BooleanFactor booleanFactor;

    public void setBooleanFactor(BooleanFactor booleanFactor) {
	this.booleanFactor = booleanFactor;
    }

    public BooleanFactor getBooleanFactor() {
	return booleanFactor;
    }

}
