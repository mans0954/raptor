package uk.ac.cardiff.model.wsmodel;

public class EventType {

    /**
     * A Shibboleth Authentication Event Type SHIBBOLETH_AUTHENTICATION("ShibbolethIdpAuthenticationEvent", new String[] {
     * "uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent", "uk.ac.cardiff.model.event.AuthenticationEvent", "uk.ac.cardiff.model.event.Event" }),
     * 
     * EZPROXY_AUTHENTICATION("EzproxyAuthenticationEvent", new String[] { "uk.ac.cardiff.model.event.EzproxyAuthenticationEvent",
     * "uk.ac.cardiff.model.event.AuthenticationEvent", "uk.ac.cardiff.model.event.Event" });
     */

    /** String identifier for this event type. */
    private String id;

    private String hibernateSimpleClassName;

    private String[] classHierarchy;

    public String getHibernateSimpleClassName() {
        return hibernateSimpleClassName;
    }

    public String[] getClassHierarchy() {
        return classHierarchy;
    }

    /**
     * @param classHierarchy
     *            the classHierarchy to set
     */
    public void setClassHierarchy(String[] classHierarchy) {
        this.classHierarchy = classHierarchy;
    }

    /**
     * @param hibernateSimpleClassName
     *            the hibernateSimpleClassName to set
     */
    public void setHibernateSimpleClassName(String hibernateSimpleClassName) {
        this.hibernateSimpleClassName = hibernateSimpleClassName;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

}
