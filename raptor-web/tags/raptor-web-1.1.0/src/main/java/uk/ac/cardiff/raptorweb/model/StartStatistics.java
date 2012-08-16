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
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.StatisticParameters.ResourceCategory;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet.TimeRange;

public class StartStatistics implements Serializable {

    /** Generates serial UID */
    private static final long serialVersionUID = 5877084192204850929L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(StartStatistics.class);

    /**
     * The resource category this start statistics has been computed for.
     */
    private ResourceCategory resourceCategory;

    private double numberOfAuthenticationsPer;
    private double numberOfUniqueAuthenticationsPer;
    private RaptorTableChartModel topFiveResouces;
    private RaptorJFreeChartModel headlineGraph;
    private RaptorTableChartModel topFiveUniqueUsersPerSP;

    public enum StartStatisticType {
        /** Number of authentications */
        NO_AUTHS,
        /** Number of unique authentications */
        NO_UNIQUE_AUTHS,
        /** Top Five resources by no. of authentications */
        TOP_FIVE,
        /** The number of authentications over a given time period */
        HEADLINE_GRAPH,
        /** Top five resouces by no. of unique users */
        TOP_FIVE_UNIQUE
    }

    private String computedForClassType;

    /** the time at which these values were computed. */
    private DateTime accurateOf;

    /** The qualitative description of the period this statistic is computed for. */
    private TimeRange timeRange;

    public StartStatistics() {
        topFiveResouces = null;
    }

    public void setNumberOfAuthenticationsPer(double numberOfAuthenticationsPer) {
        this.numberOfAuthenticationsPer = numberOfAuthenticationsPer;
    }

    public double getNumberOfAuthenticationsPer() {
        return numberOfAuthenticationsPer;
    }

    public String getNumberOfAuthenticationsPerFormatted() {
        return formatDoubleWithCommas(numberOfAuthenticationsPer);
    }

    private String formatDoubleWithCommas(double number) {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        String formattedNumber = df.format((int) number);
        return formattedNumber;
    }

    public void setNumberOfUniqueAuthenticationsPer(double numberOfUniqueAuthenticationsPer) {
        this.numberOfUniqueAuthenticationsPer = numberOfUniqueAuthenticationsPer;
    }

    public double getNumberOfUniqueAuthenticationsPer() {
        return numberOfUniqueAuthenticationsPer;
    }

    public String getNumberOfUniqueAuthenticationsPerFormatted() {
        return formatDoubleWithCommas(numberOfUniqueAuthenticationsPer);
    }

    public void setTopFiveResouces(RaptorTableChartModel topFiveResouces) {
        this.topFiveResouces = topFiveResouces;
    }

    public RaptorTableChartModel getTopFiveResouces() {
        return topFiveResouces;
    }

    public void setHeadlineGraph(RaptorJFreeChartModel headlineGraph) {
        this.headlineGraph = headlineGraph;
    }

    public RaptorJFreeChartModel getHeadlineGraph() {
        return headlineGraph;
    }

    public void setAccurateOf(DateTime accurateOf) {
        this.accurateOf = accurateOf;
    }

    public DateTime getAccurateOf() {
        return accurateOf;
    }

    public String getAccurateOfFormatted() {
        if (accurateOf != null)
            return accurateOf.toString("dd/MM/yyyy HH:mm:ss");
        return "not available";
    }

    public void setTopFiveUniqueUsersPerSP(RaptorTableChartModel topFiveUniqueUsersPerSP) {
        this.topFiveUniqueUsersPerSP = topFiveUniqueUsersPerSP;
    }

    public RaptorTableChartModel getTopFiveUniqueUsersPerSP() {
        return topFiveUniqueUsersPerSP;
    }

    /**
     * @param computedForClassType
     *            the computedForClassType to set
     */
    public void setComputedForClassType(String computedForClassType) {
        this.computedForClassType = computedForClassType;
    }

    /**
     * @return the computedForClassType
     */
    public String getComputedForClassType() {
        return computedForClassType;
    }

    /**
     * @param timeRange
     *            the timeRange to set
     */
    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * @return the timeRange
     */
    public TimeRange getTimeRange() {
        return timeRange;
    }

    /**
     * @return Returns the resourceCategory.
     */
    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    /**
     * @param resourceCategory The resourceCategory to set.
     */
    public void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

}
