/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;




import java.util.HashMap;
import java.util.Map;

import net.shibboleth.idp.attribute.Attribute;
import net.shibboleth.idp.attribute.resolver.AttributeResolutionException;
import net.shibboleth.idp.attribute.resolver.AttributeResolver;
import net.shibboleth.idp.attribute.resolver.BaseDataConnector;
import net.shibboleth.idp.attribute.resolver.MockDataConnector;
import net.shibboleth.idp.attribute.resolver.ResolvedDataConnector;

import org.junit.Assert;
import org.opensaml.util.collections.LazySet;

/**
 * @author philsmart
 *
 */
public class AttributeAssociationTest {


    @org.junit.Test
    public void testSetAttributeDefinitions() {
        System.out.println("Do Test");
        AttributeResolver resolver = new AttributeResolver("foo");

        LazySet<BaseDataConnector> connectors = null;
        resolver.setDataConnectors(connectors);
        Assert.assertNotNull(resolver.getDataConnectors());
        Assert.assertTrue(resolver.getDataConnectors().isEmpty());

        connectors = new LazySet<BaseDataConnector>();
        resolver.setDataConnectors(connectors);
        Assert.assertNotNull(resolver.getDataConnectors());
        Assert.assertTrue(resolver.getDataConnectors().isEmpty());

        connectors.add(new MockDataConnector("foo", (Map)null));
        connectors.add(null);
        connectors.add(new MockDataConnector("bar", (Map)null));
        connectors.add(new MockDataConnector("foo", (Map)null));
        resolver.setDataConnectors(connectors);
        Assert.assertNotNull(resolver.getDataConnectors());
        Assert.assertEquals(resolver.getDataConnectors().size(), 2);

        connectors.clear();
        Assert.assertNotNull(resolver.getDataConnectors());
        Assert.assertEquals(resolver.getDataConnectors().size(), 2);

        resolver.setDataConnectors(connectors);
        Assert.assertNotNull(resolver.getDataConnectors());
        Assert.assertTrue(resolver.getDataConnectors().isEmpty());
    }

}
