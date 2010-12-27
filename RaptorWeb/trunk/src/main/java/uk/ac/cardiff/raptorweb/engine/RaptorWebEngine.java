/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.WebMetadata;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.reports.ReportHandler;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.SetupModel;
import uk.ac.cardiff.raptorweb.sei.ServiceEndpointInterface;

/**
 * @author philsmart
 *
 */
public class RaptorWebEngine {
    static Logger log = LoggerFactory.getLogger(RaptorWebEngine.class);

    /* the registry which holds information about all MUAs injected from the xml file*/
    private MUARegistry registry;
    
    /* constructs downloadable reports from that currently displayed*/
    private ReportHandler reportHandler;

    /* holds the currently attached MUA */
    private MUAEntry attachedMUA;

    /* holds the capabilities and statistical information for the currently attached MUA */
    private Capabilities currentlyAttachedCapabilities;

    /* holds basic metadata about this particular RaptorWeb engine instance*/
    private WebMetadata webMetadata;

    /**
     * @return
     */
    public List getAttached() {

	return registry.getUAEntries();
    }

    public void setRegistry(MUARegistry registry) {
	this.registry = registry;
	for (MUAEntry entry : registry.getUAEntries()){
	    if (entry.getIsAttached()) setAttached(entry);
	}
    }

    public MUARegistry getRegistry() {
	return registry;
    }

    public void setAttached(MUAEntry entry) {
	log.info("Attaching {} and retrieving abilities",entry);
	this.attachedMUA = entry;
	currentlyAttachedCapabilities = ServiceEndpointInterface.discoverMUACapabilities(attachedMUA.getServiceEndpoint());
    }

    /**
     * returns the list of statistical units from the attached MUA. If no
     * MUA has been attached through the web interface, it chooses the
     * attached MUA that was loaded with <code>isAttached value="true"</code>
     * from the XML configuration.
     *
     * @return List of statistical units, as <code>StatisticalUnitInformation</code>
     */
    public List getStatisticalUnits() {
	/* get the statistical methods from the currently attached MUA */
	if (attachedMUA != null) {
	    return currentlyAttachedCapabilities.getStatisticalServices();
	} else {
	    MUAEntry attached = null;
	    for (MUAEntry entry : registry.getUAEntries()) {
		if (entry.getIsAttached()) {
		    attached = entry;
		}
	    }

	    Capabilities capabilities = ServiceEndpointInterface.discoverMUACapabilities(attached.getServiceEndpoint());
	    if (!capabilities.isError()){
		log.debug("Has retrieved {} statistics", capabilities.getStatisticalServices().size());
		attachedMUA = attached;
		currentlyAttachedCapabilities=capabilities;
		return capabilities.getStatisticalServices();
	    }

	}

	//return an empty list so as not confuse the view.
	return new ArrayList();
    }

    /**
     * @param selectedEndpoint
     * @return
     */
    public Capabilities getCapabilities(MUAEntry selectedEndpoint) {
	return ServiceEndpointInterface.discoverMUACapabilities(selectedEndpoint.getServiceEndpoint());
    }

    public MUAEntry getCurrentlyAttached() {
	for (MUAEntry entry : registry.getUAEntries()) {
	    if (entry.getIsAttached()) {
		return entry;
	    }
	}
	return null;
    }

    public AggregatorGraphModel invokeStatisticalUnit(StatisticalUnitInformation selectedStatisticalUnit) {
	AggregatorGraphModel gmodel = ServiceEndpointInterface.invokeStatisticalUnit(getCurrentlyAttached().getServiceEndpoint(), selectedStatisticalUnit.getStatisticParameters().getUnitName());
	return gmodel;

    }

    /**
     * @param currentTableGraph
     */
    public void generateReport(GraphModel model, String reportType, ReportModel report) {
	reportHandler.generateReport(model, reportType, report);

    }

    public void setReportHandler(ReportHandler reportHandler) {
	this.reportHandler = reportHandler;
    }

    public ReportHandler getReportHandler() {
	return reportHandler;
    }

    public void loadSavedReports(ReportModel report) {
	reportHandler.loadSavedReports(report);

    }

    /**
     * @param model
     */
    public void updateMUAStatistic(GraphModel model) {
	log.debug("Updating statistic {} ",model.getSelectedStatisticalUnit().getStatisticParameters().getUnitName());
	log.debug("With start date: {} and end date: {}" ,model.getSelectedStatisticalUnit().getStatisticParameters().getStartTimeAsDate(),model.getSelectedStatisticalUnit().getStatisticParameters().getEndTimeAsDate());
	ServiceEndpointInterface.updateStatisticalUnit(attachedMUA.getServiceEndpoint(),model.getSelectedStatisticalUnit());
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
	if (webMetadata!=null)function.setRequester(webMetadata.getWebName());
	else function.setRequester("UNKNOWN");
	boolean success = ServiceEndpointInterface.invokeAdministrativeFunction(attachedMUA.getServiceEndpoint(), function);
	log.debug("Removal successfull {}",success);
	if (!success) model.setProcessingResult("ERROR: Entries did not remove");
	else if (success) model.setProcessingResult("Operation Successful");
    }

    public void setWebMetadata(WebMetadata webMetadata) {
	this.webMetadata = webMetadata;
    }

    public WebMetadata getWebMetadata() {
	return webMetadata;
    }

    /**
     * @return
     */
    public boolean hasAttached() {
	return (attachedMUA!=null) ? true : false;
    }

}
