/**
 * 
 */
package uk.ac.cardiff.raptormua.mail;

import java.io.Serializable;
import java.util.Map;

import org.springframework.core.io.Resource;

/**
 * @author philsmart
 *
 */
public class TemplateEmailContext extends BaseEmailContext implements Serializable {
    
    /**
     * Generated SerialUID
     */
    private static final long serialVersionUID = 4316551261783137479L;

    
    @SuppressWarnings("rawtypes")
    private Map model;


    /**
     * @param model the model to set
     */
    public void setModel(Map model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public Map getModel() {
        return model;
    }

    

}
