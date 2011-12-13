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
package uk.ac.cardiff.raptorweb.engine.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 * 
 */
public abstract class BaseReportConstructor {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

    /** the location of the directory within the application that reports are saved to */
    protected Resource saveDirectory;
    protected Resource baseDirectory;

    /** the enum list of the report types this handler can deal with e.g. excel or csv */
    public enum HandledReportTypes {
        excel("xls", "excel"), csv("csv", "CSV"), pdf("pdf", "PDF");

        public String fileExtension;
        public String displayName;

        HandledReportTypes(String fileExtension, String displayName) {
            this.fileExtension = fileExtension;
            this.displayName = displayName;
        }

    };

    private HandledReportTypes handledReportType;

    public BaseReportConstructor() {
        HandledReportTypes registeredReportType = getRegisterHandledReportType();
        if (registeredReportType != null) {
            handledReportType = registeredReportType;
        } else
            log.error("Report Generator did not register a report type");
    }

    protected abstract HandledReportTypes getRegisterHandledReportType();

    public abstract void generateReport(WebSession session);

    public void setSaveDirectory(Resource saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    public Resource getSaveDirectory() {
        return saveDirectory;
    }

    public void setBaseDirectory(Resource baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public Resource getBaseDirectory() {
        return baseDirectory;
    }

    protected void setHandledReportType(HandledReportTypes handledReportType) {
        this.handledReportType = handledReportType;
    }

    protected HandledReportTypes getHandledReportType() {
        return handledReportType;
    }

}
