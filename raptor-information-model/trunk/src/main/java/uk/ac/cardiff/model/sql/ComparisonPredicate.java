package uk.ac.cardiff.model.sql;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [  <comparison predicate>    ::=   <row value expression> <comp op> <row value expression>]
 *
 * @author philsmart
 *
 */
public class ComparisonPredicate implements Serializable{
    /**
     * Generated SerialUID
     */
    private static final long serialVersionUID = 4721780920444284628L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(ComparisonPredicate.class);

    public enum CompOp {EQUAL,NOT_EQUAL}

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
        log.debug("Setting fieldName {}",fieldName);
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

    public CompOp[] getPossibleComparisonOperatorValues() {
	return CompOp.values();
    }

    /**
     * Does not set anything, only used to maintain compatibility with XML bindings
     * @param compValues
     */
    public void setPossibleComparisonOperatorValues(CompOp[] compValues){

    }

}
