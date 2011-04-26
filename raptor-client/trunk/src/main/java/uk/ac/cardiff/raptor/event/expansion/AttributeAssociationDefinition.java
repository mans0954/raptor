/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;

/**
 * @author philsmart
 *
 */
public class AttributeAssociationDefinition {

    /** The name of the field the subject's principal should be extracted from*/
    private String subjectPrincipalField;

    /**
     * @param subjectPrincipalField the subjectPrincipalField to set
     */
    public void setSubjectPrincipalField(String subjectPrincipalField) {
        this.subjectPrincipalField = subjectPrincipalField;
    }

    /**
     * @return the subjectPrincipalField
     */
    public String getSubjectPrincipalField() {
        return subjectPrincipalField;
    }


}
