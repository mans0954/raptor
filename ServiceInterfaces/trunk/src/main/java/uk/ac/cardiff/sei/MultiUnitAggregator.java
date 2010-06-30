/**
 *
 */
package uk.ac.cardiff.sei;

import javax.jws.WebService;

/**
 * @author philsmart
 *
 */

@WebService
public interface MultiUnitAggregator {

	public String getVersion();

	public void performStatistic(String statisticName);

}
