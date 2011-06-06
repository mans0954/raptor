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
package uk.ac.cardiff.raptorweb.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptorweb.engine.reports.ReportHandler;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.SetupModel;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.sei.ServiceEndpointClient;

/**
 * @author philsmart
 *
 */
public class RaptorWebEngine {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(RaptorWebEngine.class);

    /** the registry which holds information about all MUAs injected from the xml file*/
    private MUARegistry registry;

    /** constructs downloadable reports from that currently displayed*/
    private ReportHandler reportHandler;

    /** holds the currently attached MUA */
    private MUAEntry attachedMUA;

    /** holds the capabilities and statistical information for the currently attached MUA */
    private Capabilities currentlyAttachedCapabilities;

    /** holds basic metadata about this particular RaptorWeb engine instance*/
    private ServiceMetadata webMetadata;

    /** The client used to set up, connect to, action and return results from the attached MUA*/
    private ServiceEndpointClient serviceEndpointClient;

    /**
     * Constructor, pass the service endpoint client so initialisation on certain getters can be performed.
     *
     * @param serviceEndpointClient
     */
    private RaptorWebEngine(ServiceEndpointClient serviceEndpointClient){
	this.serviceEndpointClient = serviceEndpointClient;
    }

    /**
     * @return
     */
    public List getAttached() {
	return registry.getMUAEntries();
    }

    public void setRegistry(MUARegistry registry) {
	this.registry = registry;
	for (MUAEntry entry : registry.getMUAEntries()){
	    if (entry.getIsAttached()) setAttached(entry);
	}
    }

    public MUARegistry getRegistry() {
	return registry;
    }

    public void setAttached(MUAEntry entry) {
	log.info("Attaching {} and retrieving abilities",entry);
	this.attachedMUA = entry;
	currentlyAttachedCapabilities = serviceEndpointClient.discoverMUACapabilities(attachedMUA);
    }

    public SuggestionValues getSuggestionValues(){
	if (currentlyAttachedCapabilities!=null)
	    return currentlyAttachedCapabilities.getSuggestionValues();
	return new SuggestionValues();
    }

    /**
     * Returns the list of USER level statistical units from the attached MUA as stored in the
     * currentlyAttachedCapabilities. If no currentlyAttachedCapabilities exists, hence
     * no MUA has been successfully queried, it chooses either the current MUA <code>attachedMUA</code>
     * or the MUA that was loaded with <code>isAttached value="true"</code>
     * from the XML configuration.
     *
     * @return List of statistical units, as <code>StatisticalUnitInformation</code>
     */
    public List<StatisticalUnitInformation> getStatisticalUnits() {
	/* get the statistical methods from the currently attached MUA */
	if (currentlyAttachedCapabilities!=null && currentlyAttachedCapabilities.isError()==false){
	    return currentlyAttachedCapabilities.getStatisticalServices();
	} else {
	    MUAEntry attached = null;
	    if (attachedMUA!=null){
		attached =attachedMUA;
	    }
	    for (MUAEntry entry : registry.getMUAEntries()) {
		if (entry.getIsAttached()) {
		    attached = entry;
		}
	    }
	    Capabilities capabilities = serviceEndpointClient.discoverMUACapabilities(attached);
	    if (capabilities!=null && !capabilities.isError()){
		log.debug("Has retrieved {} statistics", capabilities.getStatisticalServices().size());
		attachedMUA = attached;
		currentlyAttachedCapabilities=capabilities;
		return capabilities.getStatisticalServices();
	    }

	}

	//return an empty list so as not confuse the view.
	return new ArrayList<StatisticalUnitInformation>();
    }

    /**
     * @param selectedEndpoint
     * @return
     */
    public Capabilities getCapabilities(MUAEntry selectedEndpoint) {
	return serviceEndpointClient.discoverMUACapabilities(selectedEndpoint);
    }

    public MUAEntry getCurrentlyAttached() {
	for (MUAEntry entry : registry.getMUAEntries()) {
	    if (entry.getIsAttached()) {
		return entry;
	    }
	}
	return null;
    }

    public AggregatorGraphModel invokeStatisticalUnit(StatisticalUnitInformation selectedStatisticalUnit) {
	AggregatorGraphModel gmodel = serviceEndpointClient.invokeStatisticalUnit(getCurrentlyAttached(), selectedStatisticalUnit.getStatisticParameters().getUnitName());
	return gmodel;

    }

