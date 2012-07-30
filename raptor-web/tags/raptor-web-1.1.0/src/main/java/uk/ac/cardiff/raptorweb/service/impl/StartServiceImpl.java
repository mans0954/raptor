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
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.ResourceCategory;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.CachedStartStatistics;
import uk.ac.cardiff.raptorweb.model.CurrentTimeRanges;
import uk.ac.cardiff.raptorweb.model.NoSuchTimeRangeException;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.StartStatistics;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.model.dashboard.AbstractDashboardStatistic;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticException;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet;
import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet.TimeRange;
import uk.ac.cardiff.raptorweb.service.StartService;

/**
 * @author philsmart
 * 
 */
public class StartServiceImpl implements StartService {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(StartServiceImpl.class);

    /** The engine that is delegated to for all common internal functions of RaptorWeb */
    private RaptorWebEngine webEngine;

    /** A <code>List</code> of all the dashboard statistic sets to compute */
    private List<DashboardStatisticsSet> dashboardStatisticSets;

    /** Enumeration of all possible statistical types supported by the attached MUA. */
    private List<StartStatistics.StartStatisticType> muaSupportedStatisticalTypes;

    /** holds the statistics for the front page gathered from the background worker thread */
    private CachedStartStatistics cachedStartStatistics;

    public StartServiceImpl() {
        cachedStartStatistics = new CachedStartStatistics();
    }

    public void generateStatisticsBackground() {
        log.info("Generating background statistics for the start page, using {}", this);

        CurrentTimeRanges timeRanges = new CurrentTimeRanges();

        List<StartStatistics> computedStatistics = new ArrayList<StartStatistics>();

        List<StatisticalUnitInformation> statisticalUnits = getStatisticalUnits();

        for (DashboardStatisticsSet set : dashboardStatisticSets) {
            StartStatistics statisticalSet = new StartStatistics();

            for (TimeRange period : set.getComputedOverTimeRanges()) {
                for (ResourceCategory category : set.getResourceCategorys()) {
                    for (AbstractDashboardStatistic dashboardStatistic : set.getDashboardStatistics()) {
                        if (dashboardStatistic.isEnabled()) {
                            try {

                                statisticalSet.setComputedForClassType(dashboardStatistic.getEventType());
                                statisticalSet.setTimeRange(period);
                                DateTime start = timeRanges.getStartTime(period);
                                DateTime end = timeRanges.getEndTime(period);

                                log.info("Computing start statistic type [{}-{}], resource type [{}], period [{} - [{}, {}]]",
                                        new Object[] { dashboardStatistic.getEventType(), dashboardStatistic.getStatisticalUnitName(), category, period, start, end });

                                for (StatisticalUnitInformation unit : statisticalUnits) {
                                    if (unit.getStatisticParameters().getType() == StatisticParameters.StatisticType.SYSTEM) {
                                        if (unit.getStatisticParameters().getUnitName().equals(dashboardStatistic.getStatisticalUnitName())) {
                                            unit.getStatisticParameters().setEndTime(end);
                                            unit.getStatisticParameters().setStartTime(start);
                                            unit.getStatisticParameters().setEventType(dashboardStatistic.getEventType());
                                            unit.getStatisticParameters().setResourceCategory(category);
                                            log.debug("Invoking statistic {} for the dasboard page", unit.getStatisticParameters().getUnitName());
                                            AggregatorGraphModel model = webEngine.updateAndInvokeStatisticalUnit(unit);
                                            try {
                                                Object result = dashboardStatistic.getProcessedStatistic(model, unit);
                                                cacheStatistic(statisticalSet, result, dashboardStatistic.getDashboardStatisticType());
                                                // computed++;
                                            } catch (DashboardStatisticException e) {
                                                log.warn("Dashboard statistic failed to process statistic, {}", e.getMessage());
                                            } catch (ClassCastException e) {
                                                log.error("Unable to set cached statistic {}", e.getMessage());
                                            }
                                        }
                                    }
                                }

                            } catch (NoSuchTimeRangeException e) {
                                log.error("Could not compute start statistic", e);
                            }
                        }
                    }
                }
            }
            statisticalSet.setAccurateOf(new DateTime(System.currentTimeMillis()));
            computedStatistics.add(statisticalSet);
        }
        cachedStartStatistics.setCached(computedStatistics);

        log.info("Generating background statistics for the start page...done");
    }

