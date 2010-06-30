/**
 *
 */
package uk.ac.cardiff.raptormua.service;

/**
 * @author philsmart
 *
 */
public interface MUAProcess {


	public void poll();

	/**
	 * @param statisticName
	 */
	public void performStatistic(String statisticName);
}
