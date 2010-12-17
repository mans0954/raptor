/**
 *
 */
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.SetupModel;

/**
 * @author philsmart
 *
 */
public interface SetupService {

	public List getStatisticalServices();

	public void setAttachedEndpoint(SetupModel model);

	/**
	 * performs the operation in place on the SetupModel
	 * @param model
	 */
	public void getCapabilities(SetupModel model);

	public void deleteAllEntriesFromAttachedMUA(SetupModel model);

	/**
	 * Check if there is at least one attached MUA
	 * @return
	 */
	public boolean getHasAttached();

	public Capabilities getAttachedCapabilities();

	public MUAEntry getCurrentlyAttached();

}
