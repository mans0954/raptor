/**
 *
 */
package uk.ac.cardiff.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.sql.SQLFilter;
import uk.ac.cardiff.model.wsmodel.MethodParameter;

/**
 * @author philsmart
 *
 */
public class StatisticParameters implements Serializable{

    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 9135524508369102248L;

    static Logger log = LoggerFactory.getLogger(StatisticParameters.class);

    private static final String outputDateFormat ="dd/MM/yyyy HH:mm:ss";;

    private String unitName;

    /* List of parameters passed into the <code>performStatistic</code> method*/
    private List<MethodParameter> methodParams;

    /*
     * the start time from which to produce the starts, defining the temporal extent If a starttime and endtime is not given, the entire temporal extent of the
     * data series will be used
     */
    private DateTime startTime;

    /* the end time from which to produce the starts, defining the temporal extent */
    private DateTime endTime;

    /*Its important that these dates (used for display in trinidad) are kept in sync with the
     * Joda DateTime classes above*/
    private Date startTimeJava;
    private Date endTimeJava;

    /* either system or user types */
    public enum StatisticType {
	/*
	 * A statistic for use by the USER. Possibly removed in the future, in favour of individual
	 * user based statistics
	 */ 
	 USER,
	 /*
	  * A statistic for use by the system level processes, e.g. to generate dashboard 
	  * statistics
	  */
	 SYSTEM};
	 
    private StatisticType type;


    /* Configures the parameters used to view a graphical representation*/
    private Presentation presentation;

    /* A series to plot on the graph*/
     private List<Series> series;

     public StatisticParameters(){
	 presentation = new Presentation();
	 presentation.setGraphTitle("");
	 presentation.setxAxisLabel("");
	 presentation.setyAxisLabel("");
	 setSeries(new ArrayList<Series>());
     }

    public void setUnitName(String unitName) {
	this.unitName = unitName;
    }

    public String getUnitName() {
	return unitName;
    }


    public void setMethodParams(List<MethodParameter> methodParams) {
	this.methodParams = methodParams;
    }

    public List<MethodParameter> getMethodParams() {
	return methodParams;
    }

    /**
     * This method should be used as the primary method for setting the startTime on
     * this class
     * @param startTime
     */
    public void setStartTime(DateTime startTime) {
	this.startTime = startTime;
	this.startTimeJava = new Date (this.startTime.getMillis());
    }

    public DateTime getStartTimeAsDate() {
	log.debug("Returning start time as {}",startTime);
	return startTime;
    }

    /**
     * This method should be used as the primary method for setting the endTime on
     * this class
     * @param endTime
     */
    public void setEndTime(DateTime endTime) {
	this.endTime = endTime;
    }

    public DateTime getEndTimeAsDate() {
	return endTime;
    }

    public void setStartTime(String startTime) {
	this.startTime = formatDate(startTime, false);
	this.startTimeJava = new Date (this.startTime.getMillis());
	
    }

    public String getStartTime() {
	if (startTime!=null)return startTime.toString(outputDateFormat);
	return null;
    }

    public void setEndTime(String endTime) {
	this.endTime = formatDate(endTime, true);
	this.endTimeJava = new Date (this.endTime.getMillis());
    }

    public String getEndTime() {
	if (endTime!=null)return endTime.toString(outputDateFormat);
	return null;
    }


    /**
     * for output on the web page (just a date that can be rendered by trinidad)
     * @return
     */
    public Date getEndTimeAsJavaDate(){
	return endTimeJava;

    }

    /**
     * for output on the web page 
     * @param date
     */
    public Date getStartTimeAsJavaDate(){
	return startTimeJava;
    }


    public void setEndTimeAsJavaDate(Date date){
	this.endTimeJava = date;
	this.endTime = new DateTime(date);
    }

    public void setStartTimeAsJavaDate(Date date){
	this.startTimeJava = date;
	this.startTime = new DateTime(date);
    }

    /* human consumable outputs */

    public String getStartTimeAsFormattedString() {
	DateTimeFormatter dtf = DateTimeFormat.forPattern(outputDateFormat);
	if (getStartTime() != null)
	    return getStartTimeAsDate().toString(dtf);
	else
	    return "First Entry";
    }

    public String getEndTimeAsFormattedString() {
	DateTimeFormatter dtf = DateTimeFormat.forPattern(outputDateFormat);
	if (getEndTime() != null)
	    return getEndTimeAsDate().toString(dtf);
	else
	    return "Last Entry";
    }


