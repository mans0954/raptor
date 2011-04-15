/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;




import java.util.HashMap;
import java.util.HashSet;
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

        //String id, String ldapUrl, String ldapBaseDn, boolean startTls, int maxIdle, int initIdleCapacity
        LdapDataConnector ldapConnector = new LdapDataConnector("ldap","zidman12.cf.ac.uk","o=people",false,10,10);
        ldapConnector.setPrincipal("cn=srvreg1,o=users");
        ldapConnector.setPrincipalCredential("1hk27be");
        ldapConnector.setCacheResults(false);
        Map<String, String> ldapProperties = new HashMap<String, String>();
        ldapProperties.put("","");
        ldapConnector.initialize();

        HashSet<Attribute<?>> attributes = new HashSet<Attribute<?>>();
        Attribute<?> attribute = new Attribute<String>("CardiffIDManStatus");
        attributes.add(attribute);
        context.setRequestedAttributes(attributes);
        //I think the ldap connector should take it form the context, as set above, not as a seperate set of attributes.
        //ldapConnector.setReturnAttributes("CardiffIDManStatus");

        Map<String, Attribute<?>> result = ldapConnector.resolve(context);
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
