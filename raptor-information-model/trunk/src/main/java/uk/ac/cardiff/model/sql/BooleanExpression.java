package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * Subset of intended BNF [<boolean value expression>    ::= 
         <boolean term>
     |     <boolean value expression> OR <boolean term>]
     
 * @author philsmart
 *
 */
public class BooleanExpression implements Serializable{
    
    private BooleanTerm booleanTerm;

    public void setBooleanTerm(BooleanTerm booleanTerm) {
	this.booleanTerm = booleanTerm;
    }

    public BooleanTerm getBooleanTerm() {
	return booleanTerm;
    }
    

}
