/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;

/**
 * @author philsmart
 *
 */
public class AuthenticationStatistic extends Statistic{
	static Logger log = Logger.getLogger(AuthenticationStatistic.class);



	public void countEntryPerInterval(String timeInterval) throws StatisticalUnitException{
		log.debug("Performing countEntryPerInterval Statistical Operation");
		int timeIntervalInt = Integer.parseInt(timeInterval);
		log.debug("Params for method:  "+this.getField()+", "+this.getMethodName()+", "+this.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Working off "+this.getAuthEntries().size()+" entries");

		/* divide the temporal extent into evenly sized buckets*/
		DateTime start = startingTime();
		DateTime end = endingTime();

		long difference = end.getMillis() - start.getMillis();
		log.debug("There is "+difference+"ms difference between start and end entries");
		int numberOfBuckets = (int) (difference / timeIntervalInt);
		long reminder = difference % timeIntervalInt;
		log.debug("There are "+numberOfBuckets+" buckets, with reminder "+reminder+"ms");
		/* now create that many buckets of length timeIntervalInt*/
		Bucket[] buckets = new Bucket[numberOfBuckets];

		buckets[0] = new Bucket();
		buckets[0].setStart(start);
		buckets[0].setEnd(new DateTime(start.getMillis()+timeIntervalInt));

		for (int i=1 ; i < numberOfBuckets; i++){
			Bucket buck = new Bucket();
			buck.setStart(buckets[i-1].getEnd());
			buck.setEnd(new DateTime(buckets[i-1].getEnd().getMillis()+timeIntervalInt));
			buckets[i] = buck;
		}

		for (Entry entry : this.getAuthEntries()){

			for (int i=0 ; i < buckets.length; i++){
				if (buckets[i].isInside(entry.getEventTime()))buckets[i].increment();
			}
		}

		int testCount = 0;
		for (Bucket bucket : buckets){
			testCount +=bucket.getFrequency();
			log.debug(bucket.getStart()+"-"+bucket.getEnd()+" --> "+bucket.getFrequency());
		}
		/* test count should equal the number of entries unless there is a reminder as this has not been catered for yet.*/
		log.debug("Entries: "+this.getAuthEntries().size()+", total in buckets: "+testCount);

		if (this.getAuthEntries().size()!=testCount)log.error("Ah! Curse your sudden but inevitable betrayal!, Potential statistical error in countEntryPerInterval, total frequency does not match total entries");

		/* now do something with the reminder */

	}

	private DateTime startingTime(){
		DateTime start=null;
		for (Entry entry : this.getAuthEntries() ){
			if (start ==null)start = entry.getEventTime();
			if (entry.getEventTime().isBefore(start))start = entry.getEventTime();
		}
		return start;
	}

	private DateTime endingTime(){
		DateTime end=null;
		for (Entry entry : this.getAuthEntries() ){
			if (end ==null)end = entry.getEventTime();
			//log.debug("end "+end+" current "+entry.getEventTime()+" after :"+entry.getEventTime().isAfter(end));
			if (entry.getEventTime().isAfter(end))end = entry.getEventTime();
		}
		return end;
	}







}
