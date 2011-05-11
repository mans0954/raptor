package uk.ac.cardiff.model.event.auxiliary;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

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
    public PrincipalInformation(){
        
    }
    
    /**
     * Copy constructor
     * 
     * @param principalInformation
     */
    public PrincipalInformation(PrincipalInformation principalInformation){
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

    /**
     * Reflection based toString method that outputs all the field value pairs of this class
     */
    public String toString() {
        Method[] methods = this.getClass().getMethods();
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass() + "@[");
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                    this.getClass().getMethod(method.getName(), (Class[]) null);
                    Object object = method.invoke(this, (Object[]) null);
                    if (object instanceof Collection) {
                        builder.append(method.getName() + " [" + Arrays.asList(object) + "],");
                    } else {
                        builder.append(method.getName() + " [" + object + "],");
                    }
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public void setPrincipleId(long principleId) {
        this.principleId = principleId;
    }

    public long getPrincipleId() {
        return principleId;
    }

}
