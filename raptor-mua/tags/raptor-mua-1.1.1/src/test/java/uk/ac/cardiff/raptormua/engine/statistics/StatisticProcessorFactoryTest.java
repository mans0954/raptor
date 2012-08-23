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

import javax.annotation.Resource;

import org.testng.annotations.Test;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.raptormua.engine.BaseMuaTest;

public class StatisticProcessorFactoryTest extends BaseMuaTest {

    @Resource(name = "processorFactory")
    StatisticProcessorFactory factory;

//    @Test
//    public void getProcessorTest() throws StatisticPostProcessorFactoryException {
//        List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
//        MethodParameter param = new MethodParameter();
//        param.setParameterType(ParameterType.VALUE);
//        param.setValue(5);
//        methodParameters.add(param);
//        factory.getPostProcessor("uk.ac.cardiff.raptormua.engine.statistics.processor.CutRowsPostProcessor",
//                methodParameters);
//    }
}