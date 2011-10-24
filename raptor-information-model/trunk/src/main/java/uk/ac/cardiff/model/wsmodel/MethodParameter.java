/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class MethodParameter.
 * 
 * @author philsmart
 */
public class MethodParameter implements Serializable {

    /** Generated SerialUID. */
    private static final long serialVersionUID = -290473614440091625L;

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(MethodParameter.class);

    /**
     * enum, which holds information about the parameter FIELD - name of a field in the model VALUE - a primitive data type value.
     */
    public enum ParameterType {
        /** The FIELD. */
        FIELD,
        /** The VALUE. */
        VALUE
    };

    /** The parameter type. */
    private ParameterType parameterType;

    /** friendly name of the parameter, for the benefit of the view. */
    private String parameterName;

    /** value of the parameter. */
    private String value;

    /**
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the parameter name.
     * 
     * @param parameterName
     *            the new parameter name
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * Gets the parameter name.
     * 
     * @return the parameter name
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * Sets the parameter type.
     * 
     * @param parameterType
     *            the new parameter type
     */
    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * Gets the parameter type.
     * 
     * @return the parameter type
     */
    public ParameterType getParameterType() {
        return parameterType;
    }

}
