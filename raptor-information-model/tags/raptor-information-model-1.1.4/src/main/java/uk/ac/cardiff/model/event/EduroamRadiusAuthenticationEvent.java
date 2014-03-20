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
package uk.ac.cardiff.model.event;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;
import uk.ac.cardiff.utility.StringUtils;

public class EduroamRadiusAuthenticationEvent extends AuthenticationEvent {


    private String clientIdentifier;

    private String eapType;

    private String serviceClass;

    private String serverSoftware;
    
    private String serverSoftwareVersion;

    private String result;

    private String resultInfo;

    private String userIdCui;

    private String realm;

    private String serviceClientIdentifier;
    
    private String rpOperatorName;

    /**
     * Copy constructor.
     * 
     * @param event
     *            the {@link EduroamRadiusAuthenticationEvent} to copy.
     */
    protected EduroamRadiusAuthenticationEvent(EduroamRadiusAuthenticationEvent event) {
        super(event);
        this.clientIdentifier = event.clientIdentifier;
    }

    /**
     * Instantiates a new shibboleth idp authentication event.
     */
    public EduroamRadiusAuthenticationEvent() {
        super();
    }

    /**
     * New instance.
     * 
     * @return the {@link EduroamRadiusAuthenticationEvent} authentication event
     */
    public static EduroamRadiusAuthenticationEvent newInstance() {
        return new EduroamRadiusAuthenticationEvent();
    }

    /**
     * Copy method. Alternative to clone. Returns a copied version of this event.
     * 
     * @return the shibboleth idp authentication event
     */
    @Override
    public EduroamRadiusAuthenticationEvent copy() {
        return new EduroamRadiusAuthenticationEvent(this);
    }

    /**
     * create a unique hash, with as uniform a distribution as possible.
     * 
     * @return the int
     */
    @Override
    public int hashCode() {
        int hash = HashCodeUtil.SEED;

        hash = HashCodeUtil.hash(hash, getEventTimeMillis());
        hash = HashCodeUtil.hash(hash, getAuthenticationType());
        hash = HashCodeUtil.hash(hash, getEventId());
        hash = HashCodeUtil.hash(hash, getServiceHost());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        hash = HashCodeUtil.hash(hash, getClientIdentifier());
        hash = HashCodeUtil.hash(hash, getEapType());
        hash = HashCodeUtil.hash(hash, getServiceClass());
        hash = HashCodeUtil.hash(hash, getServerSoftware());
        hash = HashCodeUtil.hash(hash, getResult());
        hash = HashCodeUtil.hash(hash, getResultInfo());
        hash = HashCodeUtil.hash(hash, getUserIdCui());
        hash = HashCodeUtil.hash(hash, getServiceClientIdentifier());
        hash = HashCodeUtil.hash(hash, getRealm());
        hash = HashCodeUtil.hash(hash, getServerSoftwareVersion());
        hash = HashCodeUtil.hash(hash, getRpOperatorName());
        return hash;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * For hibernate, so the hashcode can be persisted.
     * 
     * @return the hash code
     */
    public int getHashCode() {
        return hashCode();
    }

    /**
     * For hibernate, does nothing as the hascode is computed on the fly from the <code>hashCode</code> method.
     * 
     * @param hashCode
     *            the new hash code
     */
    public void setHashCode(int hashCode) {

    }
    /**
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        EduroamRadiusAuthenticationEvent that = (EduroamRadiusAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) && EqualsUtil.areEqual(this.getClientIdentifier(), that.getClientIdentifier())
                && EqualsUtil.areEqual(this.getEapType(), that.getEapType()) && EqualsUtil.areEqual(this.getServiceClass(), that.getServiceClass())
                && EqualsUtil.areEqual(this.getServerSoftware(), that.getServerSoftware()) && EqualsUtil.areEqual(this.getResult(), that.getResult())
                && EqualsUtil.areEqual(this.getResultInfo(), that.getResultInfo()) && EqualsUtil.areEqual(this.getRealm(), that.getRealm())
                && EqualsUtil.areEqual(this.getServiceClientIdentifier(), that.getServiceClientIdentifier()) && EqualsUtil.areEqual(this.getUserIdCui(), that.getUserIdCui())
                && EqualsUtil.areEqual(this.getServerSoftwareVersion(), that.getServerSoftwareVersion()) && EqualsUtil.areEqual(this.getRpOperatorName(), that.getRpOperatorName())
                ;

        return areEqual;
    }

    /**
     * @param clientIdentifier
     *            the clientIdentifier to set
     */
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    /**
     * @return the clientIdentifier
     */
    public String getClientIdentifier() {
        return clientIdentifier;
    }

    /**
     * @return Returns the eapType.
     */
    public String getEapType() {
        return eapType;
    }

    /**
     * @param eapType
     *            The eapType to set.
     */
    public void setEapType(String eapType) {
        this.eapType = eapType;
    }

    /**
     * @return Returns the serviceClass.
     */
    public String getServiceClass() {
        return serviceClass;
    }

    /**
     * @param serviceClass
     *            The serviceClass to set.
     */
    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    /**
     * @return Returns the serverSoftware.
     */
    public String getServerSoftware() {
        return serverSoftware;
    }

    /**
     * @param serverSoftware
     *            The serverSoftware to set.
     */
    public void setServerSoftware(String serverSoftware) {
        this.serverSoftware = serverSoftware;
    }

    /**
     * @return Returns the result.
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result
     *            The result to set.
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return Returns the resultInfo.
     */
    public String getResultInfo() {
        return resultInfo;
    }

    /**
     * @param resultInfo
     *            The resultInfo to set.
     */
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    /**
     * @return Returns the userIdCui.
     */
    public String getUserIdCui() {
        return userIdCui;
    }

    /**
     * @param userIdCui
     *            The userIdCui to set.
     */
    public void setUserIdCui(String userIdCui) {
        this.userIdCui = userIdCui;
    }

    /**
     * @return Returns the realm.
     */
    public String getRealm() {
        return realm;
    }

    /**
     * @param realm
     *            The realm to set.
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * @return Returns the serviceClientIdentifier.
     */
    public String getServiceClientIdentifier() {
        return serviceClientIdentifier;
    }

    /**
     * @param serviceClientIdentifier
     *            The serviceClientIdentifier to set.
     */
    public void setServiceClientIdentifier(String serviceClientIdentifier) {
        this.serviceClientIdentifier = serviceClientIdentifier;
    }

    public String getRpOperatorName() {
        return rpOperatorName;
    }

    public void setRpOperatorName(String rpOperatorName) {
        this.rpOperatorName = rpOperatorName;
    }

    public String getServerSoftwareVersion() {
        return serverSoftwareVersion;
    }

    public void setServerSoftwareVersion(String serverSoftwareVersion) {
        this.serverSoftwareVersion = serverSoftwareVersion;
    }
}
