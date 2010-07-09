/**
 *
 */
package uk.ac.cardiff.sei;

import java.util.List;

import javax.jws.WebService;

import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */

@WebService
public interface MultiUnitAggregator {

	public String getVersion();

	public void performStatistic(String statisticName);

	public Capabilities getCapabilities();

}
