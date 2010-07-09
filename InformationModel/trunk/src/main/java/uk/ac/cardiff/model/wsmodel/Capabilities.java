/**
 * 
 */
package uk.ac.cardiff.model.wsmodel;

import java.util.List;

/**
 * @author philsmart
 *
 */
public class Capabilities {
	
	private List<String> attachedMUAs;
	private List<String> statisticalServices;
	public void setStatisticalServices(List<String> statisticalServices) {
		this.statisticalServices = statisticalServices;
	}
	public List<String> getStatisticalServices() {
		return statisticalServices;
	}
	public void setAttachedMUAs(List<String> attachedMUAs) {
		this.attachedMUAs = attachedMUAs;
	}
	public List<String> getAttachedMUAs() {
		return attachedMUAs;
	}

}
