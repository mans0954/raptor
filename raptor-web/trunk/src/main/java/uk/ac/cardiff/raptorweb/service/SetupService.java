/**
 *
 */
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.SetupModel;
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 *
 */
public interface SetupService {

	public List getStatisticalServices();

	public void setAttachedEndpoint(WebSession websession);

	/**
	 * performs the operation in place on the SetupModel
	 * @param model
	 */
	public void getCapabilities(WebSession websession);

	public void deleteAllEntriesFromAttachedMUA(WebSession websession);

	/**
	 * Check if there is at least one attached MUA
	 * @return
	 */
	public boolean getHasAttached();

	public Capabilities getAttachedCapabilities();

	public MUAEntry getCurrentlyAttached();

}
