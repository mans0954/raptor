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

package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextAwareStatisticRegistry extends AbstractStatisticRegistry implements ApplicationContextAware,
        InitializingBean {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(ContextAwareStatisticRegistry.class);

    /**
     * Whether to automatically find and load <code>Statistic</code>s from spring's application context. If set to true,
     * statistics can not be added to the <code>Set</code> of <code>statisticalUnits</code> via the
     * <code>setStatisticalUnitsFromApplicationContext</code> setter method.
     **/
    private boolean automaticallyFindStatsiticsToLoad = false;

    /** Used to automatically add statistics from the application context iff automaticallyFindStatsiticsToLoad = true */
    private ApplicationContext applicationContext;

    /**
     * Calls to constructs the list of <code>statisticalUnits</code> automatically if the
     * automaticallyFindStatsiticsToLoad has been set to true
     */
    public void afterPropertiesSet() throws Exception {
        if (automaticallyFindStatsiticsToLoad == true) {
            log.info("Statistic registry has been asked to register the set of statistics automatically from the application context");
            setStatisticalUnitsFromApplicationContext();
        }
    }

    /**
     * Sets the list of statistical units from the <code>statisticalUnits</code> parameter. If
     * <code>automaticallyFindStatsiticsToLoad</code> has been set to true, then the statistical units sets by this
     * method will be overwritten by those automatically discovered using the
     * <code>setStatisticalUnitsFromApplicationContext</code> method
     */
    public void setStatisticalUnits(List<BaseStatistic> statisticalUnits) {
        for (BaseStatistic stat : statisticalUnits) {
            log.info("Registering statistic [{}], role {}", stat.getStatisticParameters().getUnitName(), stat
                    .getStatisticParameters().getType());
        }
        this.statisticalUnits = statisticalUnits;

    }

    public void setStatisticalUnitsFromApplicationContext() {
        statisticalUnits = new ArrayList<BaseStatistic>();
        Map<String, ?> allStatisticBeans = applicationContext.getBeansOfType(BaseStatistic.class);
        for (Map.Entry<String, ?> entry : allStatisticBeans.entrySet()) {
            BaseStatistic statistic = (BaseStatistic) entry.getValue();
            log.debug("Registering statistic [{}], role {}", statistic.getStatisticParameters().getUnitName(),
                    statistic.getStatisticParameters().getType());
            statistic.setPostprocessor(initialisePostProcessors(statistic.getAttachProcessors()));
            statisticalUnits.add(statistic);
        }
    }

    public List<BaseStatistic> getStatisticalUnits() {
        return statisticalUnits;
    }

    /**
     * @param automaticallyFindStatsiticsToLoad the automaticallyFindStatsiticsToLoad to set
     */
    public void setAutomaticallyFindStatsiticsToLoad(boolean automaticallyFindStatsiticsToLoad) {
        this.automaticallyFindStatsiticsToLoad = automaticallyFindStatsiticsToLoad;
    }

    /**
     * @return the automaticallyFindStatsiticsToLoad
     */
    public boolean isAutomaticallyFindStatsiticsToLoad() {
        return automaticallyFindStatsiticsToLoad;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;

    }

}
