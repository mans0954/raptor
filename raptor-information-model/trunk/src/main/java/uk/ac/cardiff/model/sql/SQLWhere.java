package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [  <search condition>    ::=   <boolean value expression>]
 * @author philsmart
 *
 */
public class SQLWhere extends SQLFilter implements Serializable{
    
    private BooleanExpression booleanExpression;

    public void setBooleanExpression(BooleanExpression booleanExpression) {
	this.booleanExpression = booleanExpression;
    }

    public BooleanExpression getBooleanExpression() {
	return booleanExpression;
    }

}
