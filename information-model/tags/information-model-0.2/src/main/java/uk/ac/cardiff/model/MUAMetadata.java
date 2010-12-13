package uk.ac.cardiff.model;

import java.io.Serializable;

public class MUAMetadata implements Serializable{

	private String muaName;
	private String organisationName;
	private String contactEmail;
	/**
	 * @param muaName the muaName to set
	 */
	public void setMuaName(String muaName) {
		this.muaName = muaName;
	}
	/**
	 * @return the muaName
	 */
	public String getMuaName() {
		return muaName;
	}
	/**
	 * @param organisationName the organisationName to set
	 */
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	/**
	 * @return the organisationName
	 */
	public String getOrganisationName() {
		return organisationName;
	}
	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

}
