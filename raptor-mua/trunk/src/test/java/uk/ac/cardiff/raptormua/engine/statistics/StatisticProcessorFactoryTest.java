
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.raptormua.engine.BaseMuaTest;

public class StatisticProcessorFactoryTest extends BaseMuaTest {

    @Resource(name = "processorFactory")
    StatisticProcessorFactory factory;

    @Test
    public void getProcessorTest() throws StatisticPostProcessorFactoryException {
        List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
        MethodParameter param = new MethodParameter();
        param.setParameterType(ParameterType.VALUE);
        param.setValue(5);
        methodParameters.add(param);
        factory.getPostProcessor("uk.ac.cardiff.raptormua.engine.statistics.processor.CutRowsPostProcessor",
                methodParameters);
    }
}
