/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;

import java.util.List;

import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;

/**
 * @author philsmart
 *
 */
public class AttributeAssociationDefinition {

    /** The name of the field the subject's principal should be extracted from*/
    private String subjectPrincipalField;

    private List<AttributeLookup> lookupAttributes;

    /** The class type that these attribute definitions are attached to*/
    private Class internalModelClass;


    /**
     * @return
     */
    public String[] getSourceAttributesAsArray() {
        String[] sourceAttributes = new String[lookupAttributes.size()];
        int i=0;
        for (AttributeLookup lookupAttribute : lookupAttributes){
            sourceAttributes[i++] = lookupAttribute.getSourceAttributeName();
        }
        return sourceAttributes;
    }



    /**
     * @param attributeSourceName
     * @return
     */
    public String getInternalAttributeName(String attributeSourceName) throws AttributeAssociationException{
        for (AttributeLookup lookupAttribute : lookupAttributes){
               if (lookupAttribute.getSourceAttributeName().equals(attributeSourceName))
                   return lookupAttribute.getInternalAttributeName();
        }
        throw new AttributeAssociationException("Source attribute "+attributeSourceName+" was not found in the attribute association mappings");
    }


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

    /**
     * @param lookupAttributes the lookupAttributes to set
     */
    public void setLookupAttributes(List<AttributeLookup> lookupAttributes) {
        this.lookupAttributes = lookupAttributes;
    }

    /**
     * @return the lookupAttributes
     */
    public List<AttributeLookup> getLookupAttributes() {
        return lookupAttributes;
    }



    /**
     * @param internalModelClass the internalModelClass to set
     */
    public void setInternalModelClass(Class internalModelClass) {
        this.internalModelClass = internalModelClass;
    }



    /**
     * @return the internalModelClass
     */
    public Class getInternalModelClass() {
        return internalModelClass;
    }





}
