/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.cardiff.model.sql;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [ <comparison predicate> ::= <row value expression> <comp op> <row value expression>]
 * 
 * @author philsmart
 * 
 */
public class ComparisonPredicate implements Serializable {
    /**
     * Generated SerialUID
     */
    private static final long serialVersionUID = 4721780920444284628L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(ComparisonPredicate.class);

    public enum CompOp {
        EQUAL, NOT_EQUAL
    }

    private CompOp compOp;
    private String fieldName;
    private String value;

    /**
     * Constructor.
     * 
     * @param comparisonPredicate
     */
    public ComparisonPredicate() {
        super();
    }

    /**
     * Copy Constructor.
     * 
     * @param comparisonPredicate
     */
    public ComparisonPredicate(ComparisonPredicate comparisonPredicate) {
        super();
        if (comparisonPredicate != null) {
            fieldName = comparisonPredicate.fieldName;
            value = comparisonPredicate.value;
            if (comparisonPredicate.compOp != null) {
                compOp = CompOp.valueOf(comparisonPredicate.compOp.name());
            }
        }

    }

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

    public CompOp[] getPossibleComparisonOperatorValues() {
        return CompOp.values();
    }

    /**
     * Does not set anything, only used to maintain compatibility with XML bindings
     * 
     * @param compValues
     */
    public void setPossibleComparisonOperatorValues(CompOp[] compValues) {

    }

}