    public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation selectedStatisticalUnit) {
	AggregatorGraphModel gmodel = serviceEndpointClient.updateAndinvokeStatisticalUnit(getCurrentlyAttached(), selectedStatisticalUnit);
	return gmodel;

    }

    /**
     * @param currentTableGraph
     */
    public void generateReport(WebSession session, String reportType) {
	reportHandler.generateReport(session, reportType);

    }

    public void setReportHandler(ReportHandler reportHandler) {
	this.reportHandler = reportHandler;
    }

    public ReportHandler getReportHandler() {
	return reportHandler;
    }

    public void loadSavedReports(WebSession session) {
	reportHandler.loadSavedReports(session);

    }

    /**
     * @param model
     */
    public void updateMUAStatistic(GraphModel model) {
	log.debug("Updating statistic {} ",model.getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getUnitName());
	log.debug("Has startDate {}",model.getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getStartTimeAsDate());
	serviceEndpointClient.updateStatisticalUnit(attachedMUA,model.getSelectedStatisticalUnit().getStatisticalUnitInformation());
    }

    /**
     * @param model
     */
    public void removeReport(ReportModel model) {
	reportHandler.removeReport(model);


    }

    public Capabilities getAttachedCapabilities() {
	return currentlyAttachedCapabilities;
    }

    /**
     * @param model
     */
    public void deleteAllEntriesFromAttachedMUA(SetupModel model) {
	log.info("Deleting all entries from MUA [{}]",attachedMUA.getServiceEndpoint());
	AdministrativeFunction function = new AdministrativeFunction();
	function.setAdministrativeFunction(AdministrativeFunction.AdministrativeFunctionType.REMOVEALL);
	if (webMetadata!=null)
	    function.setRequester(webMetadata.getServiceName());
	else
	    function.setRequester("UNKNOWN");
	boolean success = serviceEndpointClient.invokeAdministrativeFunction(attachedMUA, function);
	log.debug("Removal successfull {}",success);
	if (!success) model.setProcessingResult("ERROR: Entries did not remove");
	else if (success) model.setProcessingResult("Operation Successful");
    }

    public void setWebMetadata(ServiceMetadata webMetadata) {
	this.webMetadata = webMetadata;
    }

    public ServiceMetadata getWebMetadata() {
	return webMetadata;
    }

    /**
     * @return
     */
    public boolean hasAttached() {
	return (attachedMUA!=null) ? true : false;
    }

    public void setServiceEndpointClient(ServiceEndpointClient serviceEndpointClient) {
	this.serviceEndpointClient = serviceEndpointClient;
    }

    public ServiceEndpointClient getServiceEndpointClient() {
	return serviceEndpointClient;
    }

    public void batchUpload(WebSession websession) {
        ArrayList<LogFileUpload> uploadFiles = websession.getSetupmodel().getFileUpload().getFiles();

        ArrayList<LogFileUpload> toUpload = new ArrayList<LogFileUpload>();
        for (LogFileUpload lfu : uploadFiles){
            if (lfu.isProcessed()==false){
                if (lfu.getEventType() == LogFileUpload.ParsingEventType.NA){
                    lfu.setProcessingStatus("No event type selected");
                }
                else{
                    toUpload.add(lfu);
                }
            }
        }
        if (toUpload.size()>0){
            List<LogFileUploadResult> results = serviceEndpointClient.sendBatch(toUpload,attachedMUA);

            if (results!=null){
                for (LogFileUpload uploadFile : toUpload){
                    for (LogFileUploadResult result : results){
                        if (uploadFile.getId()==result.getId()){
                            uploadFile.setProcessingStatus(result.getStatus());
                            uploadFile.setProcessed(result.isProcessed());
                        }
                    }
                }
            }
            if (results==null){
                for (LogFileUpload uploadFile : toUpload){
                    uploadFile.setProcessingStatus("Error in upload, Retry");
                    uploadFile.setProcessed(false);
                }
            }
        }


    }

    /**
     * Sends back to the attached MUA the current resource classification
     */
    public void sendResourceClassification() {
        List<ResourceMetadata> resourceMetadata = currentlyAttachedCapabilities.getResourceMetadata();
        serviceEndpointClient.sendResourceMetadata(resourceMetadata,attachedMUA);
        
    }


}