    /**
     * <p>
     * </p>
     *
     * @param date
     * @param isEndTime- if is endTime and only ddMMyyyy is given, then the endTime should be 23.59 as opposed to 00.00 as this is the end of the day
     * @return
     */
    private DateTime formatDate(String date, boolean isEndTime) {
	/* this is not a nice hack, please tidy */
	log.debug("Date format being parsed {} with {} characters",date,date.length());
	if (date.length() == 8) {
	    // assume ddMMyyy
	    String format = "ddMMyyyy";
	    DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
	    DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
	    if (isEndTime) {
		dt = new DateTime(dt.getMillis() + 86340000); // where 86340000 is 23.59 hours
	    }
	    log.debug("time set to " + dt.getDayOfMonth() + "th " + dt.getMonthOfYear() + " " + dt.getYear() + " " + dt.getHourOfDay() + ":" + dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute() + " for " + getUnitName());
	    return dt;
	} else if (date.length() == 15) {
	    // assume yyyyMMdd'T'HHmmss
	    String format = "yyyyMMdd'T'HHmmss";
	    DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
	    DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
	    log.debug("time set to " + dt.getDayOfMonth() + "th " + dt.getMonthOfYear() + " " + dt.getYear() + " " + dt.getHourOfDay() + ":" + dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute() + " for " + getUnitName());
	    return dt;
	} else if (date.length()==19){
	    // assume outputDateFormat
	    String format = outputDateFormat;
	    DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
	    DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
	    log.debug("time set to " + dt.getDayOfMonth() + "th " + dt.getMonthOfYear() + " " + dt.getYear() + " " + dt.getHourOfDay() + ":" + dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute() + " for " + getUnitName());
	    return dt;
	}
	//which is instantiated with the current date and time
	return new DateTime();
    }

    /** methods to get and set the minutes seconds and hours of end and start time (for the web interface)*/

    public void setEndTimeHour(int hour){
	DateTime newTime = new DateTime(endTime.getYear(),endTime.getMonthOfYear(),endTime.getDayOfMonth(),
		hour, endTime.getMinuteOfHour(), endTime.getSecondOfMinute(), endTime.getMillisOfSecond());
	endTime = newTime;
	endTimeJava = new Date(endTime.getMillis());
    }
    public void setEndTimeMinute(int minutes){
	DateTime newTime = new DateTime(endTime.getYear(),endTime.getMonthOfYear(),endTime.getDayOfMonth(),
		endTime.getHourOfDay(), minutes, endTime.getSecondOfMinute(), endTime.getMillisOfSecond());
	endTime = newTime;
	endTimeJava = new Date(endTime.getMillis());
    }
    public void setEndTimeSecond(int seconds){
	DateTime newTime = new DateTime(endTime.getYear(),endTime.getMonthOfYear(),endTime.getDayOfMonth(),
		endTime.getHourOfDay(), endTime.getMinuteOfHour(), seconds, endTime.getMillisOfSecond());
	endTime = newTime;
	endTimeJava = new Date(endTime.getMillis());
    }
    public int getEndTimeHour(){
	if (endTime!=null)
	    return endTime.getHourOfDay();
	return 0;
    }
    public int getEndTimeMinute(){
	if (endTime!=null)
	    return endTime.getMinuteOfHour();
	return 0;
    }
    public int getEndTimeSecond(){
	if (endTime!=null)
	    return endTime.getSecondOfMinute();
	return 0;
    }

    public void setStartTimeHour(int hour){
	DateTime newTime = new DateTime(startTime.getYear(),startTime.getMonthOfYear(),startTime.getDayOfMonth(),
		hour, startTime.getMinuteOfHour(), startTime.getSecondOfMinute(), startTime.getMillisOfSecond());
	startTime = newTime;
	startTimeJava = new Date(startTime.getMillis());
    }
    public void setStartTimeMinute(int minutes){
	DateTime newTime = new DateTime(startTime.getYear(),startTime.getMonthOfYear(),startTime.getDayOfMonth(),
		startTime.getHourOfDay(), minutes, startTime.getSecondOfMinute(), startTime.getMillisOfSecond());
	startTime = newTime;
	startTimeJava = new Date(startTime.getMillis());
    }
    public void setStartTimeSecond(int seconds){
	DateTime newTime = new DateTime(startTime.getYear(),startTime.getMonthOfYear(),startTime.getDayOfMonth(),
		startTime.getHourOfDay(), startTime.getMinuteOfHour(), seconds, startTime.getMillisOfSecond());
	startTime = newTime;
	startTimeJava = new Date(startTime.getMillis());
    }
    public int getStartTimeHour(){
	if (startTime!=null)
	    return startTime.getHourOfDay();
	return 0;
    }
    public int getStartTimeMinute(){
	if (startTime!=null)
	    return startTime.getMinuteOfHour();
	return 0;
    }
    public int getStartTimeSecond(){
	if (startTime!=null)
	    return startTime.getSecondOfMinute();
	return 0;
    }

    public void setStatisticType(String type) {
	type = type.toUpperCase();
	for (StatisticType thisType : StatisticType.values()){
	    if (thisType.toString().equals(type)){
		this.type=thisType;
		return;
	    }
	}
	//set default
	this.type = StatisticType.USER;
    }

    public void setType(StatisticType type) {
	this.type = type;
    }

    public StatisticType getType() {
	return type;
    }

    public void setPresentation(Presentation presentation) {
	this.presentation = presentation;
    }

    public Presentation getPresentation() {
	return presentation;
    }

    public void setSeries(List<Series> series) {
	this.series = series;
    }

    public List<Series> getSeries() {
	return series;
    }




}
