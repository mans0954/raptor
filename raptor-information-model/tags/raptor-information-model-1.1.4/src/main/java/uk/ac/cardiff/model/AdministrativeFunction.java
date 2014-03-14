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
package uk.ac.cardiff.model;

import java.util.Date;

/**
 * @author philsmart
 *
 */
public class AdministrativeFunction {

    /* enumeration of the different types of administrative function allowed in the setup page*/
    public static enum AdministrativeFunctionType {REMOVEALL}
    private Date requestCreationTime;
    private String requester;
    private AdministrativeFunctionType administrativeFunction;

    /**
     * Sets the <code>requestCreationTime</code> to be the time this class was instantiated
     */
    public AdministrativeFunction(){
	requestCreationTime = new Date(System.currentTimeMillis());
    }

    public Date getRequestCreationTime() {
	return requestCreationTime;
    }
    public void setRequestCreationTime(Date requestCreationTime){
	this.requestCreationTime = requestCreationTime;
    }
    public void setRequester(String requester) {
	this.requester = requester;
    }
    public String getRequester() {
	return requester;
    }
    public void setAdministrativeFunction(AdministrativeFunctionType administrativeFunction) {
	this.administrativeFunction = administrativeFunction;
    }
    public AdministrativeFunctionType getAdministrativeFunction() {
	return administrativeFunction;
    }


}
