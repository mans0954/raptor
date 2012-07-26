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

import java.util.Arrays;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @author philsmart
 * 
 */
public class DefaultEmailer extends AbstractEmailer {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(DefaultEmailer.class);

    public void sendEmail(final TemplateEmailContext emailContext, final EmailerProperties emailerProperties) {
        final String content = getEmailContent(emailContext.getModel(), emailerProperties.getTemplateLocation());
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(emailContext.getEmailAddress());
                message.setFrom(emailerProperties.getFromEmailAddress());
                message.setSubject(emailerProperties.getTitle());
                message.setText(content, true); // true means enable html email
            }
        };
        log.info("Sending email to [{}]", Arrays.toString(emailContext.getEmailAddress()));
        log.trace("Email content [{}]", content);
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            log.error("Can not send email to [{}],  reason {}", emailContext.getEmailAddress(), e.getMessage());
        }
    }

}
