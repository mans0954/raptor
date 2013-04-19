
package uk.ac.cardiff.raptorweb.engine.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.RequestContextHolder;

/**
 * @author philsmart
 * 
 */
public class MessageGenerator {

    /** class logger */
    private static final Logger log = LoggerFactory.getLogger(MessageGenerator.class);

    public static void addInfo(String message) {
        log.debug("Adding info message {}", message);
        MessageContext context = RequestContextHolder.getRequestContext().getMessageContext();
        context.addMessage(new MessageBuilder().info().defaultText(message).build());
    }

    public static void addError(String message) {
        log.debug("Adding error message {}", message);
        MessageContext context = RequestContextHolder.getRequestContext().getMessageContext();
        context.addMessage(new MessageBuilder().error().defaultText(message).build());
    }

}
