
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.ProcessorInformation;

public class DefaultStatisticProcessorRegistry implements StatisticProcessorRegistry {

    List<StatisticPostProcessor> postProcessors;

    public void addPostProcessor(ProcessorInformation processorInformation) {

    }

    public void setPostProcessors(List<ProcessorInformation> processorsInformation) {
        for (ProcessorInformation processorInformation : processorsInformation) {

        }

    }

}
