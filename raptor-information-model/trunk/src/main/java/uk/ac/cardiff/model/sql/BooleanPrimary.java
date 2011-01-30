package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [<boolean primary>    ::= 
         <predicate>
     |     <parenthesized boolean value expression>
     |     <nonparenthesized value expression primary>]
     
 * @author philsmart
 *
 */
public class BooleanPrimary implements Serializable{
    
    private Predicate predicate;

    public void setPredicate(Predicate predicate) {
	this.predicate = predicate;
    }

    public Predicate getPredicate() {
	return predicate;
    }

}
