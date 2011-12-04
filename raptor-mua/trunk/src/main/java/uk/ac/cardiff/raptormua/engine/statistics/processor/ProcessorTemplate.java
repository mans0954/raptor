
package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticPostProcessor;

public class ProcessorTemplate {

    private Class<? extends StatisticPostProcessor> processorClass;

    private String processorFriendlyName;

    private List<MethodParameter> methodParameters;

    /**
     * @param processorFriendlyName the processorFriendlyName to set
     */
    public void setProcessorFriendlyName(String processorFriendlyName) {
        this.processorFriendlyName = processorFriendlyName;
    }

    /**
     * @return the processorFriendlyName
     */
    public String getProcessorFriendlyName() {
        return processorFriendlyName;
    }

    /**
     * @param processorClass the processorClass to set
     */
    public void setProcessorClass(Class<? extends StatisticPostProcessor> processorClass) {
        this.processorClass = processorClass;
    }

    /**
     * @return the processorClass
     */
    public Class<? extends StatisticPostProcessor> getProcessorClass() {
        return processorClass;
    }

    /**
     * @param methodParameters the methodParameters to set
     */
    public void setMethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    /**
     * @return the methodParameters
     */
    public List<MethodParameter> getMethodParameters() {
        return methodParameters;
    }

}
