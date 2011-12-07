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
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.service.SetupService;

/**
 * @author philsmart
 * 
 */
public class SetupServiceImpl implements SetupService {

    /** The engine of the web interface, where all central and common functions exist */
    private RaptorWebEngine webEngine;

    public List getStatisticalServices() {
        return null;
    }

    public void setAttachedEndpoint(WebSession websession) {
        webEngine.setAttached(websession.getSetupmodel().getSelectedEndpoint());
    }

    public List getAttached() {
        return webEngine.getAttached();
    }

    public void setWebEngine(RaptorWebEngine webEngine) {
        this.webEngine = webEngine;
    }

    public RaptorWebEngine getWebEngine() {
        return webEngine;
    }

    public void getCapabilities(WebSession websession) {
        websession.getSetupmodel().setSelectEndpointCapabilities(webEngine.getCapabilities(websession.getSetupmodel().getSelectedEndpoint()));
    }

    public void getCapabilitiesOfCurrentlyAttachedEndpoint(WebSession websession) {
        websession.getSetupmodel().setSelectEndpointCapabilities(webEngine.getCapabilitiesOfCurrentlyAttachedEndpoint());
    }

    public void deleteAllEntriesFromAttachedMUA(WebSession websession) {
        webEngine.deleteAllEntriesFromAttachedMUA(websession.getSetupmodel());
    }

    public boolean getHasAttached() {
        return webEngine.hasAttached();
    }

    public Capabilities getAttachedCapabilities() {
        return webEngine.getAttachedCapabilities();
    }

    public MUAEntry getCurrentlyAttached() {
        return webEngine.getCurrentlyAttached();
    }

    public void batchUpload(WebSession websession) {
        webEngine.batchUpload(websession);

    }

    public void sendResourceClassification() {
        webEngine.sendResourceClassification();
    }

}
