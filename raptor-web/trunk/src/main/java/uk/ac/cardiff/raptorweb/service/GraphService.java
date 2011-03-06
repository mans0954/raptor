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
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 *
 */
public interface GraphService {

	public List getChartData(String statisticalUnitName);

	public List getStatisticalUnits();

	public void populateStatisticalUnits(WebSession websession);

	public void invokeStatisticalUnit(WebSession websession);

	public void generateExcelReport(WebSession websession);

	public void generateCSVReport(WebSession websession);

	public void loadSavedReports(WebSession websession);

	public void updateMUAStatistic(WebSession websession);

	public void removeReport(WebSession websession);

	public Capabilities getAttachedCapabilities();

	public MUAEntry getCurrentlyAttached();

	public void removeSeriesFromSelectedStatistic(WebSession websession);
	
	public void removeSelectedFilterFromSelectedStatistic(WebSession websession);

	public void addSeriesToSelectedStatistic(WebSession websession);
	
	public void addFilterToSelectedSeries(WebSession websession);
	
	public void rerenderGraph(WebSession websession);

}
