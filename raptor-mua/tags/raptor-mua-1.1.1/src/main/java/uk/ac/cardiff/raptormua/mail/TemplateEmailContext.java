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
