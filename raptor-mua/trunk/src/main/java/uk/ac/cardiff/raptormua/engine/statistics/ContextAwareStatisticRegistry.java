
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

import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

public class ContextAwareStatisticRegistry implements StatisticRegistry, ApplicationContextAware, InitializingBean {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(ContextAwareStatisticRegistry.class);

    /**
     * Whether to automatically find and load <code>Statistic</code>s from spring's application context. If set to true,
     * statistics can not be added to the <code>Set</code> of <code>statisticalUnits</code> via the
     * <code>setStatisticalUnitsFromApplicationContext</code> setter method.
     **/
    private boolean automaticallyFindStatsiticsToLoad = false;

    /**
     * List of {@link uk.ac.cardiff.raptormua.engine.statistics.Statistic} that have been registered with this handler
     */
    private List<Statistic> statisticalUnits;

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

    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
        Statistic toUpdate = null;
        for (Statistic statistic : statisticalUnits) {
            if (statistic.getStatisticParameters().getUnitName()
                    .equals(statisticalUnitInformation.getStatisticParameters().getUnitName()))
                toUpdate = statistic;
        }
        log.debug("Found statistic [{}] to update", toUpdate.getStatisticParameters().getUnitName());
        performUpdate(toUpdate, statisticalUnitInformation);
        log.debug("Finished updating statistic [{}]", toUpdate.getStatisticParameters().getUnitName());

    }

    public Statistic getStatistic(String statisticName) {
        for (Statistic statistic : statisticalUnits) {
            if (statistic.getStatisticParameters().getUnitName().equals(statisticName)) {
                log.debug("Found statistic [{}] from statistic registry", statistic.getStatisticParameters()
                        .getUnitName());
                return statistic;
            }
        }
        return null;
    }

    /**
     * Updates the statistical parameters of the passed statistic, does not yet handle the post processors
     * 
     * @param statistic - the statistic to update
     * @param statisticalUnitInformation - the statistical unit information to used update the <code>statistic</code>
     */
    private void performUpdate(Statistic statistic, StatisticalUnitInformation statisticalUnitInformation) {
        statistic.setStatisticParameters(statisticalUnitInformation.getStatisticParameters());
        // now deal with the post processors
        List<StatisticPostProcessor> postProcessors =
                initialisePostProcessors(statisticalUnitInformation.getPostprocessors());
        statistic.setPostprocessor(postProcessors);
    }

    /**
     * 
     * @param postProcessorsInformation
     * @return
     */
    private List<StatisticPostProcessor> initialisePostProcessors(List<ProcessorInformation> postProcessorsInformation) {
        // TODO could be factory method, need to acquire the bean implementation now.
        List<StatisticPostProcessor> postprocessors = null;

        return postprocessors;
    }

    /**
     * Sets the list of statistical units from the <code>statisticalUnits</code> parameter. If
     * <code>automaticallyFindStatsiticsToLoad</code> has been set to true, then the statistical units sets by this
     * method will be overwritten by those automatically discovered using the
     * <code>setStatisticalUnitsFromApplicationContext</code> method
     */
    public void setStatisticalUnits(List<Statistic> statisticalUnits) {
        for (Statistic stat : statisticalUnits) {
            log.info("Registering statistic [{}], role {}", stat.getStatisticParameters().getUnitName(), stat
                    .getStatisticParameters().getType());
        }
        this.statisticalUnits = statisticalUnits;

    }

    public void setStatisticalUnitsFromApplicationContext() {
        statisticalUnits = new ArrayList<Statistic>();
        Map<String, ?> allStatisticBeans = applicationContext.getBeansOfType(Statistic.class);
        for (Map.Entry<String, ?> entry : allStatisticBeans.entrySet()) {
            log.debug("Registering statistic [{}], role {}", ((Statistic) entry.getValue()).getStatisticParameters()
                    .getUnitName(), ((Statistic) entry.getValue()).getStatisticParameters().getType());
            statisticalUnits.add((Statistic) entry.getValue());
        }
    }

    public List<Statistic> getStatisticalUnits() {
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
