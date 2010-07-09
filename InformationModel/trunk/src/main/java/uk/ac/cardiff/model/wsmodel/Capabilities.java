/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

/**
 * @author philsmart
 *
 */
public class Capabilities implements Serializable{

	private List<String> attached;
	private List<String> statisticalServices;
	public void setStatisticalServices(List<String> statisticalServices) {
		this.statisticalServices = statisticalServices;
	}
	public List<String> getStatisticalServices() {
		return statisticalServices;
	}
	public void setAttached(List<String> attached) {
		this.attached = attached;
	}
	public List<String> getAttached() {
		return attached;
	}

}
