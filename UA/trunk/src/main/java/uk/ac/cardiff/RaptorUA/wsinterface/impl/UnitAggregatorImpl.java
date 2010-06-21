/**
 *
 */
package uk.ac.cardiff.RaptorUA.wsinterface.impl;

import java.util.List;

import javax.jws.WebService;

import uk.ac.cardiff.RaptorUA.wsinterface.UnitAggregator;

/**
 * @author philsmart
 *
 */

@WebService(endpointInterface = "uk.ac.cardiff.RaptorUA.wsinterface.UnitAggregator")
public class UnitAggregatorImpl implements UnitAggregator{

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.RaptorUA.wsinterface.UnitAggregator#getAllAuthentications()
	 */
	public List getAllAuthentications() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.RaptorUA.wsinterface.UnitAggregator#getVersion()
	 */
	public String getVersion() {
		return "Alpha";
	}

}
