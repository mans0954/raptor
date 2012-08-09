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

package uk.ac.cardiff.raptormua.engine;

import javax.annotation.Resource;

import org.springframework.util.Assert;
import org.testng.annotations.BeforeMethod;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.raptor.store.DefaultEventStorageEngine;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticHandler;

public class CapabilitiesConstructorTest extends BaseMuaTest {

    @Resource(name = "capabilitiesConstructor")
    private CapabilitiesConstructor constructor;

    @Resource(name = "statisticalUnits")
    private StatisticHandler statisticsHandler;

    @Resource(name = "storageEngine")
    private DefaultEventStorageEngine storageEngine;

    @Resource(name = "MUAMetadata")
    private ServiceMetadata metadata;

    @BeforeMethod
    public void testInitialised() {
        Assert.notNull(constructor);
        Assert.notNull(statisticsHandler);
        Assert.notNull(storageEngine);
        Assert.notNull(metadata);
    }

    // @Test
    // public void queueCapabilitiesTest() throws InterruptedException {
    // for (int i = 0; i < 10; i++) {
    // constructor.getCapabilities();
    // }
    // // must sleep as thread test.
    // Thread.sleep(20000);
    // }
}
