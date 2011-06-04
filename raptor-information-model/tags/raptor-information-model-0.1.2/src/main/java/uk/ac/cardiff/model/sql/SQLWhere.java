package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [  <search condition>    ::=   <boolean value expression>]
 * @author philsmart
 *
 */
public class SQLWhere extends SQLFilter implements Serializable{

    /** Generated SerialUID*/
    private static final long serialVersionUID = -7381009317987902036L;

    private BooleanExpression booleanExpression;

    public void setBooleanExpression(BooleanExpression booleanExpression) {
	this.booleanExpression = booleanExpression;
    }

    public BooleanExpression getBooleanExpression() {
	return booleanExpression;
    }

}
