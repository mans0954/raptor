/**
 *
 */
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.model.SetupModel;

/**
 * @author philsmart
 *
 */
public interface SetupService {

	public List getStatisticalServices();

	

	/* performs the operation in place on the SetupModel */
	public void getCapabilities(SetupModel model);

}
