
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

public abstract class AbstractStatisticRegistry implements StatisticRegistry {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(AbstractStatisticRegistry.class);

    /** The registry of processors to interrogate */
    private StatisticProcessorRegistry processorRegistry;

    /**
     * List of {@link uk.ac.cardiff.raptormua.engine.statistics.BaseStatistic}s that have been registered with this
     * handler
     */
    protected List<BaseStatistic> statisticalUnits;

    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
        BaseStatistic toUpdate = null;
        for (BaseStatistic statistic : statisticalUnits) {
            if (statistic.getStatisticParameters().getUnitName()
                    .equals(statisticalUnitInformation.getStatisticParameters().getUnitName()))
                toUpdate = statistic;
        }
        log.debug("Found statistic [{}] to update", toUpdate.getStatisticParameters().getUnitName());
        performUpdate(toUpdate, statisticalUnitInformation);
        log.debug("Finished updating statistic [{}]", toUpdate.getStatisticParameters().getUnitName());

    }

    public BaseStatistic getStatistic(String statisticName) {
        for (BaseStatistic statistic : statisticalUnits) {
            if (statistic.getStatisticParameters().getUnitName().equals(statisticName)) {
                log.debug("Found statistic [{}] from statistic registry", statistic.getStatisticParameters()
                        .getUnitName());
                return statistic;
            }
        }
        return null;
    }

    /**
     * Updates the statistical parameters of the passed statistic.
     * 
     * @param statistic - the statistic to update
     * @param statisticalUnitInformation - the statistical unit information to used update the <code>statistic</code>
     */
    private void performUpdate(BaseStatistic statistic, StatisticalUnitInformation statisticalUnitInformation) {
        statistic.setStatisticParameters(statisticalUnitInformation.getStatisticParameters());
        // now deal with the post processors

        // clear them first
        statistic.setPostprocessor(null);
        List<ProcessorInformation> information = statisticalUnitInformation.getPostprocessors();
        for (ProcessorInformation info : information) {
            log.debug("Setting processor [{}]", info.getProcessorClass());
            if (info.getMethodParameters() != null) {
                for (MethodParameter parameter : info.getMethodParameters()) {
                    log.debug("Parameter [{},{}]", parameter.getParameterName(), parameter.getValue());
                }
            }
        }
        List<StatisticPostProcessor> postProcessors =
                initialisePostProcessors(statisticalUnitInformation.getPostprocessors());
        for (StatisticPostProcessor postProcessor : postProcessors) {
            log.debug("Setting post processor {}", postProcessor);
        }
        statistic.setPostprocessor(postProcessors);
    }

    /**
     * 
     * @param postProcessorsInformation
     * @return
     */
    protected List<StatisticPostProcessor> initialisePostProcessors(List<ProcessorInformation> processorInformation) {
        if (processorInformation == null) {
            return null;
        }
        ArrayList<StatisticPostProcessor> initlialisedPostProcessors = new ArrayList<StatisticPostProcessor>();
        for (ProcessorInformation information : processorInformation) {
            try {
                StatisticPostProcessor processor = processorRegistry.getProcessor(information);
                initlialisedPostProcessors.add(processor);
            } catch (StatisticPostProcessorFactoryException e) {
                log.error("Could not set processor {} on statistic", information.getProcessorClass(), e);
            } catch (ProcessorRegistryException e) {
                log.error("Could not set processor {} on statistic", information.getProcessorClass(), e);
            }
        }

        return initlialisedPostProcessors;
    }

    public StatisticProcessorRegistry getStatisticProcessorRegistry() {
        return processorRegistry;
    }

    public List<BaseStatistic> getStatisticalUnits() {
        return statisticalUnits;
    }

    /**
     * @param processorRegistry the processorRegistry to set
     */
    public void setProcessorRegistry(StatisticProcessorRegistry processorRegistry) {
        this.processorRegistry = processorRegistry;
    }

    /**
     * @return the processorRegistry
     */
    public StatisticProcessorRegistry getProcessorRegistry() {
        return processorRegistry;
    }

}