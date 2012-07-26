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

package uk.ac.cardiff.raptormua.mail;

public abstract class AbstractEmailReport implements EmailReport {

    /**
     * The identifier for the {@link EmailerProperties} this report should use to bind its model to a template and send
     * reports.
     */
    private String emailerPropertiesIdentifier;

    /**
     * The identifier of this report.
     */
    private String emailReportIdentifier;

    /**
     * @param emailerPropertiesIdentifier the emailerPropertiesIdentifier to set
     */
    public void setEmailerPropertiesIdentifier(String emailerPropertiesIdentifier) {
        this.emailerPropertiesIdentifier = emailerPropertiesIdentifier;
    }

    /**
     * @return the emailerPropertiesIdentifier
     */
    public String getEmailerPropertiesIdentifier() {
        return emailerPropertiesIdentifier;
    }

    /**
     * @param emailReportIdentifier the emailReportIdentifier to set
     */
    public void setEmailReportIdentifier(String emailReportIdentifier) {
        this.emailReportIdentifier = emailReportIdentifier;
    }

    /**
     * @return the emailReportIdentifier
     */
    public String getEmailReportIdentifier() {
        return emailReportIdentifier;
    }

}
