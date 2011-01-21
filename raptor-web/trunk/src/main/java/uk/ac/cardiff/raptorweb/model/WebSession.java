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
