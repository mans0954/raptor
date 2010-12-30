/**
 *
 */
package main.uk.ac.cf.dao.external.file;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * @author philsmart
 *
 */
public class LogFileParserTest {


    /**
     * This test is not a unit test, it is not an integration test, indeed its not much of a test
     */
    @Test
    public void testDateParser(){
	String value = "20100328T010234";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dtf = dtf.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("UTC")));
	DateTime dt = dtf.parseDateTime(value);
	System.out.println(dt);
    }

}
