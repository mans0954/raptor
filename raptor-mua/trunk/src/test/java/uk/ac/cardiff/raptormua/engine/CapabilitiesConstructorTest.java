
package uk.ac.cardiff.raptormua.engine;

import javax.annotation.Resource;

import org.springframework.util.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.raptor.store.EventStorageEngine;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticHandler;

public class CapabilitiesConstructorTest extends BaseMuaTest {

    @Resource(name = "capabilitiesConstructor")
    private CapabilitiesConstructor constructor;

    @Resource(name = "statisticalUnits")
    private StatisticHandler statisticsHandler;

    @Resource(name = "storageEngine")
    private EventStorageEngine storageEngine;

    @Resource(name = "MUAMetadata")
    private ServiceMetadata metadata;

    @BeforeMethod
    public void testInitialised() {
        Assert.notNull(constructor);
        Assert.notNull(statisticsHandler);
        Assert.notNull(storageEngine);
        Assert.notNull(metadata);
    }

    @Test
    public void queueCapabilitiesTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            constructor.getCapabilities();
        }
        // must sleep as thread test.
        Thread.sleep(20000);
    }
}
