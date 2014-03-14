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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailReportHandler {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(EmailReportHandler.class);

    /**
     * List of reports configured.
     */
    private List<EmailReport> emailReports;

    /**
     * Email handler to send emails.
     */
    private EmailHandler emailHandler;

    public void sendReport(String reportName, String[] emailAddresses) {
        log.info("Sending report [{}]", reportName);
        for (EmailReport emailReport : emailReports) {
            if (emailReport.getEmailReportIdentifier().equals(reportName)) {
                send(emailReport, emailAddresses);
            }
        }
    }

    public void sendAllReports(String[] emailAddresses) {
        for (EmailReport emailReport : emailReports) {
            send(emailReport, emailAddresses);
        }
    }

    private void send(EmailReport emailReport, String[] emailAddresses) {
        String emailPropertiesIdentifier = emailReport.getEmailerPropertiesIdentifier();
        Map<String, Object> model = emailReport.generateModel();
        TemplateEmailContext emailContext = new TemplateEmailContext();
        emailContext.setEmailAddress(emailAddresses);
        emailContext.setModel(model);
        emailHandler.sendEmail(emailContext, emailPropertiesIdentifier);
    }

    /**
     * @param emailReports the emailReports to set
     */
    public void setEmailReports(List<EmailReport> emailReports) {
        this.emailReports = emailReports;
    }

    /**
     * @return the emailReports
     */
    public List<EmailReport> getEmailReports() {
        return emailReports;
    }

    /**
     * @param emailHandler the emailHandler to set
     */
    public void setEmailHandler(EmailHandler emailHandler) {
        this.emailHandler = emailHandler;
    }

    /**
     * @return the emailHandler
     */
    public EmailHandler getEmailHandler() {
        return emailHandler;
    }

}
