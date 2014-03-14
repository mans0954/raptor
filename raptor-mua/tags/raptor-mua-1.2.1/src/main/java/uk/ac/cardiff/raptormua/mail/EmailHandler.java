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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author philsmart
 *
 */
public class EmailHandler implements ApplicationContextAware, InitializingBean{
    
    /** class logger */
    private final Logger log = LoggerFactory.getLogger(EmailHandler.class);
    
    private List<EmailerProperties> emailerProperties = new ArrayList<EmailerProperties>();
    
    private Emailer emailer;

    private ApplicationContext applicationContext;
   
    
    public void sendEmail(final TemplateEmailContext emailContext, final String emailerPropertiesIdentifier){
        for (EmailerProperties properties : emailerProperties){
            if (properties.getEmailerPropertiesIdentifier().equals(emailerPropertiesIdentifier)){
                emailer.sendEmail(emailContext, properties);
                return;
            }
        }
        log.error("No emailerProperties could be found for {}, no email sent",emailerPropertiesIdentifier);
    }
    
    public void afterPropertiesSet() throws Exception{
        setEmailerPropertiesFromApplicationContext();
    }
    
    public void setEmailerPropertiesFromApplicationContext(){
        log.info("Email Handler has been asked to register the set of emailer properties form the application context");
        
        Map<String, ?> allEmailerPropertyBeans = applicationContext.getBeansOfType(EmailerProperties.class);
        for (Map.Entry<String, ?> entry : allEmailerPropertyBeans.entrySet()) {
            EmailerProperties properties = (EmailerProperties)entry.getValue();
            log.debug("Registering emailer properties [{}]", properties.getEmailerPropertiesIdentifier());
            emailerProperties.add(properties);
        }
    }


    /**
     * @param emailer the emailer to set
     */
    public void setEmailer(Emailer emailer) {
        this.emailer = emailer;
    }


    /**
     * @return the emailer
     */
    public Emailer getEmailer() {
        return emailer;
    }


    /**
     * @param emailerProperties the emailerProperties to set
     */
    public void setEmailerProperties(List<EmailerProperties> emailerProperties) {
        this.emailerProperties = emailerProperties;
    }


    /**
     * @return the emailerProperties
     */
    public List<EmailerProperties> getEmailerProperties() {
        return emailerProperties;
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        
    }


    

    
    

}
