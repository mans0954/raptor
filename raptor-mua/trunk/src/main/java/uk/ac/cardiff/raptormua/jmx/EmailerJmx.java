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

package uk.ac.cardiff.raptormua.jmx;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import uk.ac.cardiff.raptormua.mail.EmailHandler;
import uk.ac.cardiff.raptormua.mail.EmailReportHandler;

@ManagedResource(objectName = "uk.ac.cardiff.raptor:name=mailOperations",
        description = "Set of operations for force emailing a user information about this MUA")
public class EmailerJmx {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(EmailHandler.class);

    /** Email report handler used to perform all email operations. */
    private EmailReportHandler emailReportHandler;

    @ManagedOperation(description = "Email daily report to comma seperated list of email addresses")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "addresses",
            description = "comma seperated list of addresses")})
    public void emailDailyReport(String addresses) {
        String[] splitAddresses = addresses.split(",");
        log.info("Performing JMX operation to email mua daily report - to {}", Arrays.toString(splitAddresses));
        emailReportHandler.sendReport("dailyReport", splitAddresses);
    }

    /**
     * @param emailReportHandler the emailReportHandler to set
     */
    public void setEmailReportHandler(EmailReportHandler emailReportHandler) {
        this.emailReportHandler = emailReportHandler;
    }

    /**
     * @return the emailReportHandler
     */
    public EmailReportHandler getEmailReportHandler() {
        return emailReportHandler;
    }

}
