/**
 *
 */
package uk.ac.cardiff.model;

/**
 * @author philsmart
 *
 */
public class WebMetadata {

    private String webName;
    private String organisationName;
    private String contactEmail;



    public void setOrganisationName(String organisationName) {
	this.organisationName = organisationName;
    }
    public String getOrganisationName() {
	return organisationName;
    }
    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }
    public String getContactEmail() {
	return contactEmail;
    }
    public void setWebName(String webName) {
	this.webName = webName;
    }
    public String getWebName() {
	return webName;
    }


}
