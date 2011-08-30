
package uk.ac.cardiff.raptormua.engine.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for building post-processors and pre-processors given a set of parameters
 * 
 * @author philsmart
 * 
 */
public class StatisticProcessorFactory {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(StatisticProcessorFactory.class);

    /** the default package name for processors */
    private static final String DEFAULT_PROCESSOR_PACKAGE = "uk.ac.cardiff.raptormua.engine.statistics.processor.";

    public StatisticPostProcessor getPostProcessor(String processorName) {
        StatisticPostProcessor processor = null;
        Object processorClass;
        try {
            processorClass = Class.forName(DEFAULT_PROCESSOR_PACKAGE + processorName);
            if (processorClass instanceof StatisticPostProcessor) {
                processor = (StatisticPostProcessor) processorClass;
                log.debug("Processor factory has constructor [{}]", processor);
                return processor;
            }
        } catch (ClassNotFoundException e) {
            log.error("Unable to create new statistical processor", e);
        }

        return processor;
    }

}
