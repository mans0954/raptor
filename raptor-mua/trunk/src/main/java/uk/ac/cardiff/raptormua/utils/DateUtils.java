
package uk.ac.cardiff.raptormua.utils;

import java.util.List;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.Event;

public class DateUtils {

    /**
     * Returns the earliest date and time (by eventTime) {@link DateTime} from the input set of <code>events</code>
     * 
     * @param events the list of events from which to return the earliest.
     * @return the date and time of the earliest event, null otherwise.
     */
    public DateTime getEarliestTime(List<Event> events) {
        DateTime earliest = null;
        for (Event event : events) {
            if (earliest == null) {
                earliest = event.getEventTime();
            }
            if (event.getEventTime().isBefore(earliest)) {
                earliest = event.getEventTime();
            }
        }
        return earliest;
    }

    /**
     * Returns the latest date and time (by eventTime) {@link DateTime} from the input set of <code>events</code>
     * 
     * @param events the list of events from which to return the latest.
     * @return the date and time of the latest event, null otherwise.
     */
    public DateTime getLatestTime(List<Event> events) {
        DateTime earliest = null;
        for (Event event : events) {
            if (earliest == null) {
                earliest = event.getEventTime();
            }
            if (event.getEventTime().isAfter(earliest)) {
                earliest = event.getEventTime();
            }
        }
        return earliest;
    }

}
