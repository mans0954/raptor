/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
