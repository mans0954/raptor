/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.util.Date;
import java.util.List;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 *
 */
public class EventPushMessage {
    //TODO to be replaced by SAML messages

    private List<Event> events;
    private ServiceMetadata clientMetadata;
    private Date timeOfPush;

    public void setEvents(List<Event> events) {
	this.events = events;
    }
    public List<Event> getEvents() {
	return events;
    }
    public void setTimeOfPush(Date timeOfPush) {
	this.timeOfPush = timeOfPush;
    }
    public Date getTimeOfPush() {
	return timeOfPush;
    }
    public void setClientMetadata(ServiceMetadata clientMetadata) {
	this.clientMetadata = clientMetadata;
    }
    public ServiceMetadata getClientMetadata() {
	return clientMetadata;
    }

}
