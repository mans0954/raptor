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

package uk.ac.cardiff.raptor.remoting.client.sei.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.cxf.aegis.DatabindingException;
import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.aegis.type.TypeUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.AuthenticationEvent;
import uk.ac.cardiff.model.event.EzproxyAuthenticationEvent;
import uk.ac.cardiff.model.event.OpenathenslaAuthenticationEvent;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.remoting.client.sei.ServiceEndpointClient;
import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;

public class CxfServiceEndpointClient implements ServiceEndpointClient {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(CxfServiceEndpointClient.class);

    /** Raptor specific TLS parameters class, that can return cxf TLSParameters */
    private ClientTLSParameters tlsParameters;

    @Override
    public boolean sendEvents(EventPushMessage pushed, Endpoint endpoint) {
        try {
            ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
            factory.setServiceClass(MultiUnitAggregator.class);
            AegisDatabinding databinding = new AegisDatabinding();

            org.apache.cxf.aegis.AegisContext context = new org.apache.cxf.aegis.AegisContext();
            context.setWriteXsiTypes(true);

            Set<Class<?>> rootClasses = new HashSet<Class<?>>();

            Set<String> overrides = new HashSet<String>();
            overrides.add(ShibbolethIdpAuthenticationEvent.class.getName());
            overrides.add(AuthenticationEvent.class.getName());
            overrides.add(EzproxyAuthenticationEvent.class.getName());
            overrides.add(OpenathenslaAuthenticationEvent.class.getName());
            databinding.setOverrideTypes(overrides);

            for (String typeName : overrides) {
                Class<?> c = null;
                try {
                    c = ClassLoaderUtils.loadClass(typeName, TypeUtil.class);
                } catch (ClassNotFoundException e) {
                    throw new DatabindingException("Could not find override type class: " + typeName, e);
                }
                rootClasses.add(c);
            }

            context.setRootClasses(rootClasses);
            databinding.setAegisContext(context);

            factory.setAddress(endpoint.getServiceEndpoint());
            factory.getServiceFactory().setDataBinding(databinding);

            MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
            org.apache.cxf.endpoint.Client cl = ClientProxy.getClient(client);
            HTTPConduit httpConduit = (HTTPConduit) cl.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpConduit.setClient(httpClientPolicy);

            if (getTlsParameters() != null)
                httpConduit.setTlsClientParameters(getTlsParameters().getTlsClientParameters());

            log.debug("Accessing the endpoint version " + client.getVersion());
            client.addAuthentications(pushed);
            log.debug("Sent {} events", pushed.getEvents().size());

            return true;
        } catch (SoapFault e) {
            log.error("Could not send events to endpoint [{}]", endpoint.getServiceEndpoint(), e);
            return false;
        } catch (Exception e) {
            log.error("Could not send events to endpoint [{}]", endpoint.getServiceEndpoint(), e);
            return false;
        }

    }

    /**
     * @param tlsParameters the tlsParameters to set
     */
    public void setTlsParameters(ClientTLSParameters tlsParameters) {
        this.tlsParameters = tlsParameters;
    }

    /**
     * @return the tlsParameters
     */
    public ClientTLSParameters getTlsParameters() {
        return tlsParameters;
    }

}
