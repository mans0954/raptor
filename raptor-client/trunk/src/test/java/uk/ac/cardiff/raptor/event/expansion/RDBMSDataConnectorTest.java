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

package uk.ac.cardiff.raptor.event.expansion;

import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;
import uk.ac.cardiff.raptor.event.expansion.connector.RDBMSDataConnector;

public class RDBMSDataConnectorTest {

    private RDBMSDataConnector dataConnector;

    @BeforeClass
    public void setup() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("org.postgresql.Driver");
        bds.setUrl("jdbc:postgresql://localhost/identities");
        bds.setUsername("postgres");
        bds.setPassword("");

        dataConnector = new RDBMSDataConnector();
        dataConnector.setDataSource(bds);
        dataConnector.setNoResultIsError(false);
        dataConnector.setCacheResults(true);
        dataConnector.setCacheTimeoutMs(1000);
        dataConnector.initialise();
    }

    @Test
    public void getAttributesTest() throws AttributeAssociationException {
        dataConnector.setSearchTemplate("SELECT * FROM mock_identities_table where cn='[principal]'");
        Map<String, String> attributes = dataConnector.lookup("scmps2");
        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            Assert.notNull(attribute);
            System.out.println(attribute.getKey() + "," + attribute.getValue());
        }
    }

    @Test
    public void cacheTest() throws AttributeAssociationException {
        dataConnector.setSearchTemplate("SELECT * FROM mock_identities_table where cn='[principal]'");
        Map<String, String> attributes = dataConnector.lookup("scmps2");
        Map<String, String> attributesCached = dataConnector.lookup("scmps2");
        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            Assert.notNull(attribute);
            System.out.println(attribute.getKey() + "," + attribute.getValue());
        }
    }

}
