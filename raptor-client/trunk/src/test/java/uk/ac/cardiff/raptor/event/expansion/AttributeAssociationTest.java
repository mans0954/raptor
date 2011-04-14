/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;




import java.util.HashMap;
import java.util.Map;

import net.shibboleth.idp.attribute.Attribute;
import net.shibboleth.idp.attribute.resolver.AttributeResolutionContext;
import net.shibboleth.idp.attribute.resolver.AttributeResolutionException;
import net.shibboleth.idp.attribute.resolver.AttributeResolver;
import net.shibboleth.idp.attribute.resolver.BaseDataConnector;

import net.shibboleth.idp.attribute.resolver.ResolvedDataConnector;

import org.junit.Assert;
import org.opensaml.util.collections.LazySet;

/**
 * @author philsmart
 *
 */
public class AttributeAssociationTest {


    @org.junit.Test
    public void testSetAttributeDefinitions() throws Exception{
        System.out.println("Do Test");
        AttributeResolver resolver = new AttributeResolver("foo");

        AttributeResolutionContext context = new AttributeResolutionContext(null);

        MockBaseDataConnector connector = new MockBaseDataConnector("foo", (Map<String, Attribute<?>>)null);
        Assert.assertNull(connector.resolve(context));

        //String id, String ldapUrl, String ldapBaseDn, boolean startTls, int maxIdle, int initIdleCapacity
        LdapDataConnector ldapConnector = new LdapDataConnector("ldap","","",false,100,100);

        HashMap<String, Attribute<?>> values = new HashMap<String, Attribute<?>>();
        connector = new MockBaseDataConnector("foo", values);
        Assert.assertNotNull(connector.resolve(context));

        Attribute<?> attribute = new Attribute<String>("foo");
        values.put(attribute.getId(), attribute);

        connector = new MockBaseDataConnector("foo", values);
        Map<String, Attribute<?>> result = connector.resolve(context);
        Assert.assertTrue(result.containsKey(attribute.getId()));
        Assert.assertEquals(result.get(attribute.getId()), attribute);
    }


    /**
     * This class implements the minimal level of functionality and is meant only as a means of testing the abstract
     * {@link BaseDataConnector}.
     */
    private static final class MockBaseDataConnector extends BaseDataConnector {

        /** Static values returned for {@link #resolve(AttributeResolutionContext)}. */
        private Map<String, Attribute<?>> staticValues;

        /**
         * Constructor.
         *
         * @param id id of the data connector
         * @param values values returned for {@link #resolve(AttributeResolutionContext)}
         */
        public MockBaseDataConnector(final String id, final Map<String, Attribute<?>> values) {
            super(id);
            staticValues = values;
        }

        /** {@inheritDoc} */
        protected Map<String, Attribute<?>> doDataConnectorResolve(AttributeResolutionContext resolutionContext)
                throws AttributeResolutionException {
            return staticValues;
        }
    }

}
