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
