package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [  <comparison predicate>    ::=   <row value expression> <comp op> <row value expression>]
 * 
 * @author philsmart
 *
 */
public class ComparisonPredicate implements Serializable{
    public enum CompOp {EQUAL,NOTEQUAL}
    private CompOp compOp;
    private String fieldName;
    private String value;
    
    public void setCompOp(CompOp compOp) {
	this.compOp = compOp;
    }
    public CompOp getCompOp() {
	return compOp;
    }
    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }
    public String getFieldName() {
	return fieldName;
    }
    public void setValue(String value) {
	this.value = value;
    }
    public String getValue() {
	return value;
    }
}
