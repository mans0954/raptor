/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics.records;

/**
 * @author philsmart
 *
 */
public class Group {

	private String groupName;
	private long frequency;


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	public long getFrequency() {
		return frequency;
	}

	public void increment() {
		frequency++;

	}

}
