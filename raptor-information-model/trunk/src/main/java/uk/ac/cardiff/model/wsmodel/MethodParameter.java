/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author philsmart
 *
 */
public class MethodParameter implements Serializable{
    static Logger log = LoggerFactory.getLogger(MethodParameter.class);

    /* possible enum, which holds information about the parameter*/
    private String parameterType;

    private String parameter;

    public void setParameter(String parameter) {
	log.debug("Setting parameter: "+parameter);
	this.parameter = parameter;
    }

    public String getParameter() {
	return parameter;
    }

}
