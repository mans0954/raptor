/**
 *
 */
package uk.ac.cardiff.model;

/**
 * @author philsmart
 *
 */
public class ICAMetadata {

    private String icaName;
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
    public void setIcaName(String icaName) {
	this.icaName = icaName;
    }
    public String getIcaName() {
	return icaName;
    }

}
