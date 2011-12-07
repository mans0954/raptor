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
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

import org.springframework.security.core.Authentication;


/**
 * The Web Session class holds all view specific state information e.g. about the user, the startmodel, the graphmodel etc.
 * Hence any call to these must come through the flowscope.webuser context.
 * @author philsmart
 *
 */
public class WebSession implements Serializable {


    private Authentication user;
    private StartModel startmodel;
    private GraphModel graphmodel;
    private ReportModel reportmodel;
    private SetupModel setupmodel;

    /* initilise the models used by the diferent view states*/
    public WebSession(){
	startmodel = new StartModel();
	graphmodel = new GraphModel();
	reportmodel = new ReportModel();
	setupmodel = new SetupModel();
    }


    public void setUser(Authentication user) {
	this.user = user;
    }

    public Authentication getUser() {
	return user;
    }

    public void setStartmodel(StartModel startmodel) {
	this.startmodel = startmodel;
    }

    public StartModel getStartmodel() {
	return startmodel;
    }

    public void setGraphmodel(GraphModel graphmodel) {
	this.graphmodel = graphmodel;
    }

    public GraphModel getGraphmodel() {
	return graphmodel;
    }

    public void setReportmodel(ReportModel reportmodel) {
	this.reportmodel = reportmodel;
    }

    public ReportModel getReportmodel() {
	return reportmodel;
    }

    public void setSetupmodel(SetupModel setupmodel) {
	this.setupmodel = setupmodel;
    }

    public SetupModel getSetupmodel() {
	return setupmodel;
    }


}
