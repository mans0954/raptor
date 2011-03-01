package uk.ac.cardiff.model.sql;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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

    static Logger log = LoggerFactory.getLogger(ComparisonPredicate.class);

    public enum CompOp {EQUAL,NOT_EQUAL}

    private CompOp compOp;
    private String fieldName;
    private String value;

    private List<String> possibleFieldNameValues;

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
    public void setPossibleFieldNameValues(List<String> possibleFieldNameValues) {
	this.possibleFieldNameValues = possibleFieldNameValues;
    }
    public List<String> getPossibleFieldNameValues() {
	return possibleFieldNameValues;
    }
    public CompOp[] getPossibleComparisonOperatorValues() {
	return CompOp.values();
    }

}
