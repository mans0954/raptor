/**
 *
 */
package uk.ac.cardiff.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.text.StrTokenizer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 *
 */
public class NumberOfLogEntriesWritter {

    static Logger log = LoggerFactory.getLogger(NumberOfLogEntriesWritter.class);

    private final Timer timer = new Timer();
    private final int ms;

    private String logfile;

    public NumberOfLogEntriesWritter(int ms, String logfile) {
	this.ms = ms;
	this.logfile = logfile;
    }

    public void start() {
	timer.schedule(new TimerTask() {


	    Logger logtimer = LoggerFactory.getLogger(NumberOfLogEntriesWritter.class);
	    HashMap<String,String> read = new HashMap<String,String>();

	    public void run() {
		read();

	    }

	    private void read() {
		URL logfileURL;
		try {
		    logfileURL = new URL(logfile);
		    int count=0;
		    URLConnection logfileconnection = logfileURL.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(logfileconnection.getInputStream()));
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
			//give it something todo while reading, better mimicing the ica parser.
			count++;

		    }
		    System.out.println("Has "+count+" entries");
		} catch (MalformedURLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }






	}, 1, ms);
    }

}
