/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author philsmart
 *
 */
public class MethodParameter implements Serializable{
    static Logger log = LoggerFactory.getLogger(MethodParameter.class);

    /* enum, which holds information about the parameter
     * FIELD - name of a field in the model
     * VALUE - a primitive data type value*/
    public enum ParameterType{FIELD, VALUE};
    
    private ParameterType parameterType;
    
    /* friendly name of the parameter, for the benefit of the view*/
    private String parameterName;

    /* value of the parameter */
    private String value;
    


    public void setValue(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

    public void setParameterName(String parameterName) {
	this.parameterName = parameterName;
    }

    public String getParameterName() {
	return parameterName;
    }

    public void setParameterType(ParameterType parameterType) {
	this.parameterType = parameterType;
    }

    public ParameterType getParameterType() {
	return parameterType;
    }




 
}
