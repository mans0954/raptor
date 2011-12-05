
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.raptormua.engine.statistics.processor.ProcessorTemplate;
import uk.ac.cardiff.raptormua.engine.statistics.processor.ProcessorTemplate.SCOPE;

public class DefaultStatisticProcessorRegistry implements StatisticProcessorRegistry {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(DefaultStatisticProcessorRegistry.class);

    /** A list or register of all postprocessors */
    private List<ProcessorTemplate> postprocessors;

    /** Factory used to construct processors from templates */
    private StatisticProcessorFactory processorFactory;

    /**
     * Calls the <code>processorFactory</code> to instantiate a processor based a matching template from the list
     * <code>postprocessors</code> and the input <code>processorInformation</code>. Also sets the friendly name of the
     * <code>processorInformation</code> for future reference. This is a synchronized method, only one active thread can
     * call this method at a time.
     * 
     * @param processorInformation information about the processor to create.
     * @throws StatisticPostProcessorFactoryException
     */
    public synchronized StatisticPostProcessor getProcessor(ProcessorInformation processorInformation)
            throws ProcessorRegistryException, StatisticPostProcessorFactoryException {

        for (ProcessorTemplate processorTemplate : postprocessors) {
            if (processorTemplate.getProcessorClass().getCanonicalName()
                    .equals(processorInformation.getProcessorClass())) {

                // check singleton
                if (processorTemplate.getScope().equals(SCOPE.SINGLETON)) {
                    log.debug("Returning singleton bean reference [{}] for [{}]",
                            processorTemplate.getSingletonBeanReference(), processorTemplate.getProcessorFriendlyName());
                    StatisticPostProcessor processor = processorTemplate.getSingletonBeanReference();
                    processor.setFriendlyName(processorTemplate.getProcessorFriendlyName());
                    processorInformation.setFriendlyName(processorTemplate.getProcessorFriendlyName());
                    return processor;
                }
                // check else, create new (almost prototype) instance
                processorInformation.setFriendlyName(processorTemplate.getProcessorFriendlyName());
                return processorFactory.getPostProcessor(processorTemplate, processorInformation.getMethodParameters());
            }
        }
        throw new ProcessorRegistryException("No such processor with type " + processorInformation.getProcessorClass());

    }

    /**
     * @param postprocessors the postprocessors to set
     */
    public void setPostprocessors(List<ProcessorTemplate> postprocessors) {
        for (ProcessorTemplate processorTemplate : postprocessors) {
            log.info("Registering postprocessor [{}] type [{}]", processorTemplate.getProcessorFriendlyName(),
                    processorTemplate.getProcessorClass());
        }
        this.postprocessors = postprocessors;
    }

    public List<ProcessorTemplate> getStatisticProcessorTemplates() {
        return postprocessors;
    }

    /**
     * @return the postprocessors
     */
    public List<ProcessorTemplate> getPostprocessors() {
        return postprocessors;
    }

    /**
     * @param processorFactory the processorFactory to set
     */
    public void setProcessorFactory(StatisticProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    /**
     * @return the processorFactory
     */
    public StatisticProcessorFactory getProcessorFactory() {
        return processorFactory;
    }

}
