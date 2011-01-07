/**
 *
 */
package uk.ac.cardiff.model;

/**
 * @author philsmart
 *
 */
public class UAMetadata {

    private String uaName;
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
    public void setUaName(String uaName) {
	this.uaName = uaName;
    }
    public String getUaName() {
	return uaName;
    }

}
