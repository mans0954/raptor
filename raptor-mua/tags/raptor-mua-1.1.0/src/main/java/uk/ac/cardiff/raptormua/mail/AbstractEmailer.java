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
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 
 */
public abstract class AbstractEmailer implements Emailer {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(AbstractEmailer.class);

    /**
     * The Spring mail sender interface used to send mail.
     */
    protected JavaMailSender mailSender;

    /**
     * The engine used to turn template emails to complete deliverable emails.
     */
    private VelocityEngine velocityEngine;

    protected String getEmailContent(Map model, Resource templateLocation) {
        if (velocityEngine == null) {
            log.warn("Template engine not set, returning blank email content");
            return "";
        }
        if (templateLocation == null || templateLocation.exists() == false) {
            log.warn("No template location defined, returning blank email content");
            return "";
        }
        try {
            String text =
                    VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation.getFile()
                            .getCanonicalPath(), model);

            return text;
        } catch (VelocityException e) {
            log.warn("Could not construct email content", e);
            return "";
        } catch (IOException e) {
            log.warn("Could not construct email content", e);
            return "";
        }

    }

    /**
     * @param velocityEngine the velocityEngine to set
     */
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * @return the velocityEngine
     */
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @return the mailSender
     */
    public JavaMailSender getMailSender() {
        return mailSender;
    }

}
