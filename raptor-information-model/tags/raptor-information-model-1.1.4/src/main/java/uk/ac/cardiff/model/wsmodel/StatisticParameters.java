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
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.Presentation;
import uk.ac.cardiff.model.report.Series;

/**
 * @author philsmart
 * 
 */
public class StatisticParameters implements Serializable {

    /** generated serial ID */
    private static final long serialVersionUID = 9135524508369102248L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(StatisticParameters.class);

    /**
     * a constant date format used to convert start and end times into a formant suitable for human consumption.
     */
    private static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";;

    /**
     * The name of the statistic unit this {@link uk.ac.cardiff.model.wsmodel.StatisticParameters} is recording information about.
     */
    private String unitName;

    /** List of parameters passed into the <code>performStatistic</code> method */
    private List<MethodParameter> methodParameters;

    /**
     * the start time from which to produce the starts, defining the temporal extent If a starttime and endtime is not given, the entire temporal extent of the
     * data series will be used
     */
    private DateTime startTime;

    /** the end time from which to produce the starts, defining the temporal extent */
    private DateTime endTime;

    /**
     * 
     * The start date as a Java Date object. Its important that these dates (used for display in the view) are kept in sync with the Joda DateTime classes
     * above. Used for remoting
     */
    private Date startTimeJava;

    /**
     * The end date as a Java Date objects. Its important that these dates (used for display in the view) are kept in sync with the Joda DateTime classes above.
     * Used for remoting
     */
    private Date endTimeJava;

    public enum ResourceCategory {
        /** For resources that are internal to the organisation */
        INTERNAL(new int[] { 1 }),
        /** For resources that are external to the organisation */
        EXTERNAL(new int[] { 2 }),
        /** For all resource categories */
        ALL(new int[] { 1, 2 });

        private int[] resourceIdCategory;

        ResourceCategory(int[] resourceIdCategory) {
            this.resourceIdCategory = resourceIdCategory;
        }

        /**
         * @return the resourceIdCategory
         */
        public int[] getResourceIdCategory() {
            return resourceIdCategory;
        }

