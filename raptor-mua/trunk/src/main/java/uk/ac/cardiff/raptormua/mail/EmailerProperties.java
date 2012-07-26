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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * @author philsmart
 *
 */
public class EmailerProperties {
    
    /** class logger */
    private final Logger log = LoggerFactory.getLogger(EmailerProperties.class);
    
    private String fromEmailAddress;
    
    private String title;
    
    private String emailerPropertiesIdentifier;
    
    /**
     * The location of the template file used to merge and construct the email text.
     */
    private Resource templateLocation;

    /**
     * @param fromEmailAddress the fromEmailAddress to set
     */
    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    /**
     * @return the fromEmailAddress
     */
    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }


    /**
     * @param templateLocation the templateLocation to set
     */
    public void setTemplateLocation(Resource templateLocation) {
        try {
            log.debug("Setting email template location for [{}] to [{}]",emailerPropertiesIdentifier,templateLocation.getFile().getCanonicalFile());
        } catch (IOException e) {
          log.error("Problems setting template location of [{}], {}",emailerPropertiesIdentifier,e.getMessage());
        }
        this.templateLocation = templateLocation;
    }

    /**
     * @return the templateLocation
     */
    public Resource getTemplateLocation() {
        return templateLocation;
    }


    /**
     * @param emailerPropertiesIdentifier the emailerPropertiesIdentifier to set
     */
    public void setEmailerPropertiesIdentifier(String emailerPropertiesIdentifier) {
        this.emailerPropertiesIdentifier = emailerPropertiesIdentifier;
    }

    /**
     * @return the emailerPropertiesIdentifier
     */
    public String getEmailerPropertiesIdentifier() {
        return emailerPropertiesIdentifier;
    }

}
