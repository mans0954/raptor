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

package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.engine.upload.FileUpload;

/**
 * @author philsmart
 * 
 */
public class SetupModel implements Serializable {

    /** Generated Serial UID */
    private static final long serialVersionUID = 1685943278008861977L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(SetupModel.class);

    /** The currently selected MUA */
    private MUAEntry selectedEndpoint;

    /** The capabilities of the currently selected MUA */
    private Capabilities selectEndpointCapabilities;

    /** A <code>String</code> that holds a message from the result of processing */
    private String processingResult;

    /** Class for storing batch log file upload information */
    private FileUpload fileUpload;

    public SetupModel() {
        fileUpload = new FileUpload();
    }

    public void setSelectEndpointCapabilities(Capabilities selectEndpointCapabilities) {
        this.selectEndpointCapabilities = selectEndpointCapabilities;
    }

    public Capabilities getSelectEndpointCapabilities() {
        return selectEndpointCapabilities;
    }

    public void setSelectedEndpoint(MUAEntry selectedEndpoint) {
        this.selectedEndpoint = selectedEndpoint;
    }

    public MUAEntry getSelectedEndpoint() {
        return selectedEndpoint;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

}
