/**
 *
 */
package uk.ac.cardiff.file;

import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 *
 */
public class LogFileWriter {
	static Logger log = LoggerFactory.getLogger(LogFileReader.class);

	private final Timer timer = new Timer();
    private final int ms;

    public LogFileWriter(int ms) {
        this.ms = ms;
    }

    public void start() {
        timer.schedule(new TimerTask() {
        	Logger logtimer = LoggerFactory.getLogger(LogFileWriter.class);
        	/** Formatter used to convert timestamps to strings. */
            private DateTimeFormatter dateFormatter = ISODateTimeFormat.basicDateTimeNoMillis();
            int count=0;

            public void run() {
                log();
                if (count ==100000){
                    System.out.println("Stopped Writing");
                    cancel();
                }

            }
            private void log() {
        	count++;
            	String fakeAuditOutput = "|urn:mace:shibboleth:1.0:profiles:AuthnRequest"+count+"||https://sdauth.sciencedirect.com/|urn:mace:shibboleth:2.0:profiles:saml1:sso|https://idp.cardiff.ac.uk/shibboleth|urn:oasis:names:tc:SAML:1.0:profiles:browser-post|_e542ce34a2347c2090bb11a669171af7|spxdp|urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport||_10919ce60a5332e0ecde77963b2fff2d|_08d1d21934aa9a973b6d12a9cf348530,|";
            	DateTime date = new DateTime(System.currentTimeMillis());
                logtimer.info(date.toString(dateFormatter)+fakeAuditOutput+count);
            }
        }, 1, ms);
    }



}