        /**
         * Returns a <code>String</code> value that represents an IN SQL operator which specifies the resourceIdCategory(s) for this
         * <code>ResourceCategory</code> for use in SQL WHERE clauses
         * 
         * @return an SQL IN clause
         */
        public String getSql() {
            StringBuilder sb = new StringBuilder();
            sb.append("in (");
            for (int i = 0; i < resourceIdCategory.length; i++) {
                sb.append(resourceIdCategory[i]);
                if (i < resourceIdCategory.length - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
    }

    private ResourceCategory resourceCategory;

    /** either system or user types */
    public enum StatisticType {
        /**
         * A statistic for use by the USER. Possibly removed in the future, in favour of individual user based statistics
         */
        USER,
        /**
         * A statistic for use by the system level processes, e.g. to generate dashboard statistics
         */
        SYSTEM
    };

    /** The type of statistic */
    private StatisticType type;

    /** The type of event (canonical class name) this statistic is computed for */
    private String eventType;

    /** Configures the parameters used to view a graphical representation */
    private Presentation presentation;

    /** A series to plot on the graph */
    private List<Series> series;

    /** Default constructor. */
    public StatisticParameters() {
        presentation = new Presentation();
        presentation.setGraphTitle("");
        presentation.setxAxisLabel("");
        presentation.setyAxisLabel("");
        setSeries(new ArrayList<Series>());
        resourceCategory = ResourceCategory.ALL;
    }

    /**
     * Copy Constructor.
     * 
     * @param statisticParameters
     */
    public StatisticParameters(StatisticParameters statisticParameters) {
        super();
        if (statisticParameters != null) {
            eventType = statisticParameters.eventType;
            if (statisticParameters.type != null) {
                type = StatisticType.valueOf(statisticParameters.type.name());
            }
            if (statisticParameters.resourceCategory != null) {
                resourceCategory = ResourceCategory.valueOf(statisticParameters.resourceCategory.name());
            }
            if (statisticParameters.presentation != null) {
                presentation = new Presentation(statisticParameters.presentation);
            }

            startTime = new DateTime(statisticParameters.startTime);
            unitName = statisticParameters.unitName;
            endTime = new DateTime(statisticParameters.endTime);
            if (statisticParameters.methodParameters != null) {
                methodParameters = new ArrayList<MethodParameter>();
                for (MethodParameter param : statisticParameters.methodParameters) {
                    MethodParameter newParam = new MethodParameter(param);
                    methodParameters.add(newParam);
                }
            }
            if (statisticParameters.series != null) {
                series = new ArrayList<Series>();
                for (Series serie : statisticParameters.series) {
                    Series newSerie = new Series(serie);
                    series.add(newSerie);
                }
            }
        }

    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setMethodParams(List<MethodParameter> methodParams) {
        this.methodParameters = methodParams;
    }

    public List<MethodParameter> getMethodParams() {

        return methodParameters;
    }

    /**
     * This method should be used as the primary method for setting the startTime on this class
     * 
     * @param startTime
     */
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
        this.startTimeJava = new Date(this.startTime.getMillis());
    }

    public DateTime getStartTimeAsDate() {
        return startTime;
    }

    /**
     * This method should be used as the primary method for setting the endTime on this class
     * 
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
        this.startTimeJava = new Date(this.startTime.getMillis());

    }

    public String getStartTime() {
        if (startTime != null)
            return startTime.toString(OUTPUT_DATE_FORMAT);
        return null;
    }

    public void setEndTime(String endTime) {
        this.endTime = formatDate(endTime, true);
        this.endTimeJava = new Date(this.endTime.getMillis());
    }

    public String getEndTime() {
        if (endTime != null)
            return endTime.toString(OUTPUT_DATE_FORMAT);
        return null;
    }

    /**
     * @return
     */
    public Date getEndTimeAsJavaDate() {
        return endTimeJava;

    }

    /**
     * for output on the web page
     * 
     * @param date
     */
    public Date getStartTimeAsJavaDate() {
        return startTimeJava;
    }

    public void setEndTimeAsJavaDate(Date date) {
        this.endTimeJava = date;
        this.endTime = new DateTime(date);
        log.trace("Setting End Time as [{}] with actual value of [{}]", date, endTime);
    }

    public void setStartTimeAsJavaDate(Date date) {
        this.startTimeJava = date;
        this.startTime = new DateTime(date);
        log.trace("Setting Start Time as [{}] with actual value of [{}]", date, startTime);
    }

    public String getStartTimeAsFormattedString() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(OUTPUT_DATE_FORMAT);
        if (getStartTime() != null)
            return getStartTimeAsDate().toString(dtf);
        else
            return "First Entry";
    }

    public String getEndTimeAsFormattedString() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(OUTPUT_DATE_FORMAT);
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
     * @param isEndTime
     *            - if is endTime and only ddMMyyyy is given, then the endTime should be 23.59 as opposed to 00.00 as this is the end of the day
     * @return
     */
    private DateTime formatDate(String date, boolean isEndTime) {
        // TODO this is not a nice hack, please tidy
        // log.debug("Date format being parsed {} with {} characters",date,date.length());
        if (date.length() == 8) {
            // assume ddMMyyy
            String format = "ddMMyyyy";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
            DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
            if (isEndTime) {
                dt = new DateTime(dt.getMillis() + 86340000); // where 86340000 is 23.59 hours
            }
            // log.debug("time set to " + dt.getDayOfMonth() + "th " + dt.getMonthOfYear() + " " + dt.getYear() + " " + dt.getHourOfDay() + ":" +
            // dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute() + " for " + getUnitName());
            return dt;
        } else if (date.length() == 15) {
            // assume yyyyMMdd'T'HHmmss
            String format = "yyyyMMdd'T'HHmmss";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
            DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
            // log.debug("time set to " + dt.getDayOfMonth() + "th " + dt.getMonthOfYear() + " " + dt.getYear() + " " + dt.getHourOfDay() + ":" +
            // dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute() + " for " + getUnitName());
            return dt;
        } else if (date.length() == 19) {
            // assume outputDateFormat
            String format = OUTPUT_DATE_FORMAT;
            DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
            DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
            // log.debug("time set to " + dt.getDayOfMonth() + "th " + dt.getMonthOfYear() + " " + dt.getYear() + " " + dt.getHourOfDay() + ":" +
            // dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute() + " for " + getUnitName());
            return dt;
        }
        // which is instantiated with the current date and time
        return new DateTime();
    }

    /** methods to get and set the minutes seconds and hours of end and start time (for the web interface) */

    public void setEndTimeHour(int hour) {
        DateTime newTime = new DateTime(endTime.getYear(), endTime.getMonthOfYear(), endTime.getDayOfMonth(), hour, endTime.getMinuteOfHour(), endTime.getSecondOfMinute(), endTime.getMillisOfSecond());
        endTime = newTime;
        endTimeJava = new Date(endTime.getMillis());
    }

    public void setEndTimeMinute(int minutes) {
        DateTime newTime = new DateTime(endTime.getYear(), endTime.getMonthOfYear(), endTime.getDayOfMonth(), endTime.getHourOfDay(), minutes, endTime.getSecondOfMinute(), endTime.getMillisOfSecond());
        endTime = newTime;
        endTimeJava = new Date(endTime.getMillis());
    }

    public void setEndTimeSecond(int seconds) {
        DateTime newTime = new DateTime(endTime.getYear(), endTime.getMonthOfYear(), endTime.getDayOfMonth(), endTime.getHourOfDay(), endTime.getMinuteOfHour(), seconds, endTime.getMillisOfSecond());
        endTime = newTime;
        endTimeJava = new Date(endTime.getMillis());
    }

    public int getEndTimeHour() {
        if (endTime != null)
            return endTime.getHourOfDay();
        return 0;
    }

    public int getEndTimeMinute() {
        if (endTime != null)
            return endTime.getMinuteOfHour();
        return 0;
    }

    public int getEndTimeSecond() {
        if (endTime != null)
            return endTime.getSecondOfMinute();
        return 0;
    }

    public void setStartTimeHour(int hour) {
        DateTime newTime = new DateTime(startTime.getYear(), startTime.getMonthOfYear(), startTime.getDayOfMonth(), hour, startTime.getMinuteOfHour(), startTime.getSecondOfMinute(),
                startTime.getMillisOfSecond());
        startTime = newTime;
        startTimeJava = new Date(startTime.getMillis());
    }

    public void setStartTimeMinute(int minutes) {
        DateTime newTime = new DateTime(startTime.getYear(), startTime.getMonthOfYear(), startTime.getDayOfMonth(), startTime.getHourOfDay(), minutes, startTime.getSecondOfMinute(),
                startTime.getMillisOfSecond());
        startTime = newTime;
        startTimeJava = new Date(startTime.getMillis());
    }

    public void setStartTimeSecond(int seconds) {
        DateTime newTime = new DateTime(startTime.getYear(), startTime.getMonthOfYear(), startTime.getDayOfMonth(), startTime.getHourOfDay(), startTime.getMinuteOfHour(), seconds,
                startTime.getMillisOfSecond());
        startTime = newTime;
        startTimeJava = new Date(startTime.getMillis());
    }

    public int getStartTimeHour() {
        if (startTime != null)
            return startTime.getHourOfDay();
        return 0;
    }

    public int getStartTimeMinute() {
        if (startTime != null)
            return startTime.getMinuteOfHour();
        return 0;
    }

    public int getStartTimeSecond() {
        if (startTime != null)
            return startTime.getSecondOfMinute();
        return 0;
    }

    public void setStatisticType(String type) {
        type = type.toUpperCase();
        for (StatisticType thisType : StatisticType.values()) {
            if (thisType.toString().equals(type)) {
                this.type = thisType;
                return;
            }
        }
        // set default
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

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeString() {
        return eventType.toString();
    }

    public String getEventType() {
        return eventType;
    }

    /**
     * @param resourceCategory
     *            the resourceCategory to set
     */
    public void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    /**
     * @return the resourceCategory
     */
    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategoryString(String resourceCategory) {
        for (ResourceCategory type : ResourceCategory.values()) {
            if (type.toString().equals(resourceCategory)) {
                this.resourceCategory = type;
            }
        }
    }

    public String getResourceCategoryString() {
        return resourceCategory.toString();
    }

}
