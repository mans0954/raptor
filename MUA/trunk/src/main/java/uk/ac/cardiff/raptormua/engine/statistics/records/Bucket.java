package uk.ac.cardiff.raptormua.engine.statistics.records;

import org.joda.time.DateTime;

public class Bucket {
	
	private DateTime start;
	private DateTime end;
	private int frequency;
	
	
	public boolean isInside(DateTime eventTime){
		/* semantics here are; if equal to or after start but before end return true*/
		if ((eventTime.isEqual(start) && eventTime.isBefore(end)) || (eventTime.isAfter(start) && eventTime.isBefore(end))) return true;
		return false;
	}
	
	public void increment(){
		frequency++;
	}
	
	/**
	 * @param start the start to set
	 */
	public void setStart(DateTime start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public DateTime getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(DateTime end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public DateTime getEnd() {
		return end;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

}
