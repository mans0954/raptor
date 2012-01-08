
package uk.ac.cardiff.raptormua.mail;

import java.util.Map;

public interface EmailReport {

    /**
     * Generate a model of the the attributes required to correctly fill an appropriate email template.
     * 
     * @return a model of attribute value pairs. Where the attribute must match to the variable in the appropriate
     *         template email.
     */
    public Map<String, Object> generateModel();

    /**
     * Return the string identifier of the {@link EmailerProperties} this report uses as an email template to bind and
     * send the model.
     * 
     * @return the identifier of the emailer properties.
     */
    public String getEmailerPropertiesIdentifier();

    /**
     * Returns the name identifier of this email report.
     * 
     * @return the identifier of this email report.
     */
    public String getEmailReportIdentifier();
}
