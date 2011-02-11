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
import uk.ac.cardiff.raptorweb.model.WebSession;
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
     * Returns the list of USER level statistical units from the attached MUA as stored in the
     * currentlyAttachedCapabilities. If no currentlyAttachedCapabilities exists, hence
     * no MUA has been successfully queried, it chooses either the current MUA <code>attachedMUA</code>
     * or the MUA that was loaded with <code>isAttached value="true"</code>
     * from the XML configuration.
     *
     * @return List of statistical units, as <code>StatisticalUnitInformation</code>
     */
    public List getStatisticalUnits() {
	/* get the statistical methods from the currently attached MUA */
	if (currentlyAttachedCapabilities!=null && currentlyAttachedCapabilities.isError()==false){
	    return currentlyAttachedCapabilities.getStatisticalServices();
	} else {
	    MUAEntry attached = null;
	    if (attachedMUA!=null){
		attached =attachedMUA;
	    }
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

    public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation selectedStatisticalUnit) {
	AggregatorGraphModel gmodel = ServiceEndpointInterface.updateAndinvokeStatisticalUnit(getCurrentlyAttached().getServiceEndpoint(), selectedStatisticalUnit);
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
	log.debug("Updating statistic {} ",model.getSelectedStatisticalUnit().getStatisticalIUnitInformation().getStatisticParameters().getUnitName());
	ServiceEndpointInterface.updateStatisticalUnit(attachedMUA.getServiceEndpoint(),model.getSelectedStatisticalUnit().getStatisticalIUnitInformation());
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
