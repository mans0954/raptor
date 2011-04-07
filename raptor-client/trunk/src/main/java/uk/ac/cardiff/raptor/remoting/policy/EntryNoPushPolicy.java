package uk.ac.cardiff.raptor.remoting.policy;

import java.util.List;

import uk.ac.cardiff.model.event.Event;

public class EntryNoPushPolicy extends PushPolicy{
	
	/** The threshold on the number of events needed before release*/
	private int pushOnOrAfterNoEntries;
	
	
	public boolean evaluatePolicy(List<Event> events) {
		if (pushOnOrAfterNoEntries <= events.size()) 
			return true;
		return false;
	}

    public void setPushOnOrAfterNoEntries(int pushOnOrAfterNoEntries) {
	this.pushOnOrAfterNoEntries = pushOnOrAfterNoEntries;
    }

    public int getPushOnOrAfterNoEntries() {
	return pushOnOrAfterNoEntries;
    }

}
