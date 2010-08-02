/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.ReflectionUtils;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;
import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;

/**
 * @author philsmart
 *
 */
public class AuthenticationStatistic extends Statistic{
	static Logger log = Logger.getLogger(AuthenticationStatistic.class);



	public AggregatorGraphModel countEntryPerInterval(String timeInterval) throws StatisticalUnitException{
		log.debug("Performing countEntryPerInterval Statistical Operation");
		int timeIntervalInt = Integer.parseInt(timeInterval);
		log.debug("Params for method:  "+this.getField()+", "+this.getMethodName()+", "+this.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Working off "+this.getAuthEntries().size()+" entries");

		/* stop processing if there are no valid entries */
		if (this.getAuthEntries()==null || this.getAuthEntries().size()==0){
			log.error("Not enough entries to perform statistic countEntryPerInterval");
			return null;
		}

		/* divide the temporal extent into evenly sized buckets*/
		DateTime start = startingTime();
		DateTime end = endingTime();

		long difference = end.getMillis() - start.getMillis();
		log.debug("There is "+difference+"ms difference between start and end entries");
		int numberOfBuckets = (int) (difference / timeIntervalInt);
		long reminder = difference % timeIntervalInt;
		log.debug("There are "+numberOfBuckets+" buckets, with reminder "+reminder+"ms");

		/* now create that many buckets of length timeIntervalInt*/
		Bucket[] buckets = null;
		if (reminder >0)buckets = new Bucket[numberOfBuckets+1]; //add 1 for the reminder
		else buckets = new Bucket[numberOfBuckets];

		buckets[0] = new Bucket();
		buckets[0].setStart(start);
		buckets[0].setEnd(new DateTime(start.getMillis()+timeIntervalInt));
		DateTime endOfEvenBuckets = null;
		for (int i=1 ; i < numberOfBuckets; i++){
			Bucket buck = new Bucket();
			buck.setStart(buckets[i-1].getEnd());
			buck.setEnd(new DateTime(buckets[i-1].getEnd().getMillis()+timeIntervalInt));
			buckets[i] = buck;
			endOfEvenBuckets = buck.getEnd();
		}

		if (reminder >0){
			/* now do something with the reminder, create a bucket from the last entry, to the maximum temporal extent of all entries */
			Bucket reminderBucket = new Bucket();
			reminderBucket.setStart(endOfEvenBuckets);
			reminderBucket.setEnd(end);
			buckets[buckets.length-1] = reminderBucket;
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



		/* now construct the GraphModel */
		AggregatorGraphModel gmodel = new AggregatorGraphModel();
		gmodel.addSeriesLabel("Number of Events per "+timeInterval+"ms");
		DateTimeFormatter startParser = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		DateTimeFormatter endParser = DateTimeFormat.forPattern("HH:mm");
		for (Bucket bucket : buckets){
			gmodel.addGroupLabel(startParser.print(bucket.getStart())+"-"+endParser.print(bucket.getEnd()));
			List<Double> values = new ArrayList();
			Double valueDouble = new Double(bucket.getFrequency());
			values.add(valueDouble);
			gmodel.addGroupValue(values);
		}

		return gmodel;

	}

	public AggregatorGraphModel groupByFrequency(String groupByField) throws StatisticalUnitException{
		log.debug("Performing groupByFrequency Statistical Operation");

		log.debug("Params for method:  "+this.getField()+", "+this.getMethodName()+", "+this.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Working off "+this.getAuthEntries().size()+" entries");

		/* stop processing if there are no valid entries */
		if (this.getAuthEntries()==null || this.getAuthEntries().size()==0){
			log.error("Not enough entries to perform statistic groupByFrequency");
			return null;
		}

		/* create groups based on unique entries for the groupByField attribute*/
		ArrayList<Group> groups = new ArrayList();
		for (Entry entry : this.getAuthEntries()){
			boolean oldGroup = false;
			//String methodName = ReflectionHelper.prepareMethodNameGet(groupByField);
			Object result = ReflectionHelper.getValueFromObject(groupByField, entry);
			for (Group group : groups){
				if (result instanceof String){
					if (group.getGroupName().equals((String)result)){
						group.increment();
						oldGroup =true;
					}
				}
			}
			if (!oldGroup){
				Group group = new Group();
				group.setFrequency(1);
				group.setGroupName((String)result);
				groups.add(group);
			}
		}


		/* test the accuracy of the result */
		int testCount = 0;
		for (Group group : groups){
			testCount +=group.getFrequency();
			log.debug(group.getGroupName()+" --> "+group.getFrequency());
		}
		/* test count should equal the number of entries unless there is a reminder as this has not been catered for yet.*/
		log.debug("Entries: "+this.getAuthEntries().size()+", total in buckets: "+testCount);

		if (this.getAuthEntries().size()!=testCount)log.error("Ah! Curse your sudden but inevitable betrayal!, Potential statistical error in countEntryPerInterval, total frequency does not match total entries");


		/* now construct the GraphModel */
		AggregatorGraphModel gmodel = new AggregatorGraphModel();
		gmodel.addSeriesLabel("Number of Events Grouped By "+groupByField);
		for (Group group : groups){
			gmodel.addGroupLabel(group.getGroupName());
			List<Double> values = new ArrayList();
			Double valueDouble = new Double(group.getFrequency());
			values.add(valueDouble);
			gmodel.addGroupValue(values);
		}

		return gmodel;

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
