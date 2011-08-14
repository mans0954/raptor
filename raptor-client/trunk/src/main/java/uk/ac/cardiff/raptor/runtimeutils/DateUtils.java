
package uk.ac.cardiff.raptor.runtimeutils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Simple class that provides a number of static helper methods to access date information
 * 
 * @author philsmart
 * 
 */
public class DateUtils {

    public static String getCurrentTimeFormatted() {
        DateTime current = new DateTime();
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("HH:mm:ss (dd/MM/yy)");
        return current.toString(dateFormat);
    }
}
