/**
 *
 */
package uk.ac.cardiff.raptormua.wsinterface.impl;

import javax.jws.WebService;

import uk.ac.cardiff.raptormua.service.MUAProcess;
import uk.ac.cardiff.raptormua.wsinterface.MultiUnitAggregator;

/**
 * @author philsmart
 *
 */

@WebService(endpointInterface = "uk.ac.cardiff.raptormua.wsinterface.MultiUnitAggregator")
public class MultiUnitAggregatorImpl implements MultiUnitAggregator{

	private MUAProcess processService;

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.wsinterface.MultiUnitAggregator#getVersion()
	 */
	public String getVersion() {
		return "Alpha";
	}

	public void setProcessService(MUAProcess processService) {
		this.processService = processService;
	}

	public MUAProcess getProcessService() {
		return processService;
	}

}
