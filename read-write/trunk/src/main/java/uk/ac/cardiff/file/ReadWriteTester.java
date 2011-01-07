/**
 *
 */
package uk.ac.cardiff.file;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.StatusManager;

/**
 * @author philsmart
 *
 */
public class ReadWriteTester {

    static Logger log = LoggerFactory.getLogger(LogFileReader.class);

    public static void main(String args[]) {

	System.out.println("Starting");

	LogFileWriter writer = new LogFileWriter(100);
	writer.start();

	NumberOfLogEntriesWritter counter = new NumberOfLogEntriesWritter(1000,"file:///Users/philsmart/Documents/DataSets/Logs/idp-audit-test.txt");
	counter.start();

	//LogFileReader reader = new LogFileReader(1, "file:///Users/philsmart/Documents/DataSets/Logs/idp-audit-test.txt");
	//reader.start();
    }

}
