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

import java.util.Map;

public interface EmailReport {

    /**
     * Generate a model of the the attributes required to correctly fill an appropriate email template.
     * 
     * @return a model of attribute value pairs. Where the attribute must match to the variable in the appropriate
     *         template email.
     */
    public Map<String, Object> generateModel();

    /**
     * Return the string identifier of the {@link EmailerProperties} this report uses as an email template to bind and
     * send the model.
     * 
     * @return the identifier of the emailer properties.
     */
    public String getEmailerPropertiesIdentifier();

    /**
     * Returns the name identifier of this email report.
     * 
     * @return the identifier of this email report.
     */
    public String getEmailReportIdentifier();
}
