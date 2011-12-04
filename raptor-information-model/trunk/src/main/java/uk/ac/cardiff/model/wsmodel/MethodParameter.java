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

    /** friendly name of the parameter. */
    private String parameterName;

    /** Generic value of the parameter. */
    private Object value;

    /**
     * The class type of the value object, used by the view component to understand the value to set. Stored as a string for remoting.
     */
    private String valueType;

    /**
     * Sets the value.
     * 
     * @param value
     *            the new value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets the value assuming the type, throws an error if the value does not equal the type.
     * 
     * @param requiredType
     *            the class that needs to match with <code>value</code>.
     */
    public <T> T getValue(Class<T> requiredType) throws MethodParameterNotOfRequiredTypeException {
        if (requiredType.isAssignableFrom(value.getClass())) {
            return (T) value;
        } else {
            throw new MethodParameterNotOfRequiredTypeException(parameterName, requiredType, value.getClass());
        }

    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public Object getValue() {
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

    /**
     * @param valueType
     *            the valueType to set
     */
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * @return the valueType
     */
    public String getValueType() {
        return valueType;
    }

}
