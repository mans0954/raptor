/**
 * 
 */

package uk.ac.cardiff.raptormua.mail;

/**
 * @author philsmart
 * 
 */
public interface Emailer {

    public void sendEmail(final TemplateEmailContext emailContext, final EmailerProperties emailerProperties);

}
