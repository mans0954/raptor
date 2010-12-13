/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	static Logger log = LoggerFactory.getLogger(AuthenticationStatistic.class);




	/**
	 * <p> returns false if semantic error with the entries, throws an exception on code failure </p>
	 * @param timeInterval - assumes a String representing minutes
	 * @return
	 * @throws StatisticalUnitException
	 */
	public Boolean countEntryPerInterval(String timeInterval) throws StatisticalUnitException{


		log.debug("Performing countEntryPerInterval Statistical Operation");
		int timeIntervalInt = Integer.parseInt(timeInterval);
		//convert minutes to ms for the procedure
		timeIntervalInt = timeIntervalInt * 60*1000;
		log.debug("Params for method:  "+statisticParameters.getMethodName()+", "+statisticParameters.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Working off {} entries",this.getAuthEntries().size());

		/* stop processing if there are no valid entries */
		if (this.getAuthEntries()==null || this.getAuthEntries().size()==0){
			log.error("Not enough entries to perform statistic countEntryPerInterval");
			return false;
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

		/* there may only be a reminder if the time frame specified is not big enough to fit the interval specified,
		 * in this case the endOfEvenBuckets has not been set, and it needs setting to the current starttime.
		 */
		boolean hasOnlyReminder = false;
		if (endOfEvenBuckets==null){
			hasOnlyReminder=true;
			endOfEvenBuckets = start;
		}

		if (reminder >0){
			/* now do something with the reminder, create a bucket from the last entry, to the maximum temporal extent of all entries */
			Bucket reminderBucket = new Bucket();
			reminderBucket.setStart(endOfEvenBuckets);
			if (!hasOnlyReminder)reminderBucket.setEnd(end);
			else reminderBucket.setEnd(new DateTime(start.getMillis()+end.getMillis()));
			buckets[buckets.length-1] = reminderBucket;
		}


		for (Entry entry : this.getAuthEntries()){
			for (int i=0 ; i < buckets.length; i++){
				if (buckets[i].isInside(entry.getEventTime()))buckets[i].increment();
			}
		}


		long testCount = 0;
		for (Bucket bucket : buckets){
			testCount +=bucket.getValue();
			log.debug(bucket.getStart()+"-"+bucket.getEnd()+" --> "+bucket.getValue());
		}
		/* test count should equal the number of entries unless there is a reminder, or the specified
		 * start time and endtime does not completely contain the entries.*/
		log.debug("Entries: "+this.getAuthEntries().size()+", total in buckets: "+testCount);

		if (this.getAuthEntries().size()!=testCount)log.error("Ah! Curse your sudden but inevitable betrayal!, Potential statistical error in countEntryPerInterval, total frequency does not match total entries");

		observations = buckets;

		//finished successfully, no exception thrown
		return true;

	}


	public Boolean countEntry(String numberOfIntervalsString) throws StatisticalUnitException{

		int numberOfIntervals = Integer.parseInt(numberOfIntervalsString);
		log.debug("Performing countEntry Statistical Operation");

		log.debug("Params for method:  "+statisticParameters.getMethodName()+", "+statisticParameters.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Working off "+this.getAuthEntries().size()+" entries");

		/* stop processing if there are no valid entries */
		if (this.getAuthEntries()==null || this.getAuthEntries().size()==0){
			log.error("Not enough entries to perform statistic countEntryPerInterval");
			return false;
		}

		/* divide the temporal extent into evenly sized buckets*/
		DateTime start = startingTime();
		DateTime end = endingTime();

		long difference = end.getMillis() - start.getMillis();
		log.debug("There is "+difference+"ms difference between start and end entries");
		int timeIntervalsInMs = (int) (difference / numberOfIntervals);
		long reminder = difference % numberOfIntervals;
		log.debug("There are "+numberOfIntervals+" buckets, with reminder "+reminder+"ms");


		/* now create that many buckets of length timeIntervalInt*/
		Bucket[] buckets = null;
		if (reminder >0)buckets = new Bucket[numberOfIntervals+1]; //add 1 for the reminder
		else buckets = new Bucket[numberOfIntervals];

		buckets[0] = new Bucket();
		buckets[0].setStart(start);
		buckets[0].setEnd(new DateTime(start.getMillis()+timeIntervalsInMs));
		DateTime endOfEvenBuckets = null;
		for (int i=1 ; i < numberOfIntervals; i++){
			Bucket buck = new Bucket();
			buck.setStart(buckets[i-1].getEnd());
			buck.setEnd(new DateTime(buckets[i-1].getEnd().getMillis()+timeIntervalsInMs));
			buckets[i] = buck;
			endOfEvenBuckets = buck.getEnd();
		}

		/* there may only be a reminder if the time frame specified is not big enough to fit the interval specified,
		 * in this case the endOfEvenBuckets has not been set, and it needs setting to the current starttime.
		 */
		boolean hasOnlyReminder = false;
		if (endOfEvenBuckets==null){
			hasOnlyReminder=true;
			endOfEvenBuckets = start;
		}

		if (reminder >0){
			/* now do something with the reminder, create a bucket from the last entry, to the maximum temporal extent of all entries */
			Bucket reminderBucket = new Bucket();
			reminderBucket.setStart(endOfEvenBuckets);
			if (!hasOnlyReminder)reminderBucket.setEnd(end);
			else reminderBucket.setEnd(new DateTime(start.getMillis()+end.getMillis()));
			buckets[buckets.length-1] = reminderBucket;
		}


		for (Entry entry : this.getAuthEntries()){

			for (int i=0 ; i < buckets.length; i++){
				if (buckets[i].isInside(entry.getEventTime()))buckets[i].increment();
			}
		}


		int testCount = 0;
		for (Bucket bucket : buckets){
			testCount +=bucket.getValue();
			log.debug(bucket.getStart()+"-"+bucket.getEnd()+" --> "+bucket.getValue());
		}
		/* test count should equal the number of entries unless there is a reminder, or the specified
		 * start time and endtime does not completely contain the entries.*/
		log.debug("Entries: "+this.getAuthEntries().size()+", total in buckets: "+testCount);

		if (this.getAuthEntries().size()!=testCount)log.error("Ah! Curse your sudden but inevitable betrayal!, Potential statistical error in countEntryPerInterval, total frequency does not match total entries");

		statisticParameters.setSeriesLabel("Number of Events per "+timeIntervalsInMs+"ms");

		observations = buckets;


		return true;

	}

	public Boolean groupByFrequency(String groupByField) throws StatisticalUnitException{


		log.debug("Performing groupByFrequency Statistical Operation");

		log.debug("Params for method:  "+statisticParameters.getMethodName()+", "+statisticParameters.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Working off "+this.getAuthEntries().size()+" entries");

		/* stop processing if there are no valid entries */
		if (this.getAuthEntries()==null || this.getAuthEntries().size()==0){
			log.error("Not enough entries to perform statistic groupByFrequency");
			return false;
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
				group.setValue(1);
				group.setGroupName((String)result);
				groups.add(group);
			}
		}


		/* test the accuracy of the result */
		int testCount = 0;
		for (Group group : groups){
			testCount +=group.getValue();
			log.debug(group.getGroupName()+" --> "+group.getValue());
		}
		/* test count should equal the number of entries unless there is a reminder as this has not been catered for yet.*/
		log.debug("Entries: "+this.getAuthEntries().size()+", total in buckets: "+testCount);

		if (this.getAuthEntries().size()!=testCount)log.error("Ah! Curse your sudden but inevitable betrayal!, Potential statistical error in countEntryPerInterval, total frequency does not match total entries");

		//add the series label or if none specified, add a default
		if (statisticParameters.getSeriesLabel()==null)
		    statisticParameters.setSeriesLabel("Number of Events Grouped By "+groupByField);

		observations = groups.toArray(new Group[0]);


		//finished successfully, no exception thrown
		return true;

	}

	private DateTime startingTime(){
		if (statisticParameters.getStartTimeAsDate()!=null)return statisticParameters.getStartTimeAsDate();
		DateTime start=null;
		for (Entry entry : this.getAuthEntries() ){
			if (start ==null)start = entry.getEventTime();
			if (entry.getEventTime().isBefore(start))start = entry.getEventTime();
		}
		return start;
	}

	private DateTime endingTime(){
		if (statisticParameters.getEndTimeAsDate()!=null)return statisticParameters.getEndTimeAsDate();
		DateTime end=null;
		for (Entry entry : this.getAuthEntries() ){
			if (end ==null)end = entry.getEventTime();
			//log.debug("end "+end+" current "+entry.getEventTime()+" after :"+entry.getEventTime().isAfter(end));
			if (entry.getEventTime().isAfter(end))end = entry.getEventTime();
		}
		return end;
	}



	/**
	 * <p> </p>
	 * @param date
	 * @param isEndTime - if is endTime and only ddMMyyyy is given, then the endTime should be 23.59 as opposed to 00.00 as this is the end of the day
	 * @return
	 */
	private DateTime formatDate(String date, boolean isEndTime){
		/* this is not a nice hack, please tidy*/
		log.debug("Date format being parsed "+date+" with "+date.length()+" characters");
		if (date.length()==8){
			//assume ddMMyyy
			 String format = "ddMMyyyy";
			 DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			 DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
			 if (isEndTime){
				 dt = new DateTime(dt.getMillis()+86340000); //where 86340000 is 23.59 hours
			 }
			 log.debug("time set to "+dt.getDayOfMonth()+"th "+dt.getMonthOfYear()+" "+dt.getYear()+" "+dt.getHourOfDay()+":"+dt.getMinuteOfHour()+":"+dt.getSecondOfMinute()+" for "+statisticParameters.getUnitName());
			 return dt;
		}
		else if(date.length()==15){
			//assume yyyyMMdd'T'HHmmss
			String format = "yyyyMMdd'T'HHmmss";
			 DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			 DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
			 log.debug("time set to "+dt.getDayOfMonth()+"th "+dt.getMonthOfYear()+" "+dt.getYear()+" "+dt.getHourOfDay()+":"+dt.getMinuteOfHour()+":"+dt.getSecondOfMinute()+" for "+statisticParameters.getUnitName());
			return dt;
		}
		return new DateTime();
	}





}
