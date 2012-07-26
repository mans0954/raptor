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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.raptor.store.TransactionInProgressException;

public class MUAEngineTest extends BaseMuaTest {

    @Resource(name = "MUAEngine")
    private MUAEngine engine;

    /**
     * Override the database connection with connection to a test database. Not currently as no easy way to set the
     * connection pool. Just use local database and assume test.
     */
   // @BeforeClass
    public void setup() {
        Assert.assertNotNull(engine);

        // force event storage to be syncrhonous so the test does not finish before processing is complete
        engine.setForceSynchronousEventStorage(true);

        // ComboPooledDataSource cpds = new ComboPooledDataSource();
        // try {
        // cpds.setDriverClass("org.postgresql.Driver");
        // } catch (PropertyVetoException e) {
        // e.printStackTrace();
        // }
        // cpds.setJdbcUrl("jdbc:postgresql://localhost/mua-test");
        // cpds.setUser("postgres");
        // cpds.setPassword("");
        // cpds.setMinPoolSize(5);
        // cpds.setAcquireIncrement(5);
        // cpds.setMaxPoolSize(20);

    }

    /**
     * Will create entry in event handler - make sure dummy event handler used?
     */
    //@Test
    public void addSingleEventTest() {
        EventPushMessage message = new EventPushMessage();
        message.setTimeOfPush(new Date(System.currentTimeMillis()));
        List<Event> events = new ArrayList<Event>();
        events.add(createShibEvent());
        message.setEvents(events);
        message.setClientMetadata(createMetadata());
        try {
            engine.addAuthentications(message);
        } catch (TransactionInProgressException e) {
            Assert.fail("Transaction in progress", e);
        }

    }

    private ServiceMetadata createMetadata() {
        ServiceMetadata serviceMetadata = new ServiceMetadata();
        serviceMetadata.setContactEmail("smartp@cf.ac.uk");
        serviceMetadata.setDescription("test");
        serviceMetadata.setEntityId("http://test.cf.ac.uk/raptor-ica");
        serviceMetadata.setOrganisationName("TestCardiff");
        serviceMetadata.setServiceName("raptor-ica-test");
        return serviceMetadata;
    }

    private ShibbolethIdpAuthenticationEvent createShibEvent() {
        ShibbolethIdpAuthenticationEvent event = new ShibbolethIdpAuthenticationEvent();
        event.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        event.setResourceId("http://testresource.cf.ac.uk");
        event.setEventTime(new DateTime(System.currentTimeMillis()));
        event.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        event.setPrincipalName("scmps2");
        event.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        event.setAttributes(new String[] {"eduPersonScopedAffiliation", "eduPersonEntitlement"});
        event.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        event.setRequestId("");
        event.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        event.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");
        return event;
    }

}
