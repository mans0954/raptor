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
/**
 *
 */
package uk.ac.cardiff.model.event;

import uk.ac.cardiff.model.event.auxiliary.PrincipalInformation;

/**
 * @author philsmart
 *
 */
public class AuthenticationEvent extends Event {

    private String authenticationType;
    private String principalName;

    /** This is fixed to a principal expansion. Not used in
     * Hash or Equality methods */
    private PrincipalInformation principalInformation;


    public AuthenticationEvent(){
        super();
    }

    /**
     * Copy constructor
     *
     * @param event
     */
    protected AuthenticationEvent(AuthenticationEvent event){
        super(event);
        this.authenticationType = event.getAuthenticationType();
        this.principalName = event.getPrincipalName();
        if (event.getPrincipalInformation()!=null){
            this.principalInformation = new PrincipalInformation(event.getPrincipalInformation());
        }

    }

    /**
     * Copy method. Alternative to clone.
     */
    public AuthenticationEvent copy(){
        return new AuthenticationEvent(this);
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    /**
     * @param principalInformation
     *            the principalInformation to set
     */
    public void setPrincipalInformation(PrincipalInformation principalInformation) {
        this.principalInformation = principalInformation;
    }

    /**
     * @return the principalInformation
     */
    public PrincipalInformation getPrincipalInformation() {
        return principalInformation;
    }

}
