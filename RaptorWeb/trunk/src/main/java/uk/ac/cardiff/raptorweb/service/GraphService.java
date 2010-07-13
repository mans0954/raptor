/**
 *
 */
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.raptorweb.model.GraphModel;

/**
 * @author philsmart
 *
 */
public interface GraphService {

	public List getChartData(String statisticalUnitName);
	
	public List getStatisticalUnits();
	
	public void invokeStatisticalUnit(GraphModel model);
}
