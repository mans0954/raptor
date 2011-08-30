
package uk.ac.cardiff.raptormua.engine.statistics;

import uk.ac.cardiff.model.wsmodel.ProcessorInformation;

public interface StatisticProcessorRegistry {

    public void addPostProcessor(ProcessorInformation processorInformation);

}