    /**
     * Gets the start statistic applicable for the selected time period and event type.
     */
    @Override
    public void generateStatistics(WebSession websession) {
        log.debug("Getting start statistics for {} from {}", websession.getStartmodel().getStatsRangeSelector(), websession.getStartmodel().getEventType());

        StartStatistics statistic = cachedStartStatistics.getStartstatistics(websession.getStartmodel().getStatsRangeSelector(), websession.getStartmodel().getEventType());

        websession.getStartmodel().setStartStatistics(statistic);

        // so we could output the name of the attached MUA
        Capabilities capabilities = getAttachedCapabilities();
        if (capabilities != null) {
            websession.getStartmodel().setAttachedMUACapabilities(capabilities);

        }
    }

    private void cacheStatistic(StartStatistics startstats, Object result, StartStatistics.StartStatisticType statisticType) throws ClassCastException {
        log.debug("Setting cached start statistic, for type {}", statisticType);

        if (statisticType == null) {
            log.error("StatisticType not set for this statistic");
            return;
        }

        switch (statisticType) {
            case NO_AUTHS:
                startstats.setNumberOfAuthenticationsPer(((Double) result).doubleValue());
                break;

            case NO_UNIQUE_AUTHS:
                startstats.setNumberOfUniqueAuthenticationsPer(((Double) result).doubleValue());
                break;

            case TOP_FIVE:
                startstats.setTopFiveResouces((RaptorTableChartModel) result);
                break;

            case HEADLINE_GRAPH:
                startstats.setHeadlineGraph((RaptorJFreeChartModel) result);
                break;

            case TOP_FIVE_UNIQUE:
                startstats.setTopFiveUniqueUsersPerSP((RaptorTableChartModel) result);
                break;
            default:
                log.error("Statistic could not be cached, probably the wrong statisticType set");
        }

    }

    private List<StatisticalUnitInformation> getStatisticalUnits() {
        return webEngine.getStatisticalUnits();
    }

    public void setWebEngine(RaptorWebEngine webEngine) {
        this.webEngine = webEngine;
    }

    public RaptorWebEngine getWebEngine() {
        return webEngine;
    }

    public Capabilities getAttachedCapabilities() {
        return webEngine.getAttachedCapabilities();
    }

    /**
     * @param muaSupportedStatisticalTypes
     *            the muaSupportedStatisticalTypes to set
     */
    public void setMuaSupportedStatisticalTypes(List<StartStatistics.StartStatisticType> muaSupportedStatisticalTypes) {
        this.muaSupportedStatisticalTypes = muaSupportedStatisticalTypes;
    }

    /**
     * @return the muaSupportedStatisticalTypes
     */
    public List<StartStatistics.StartStatisticType> getMuaSupportedStatisticalTypes() {
        return muaSupportedStatisticalTypes;
    }

    /**
     * @param cachedStartStatistics
     *            the cachedStartStatistics to set
     */
    public void setCachedStartStatistics(CachedStartStatistics cachedStartStatistics) {
        this.cachedStartStatistics = cachedStartStatistics;
    }

    /**
     * @return the cachedStartStatistics
     */
    public CachedStartStatistics getCachedStartStatistics() {
        return cachedStartStatistics;
    }

    /**
     * @param dashboardStatisticSets
     *            the dashboardStatisticSets to set
     */
    public void setDashboardStatisticSets(List<DashboardStatisticsSet> dashboardStatisticSets) {
        this.dashboardStatisticSets = dashboardStatisticSets;
    }

    /**
     * @return the dashboardStatisticSets
     */
    public List<DashboardStatisticsSet> getDashboardStatisticSets() {
        return dashboardStatisticSets;
    }

}
