package uk.ac.cardiff.model.event.auxiliary;

import uk.ac.cardiff.utility.StringUtils;

public class PrincipalInformation {

    /** For Hibernate table id */
    private long principleId;

    /** The name of the school or department this principal is a member of */
    private String school;

    /** The affiliation this principal has with their current school or department */
    private String affiliation;

    /**
     * Default constructor
     * 
     */
    public PrincipalInformation() {

    }

    /**
     * Copy constructor
     * 
     * @param principalInformation
     */
    public PrincipalInformation(PrincipalInformation principalInformation) {
        this.school = principalInformation.getSchool();
        this.affiliation = principalInformation.getAffiliation();
    }

    /**
     * @param school
     *            the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param affiliation
     *            the affiliation to set
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * @return the affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    public String toString() {
        return StringUtils.buildToString(this);
    }

    public void setPrincipleId(long principleId) {
        this.principleId = principleId;
    }

    public long getPrincipleId() {
        return principleId;
    }

}
