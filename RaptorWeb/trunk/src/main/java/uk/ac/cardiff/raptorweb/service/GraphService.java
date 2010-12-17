/**
 *
 */
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;

/**
 * @author philsmart
 *
 */
public interface GraphService {

	public List getChartData(String statisticalUnitName);

	public List getStatisticalUnits();

	public void invokeStatisticalUnit(GraphModel model);

	public void generateExcelReport(GraphModel model, ReportModel report);

	public void generateCSVReport(GraphModel model, ReportModel report);

	public void loadSavedReports(ReportModel model);

	public void updateMUAStatistic(GraphModel model);

	public void removeReport(ReportModel model);

	public Capabilities getAttachedCapabilities();

	public MUAEntry getCurrentlyAttached();
}
