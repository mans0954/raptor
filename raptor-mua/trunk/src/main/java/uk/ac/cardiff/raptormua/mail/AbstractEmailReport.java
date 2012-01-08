
package uk.ac.cardiff.raptormua.mail;

public abstract class AbstractEmailReport implements EmailReport {

    /**
     * The identifier for the {@link EmailerProperties} this report should use to bind its model to a template and send
     * reports.
     */
    private String emailerPropertiesIdentifier;

    /**
     * The identifier of this report.
     */
    private String emailReportIdentifier;

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

    /**
     * @param emailReportIdentifier the emailReportIdentifier to set
     */
    public void setEmailReportIdentifier(String emailReportIdentifier) {
        this.emailReportIdentifier = emailReportIdentifier;
    }

    /**
     * @return the emailReportIdentifier
     */
    public String getEmailReportIdentifier() {
        return emailReportIdentifier;
    }

}
