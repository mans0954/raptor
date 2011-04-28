/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;
import uk.ac.cardiff.raptor.event.expansion.connector.DataConnector;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * @author philsmart
 *
 */
public class AttributeAssociationDefinition {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(AttributeAssociationDefinition.class);

    /** Human readable name for this definition*/
    private String definiationName;

    /** The name of the field the subject's principal should be extracted from*/
    private String subjectPrincipalField;

    private List<AttributeLookup> lookupAttributes;

    /** Whether to apply the attribute association*/
    private boolean enabled;

    /** The class type that these attribute definitions are attached to*/
    private Class<?> classToAdd;

    /** The class type that this attribute association is applicable for */
    private String associateWithClass;

    /** The data connector used to acquire the attributes*/
    private DataConnector dataConnector;

    /** The ldap search filter template*/
    private String searchFilterTemplate;

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

    public void initialise(){
        dataConnector.initialise();

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


    private void populate(Map<String,String> attributes, Event event) throws InstantiationException, IllegalAccessException{

        Object classToPopulate = getClassToAdd().newInstance();

        for (Map.Entry<String,String> entry : attributes.entrySet()){
            String attributeSourceName = entry.getKey();
            String attributeValue = entry.getValue();
            log.trace("source [{}], value [{}]",attributeSourceName,attributeValue);
            try{
                String internalFieldName = getInternalAttributeName(attributeSourceName);
                ReflectionHelper.setValueOnObject(internalFieldName, attributeValue, classToPopulate);
            }
            catch(AttributeAssociationException e){
                log.warn("Error trying to populate internal model. {}",e.getMessage());
            }
        }

        //now attach the object where appropriate on the current <code>Event</code> object
        ReflectionHelper.attachObjectTo(classToPopulate,event);

       // log.debug("{}",event);
    }

    /**
     * @param event
     */
    public boolean associate(Event event) {
       // log.debug("{},v {}",event.getClass().getCanonicalName(),associateWithClass.getClass().getCanonicalName());
        if (!event.getClass().getCanonicalName().equals(associateWithClass)){
            return false;
        }

        Object principalObject = ReflectionHelper.getValueFromObject(getSubjectPrincipalField(), event);
        String principal = null;
        if (principalObject instanceof String){
            principal = (String)principalObject;
        }
        if (principal!=null){
            try {
                 dataConnector.setReturnAttributes(getSourceAttributesAsArray());
                 dataConnector.setSearchFilterTemplate(searchFilterTemplate);
                 Map<String, String> attributes = dataConnector.lookup(principal);
                 populate(attributes,event);

                 return true;
            } catch (AttributeAssociationException e) {
                log.error("Association error for principal [{}]",principal,e);
            } catch (InstantiationException e) {
                log.warn("Could not populate event [{}], {}",event,e.getMessage());
            } catch (IllegalAccessException e) {
                log.warn("Could not populate event [{}], {}",event,e.getMessage());
            }
        }
        return false;

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
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }



    /**
     * @param dataConnector the dataConnector to set
     */
    public void setDataConnector(DataConnector dataConnector) {
        this.dataConnector = dataConnector;
    }



    /**
     * @return the dataConnector
     */
    public DataConnector getDataConnector() {
        return dataConnector;
    }



    /**
     * @param searchFilterTemplate the searchFilterTemplate to set
     */
    public void setSearchFilterTemplate(String searchFilterTemplate) {
        this.searchFilterTemplate = searchFilterTemplate;
    }



    /**
     * @return the searchFilterTemplate
     */
    public String getSearchFilterTemplate() {
        return searchFilterTemplate;
    }



    /**
     * @param definiationName the definiationName to set
     */
    public void setDefiniationName(String definiationName) {
        this.definiationName = definiationName;
    }



    /**
     * @return the definiationName
     */
    public String getDefiniationName() {
        return definiationName;
    }

    /**
     * @param classToAdd the classToAdd to set
     */
    public void setClassToAdd(Class<?> classToAdd) {
        this.classToAdd = classToAdd;
    }

    /**
     * @return the classToAdd
     */
    public Class<?> getClassToAdd() {
        return classToAdd;
    }

    /**
     * @param associateWithClass the associateWithClass to set
     */
    public void setAssociateWithClass(String associateWithClass) {
        this.associateWithClass = associateWithClass;
    }

    /**
     * @return the associateWithClass
     */
    public String getAssociateWithClass() {
        return associateWithClass;
    }



}
