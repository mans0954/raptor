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
