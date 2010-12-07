/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.reports.ReportHandler;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.sei.ServiceEndpointInterface;

/**
 * @author philsmart
 *
 */
public class RaptorWebEngine {
	static Logger log = LoggerFactory.getLogger(RaptorWebEngine.class);

	private MUARegistry registry;
	private ReportHandler reportHandler;

	/**
	 * @return
	 */
	public List getAttached() {

		return registry.getUAEntries();
	}

	public void setRegistry(MUARegistry registry) {
		this.registry = registry;
	}

	public MUARegistry getRegistry() {
		return registry;
	}

	public List getStatisticalUnits(){
		/* get the statistical methods from the currently attached MUA */
		MUAEntry attached = null;
		for (MUAEntry entry : registry.getUAEntries()){
			if (entry.getIsAttached()){
				attached = entry;
			}
		}
		Capabilities capabilities = ServiceEndpointInterface.discoverMUACapabilities(attached.getServiceEndpoint());
		log.debug("Has retrieved {} statistics",capabilities.getStatisticalServices().size());
		if (!capabilities.isError()) return capabilities.getStatisticalServices();
		return null;
	}

	/**
	 * @param selectedEndpoint
	 * @return
	 */
	public Capabilities getCapabilities(MUAEntry selectedEndpoint) {
			return ServiceEndpointInterface.discoverMUACapabilities(selectedEndpoint.getServiceEndpoint());
	}

	public MUAEntry getCurrentlyAttached() {
		for (MUAEntry entry : registry.getUAEntries()){
			if (entry.getIsAttached()){
				return entry;
			}
		}
		return null;
	}

	public AggregatorGraphModel invokeStatisticalUnit(StatisticalUnitInformation selectedStatisticalUnit) {
		AggregatorGraphModel gmodel = ServiceEndpointInterface.invokeStatisticalUnit(getCurrentlyAttached().getServiceEndpoint(),selectedStatisticalUnit.getStatisticParameters().getUnitName());
		return gmodel;

	}

	/**
	 * @param currentTableGraph
	 */
	public void generateReport(GraphModel model, String reportType, ReportModel report) {
		reportHandler.generateReport(model, reportType,report);

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






}
