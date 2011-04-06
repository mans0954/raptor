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

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.aegis.DatabindingException;
import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.aegis.type.TypeUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.AuthenticationEvent;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.remoting.client.sei.ServiceEndpointClient;
import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;

public class CxfServiceEndpointClient implements ServiceEndpointClient {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(CxfServiceEndpointClient.class);
    
    /** TLS parameters */
    private TLSClientParameters tlsParameters;

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
	    databinding.setOverrideTypes(overrides);

	    for (String typeName : overrides) {
		Class c = null;
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

	  
	    if (tlsParameters!=null)
	    	httpConduit.setTlsClientParameters(tlsParameters);

	    log.debug("Accessing the endpoint version " + client.getVersion());
	    client.addAuthentications(pushed);
	    log.debug("Sent {} events", pushed.getEvents().size());

	    return true;
	} catch (SoapFault e) {
	    log.error("Could not send events to endpoint [{}] -> {}", new Object[] { endpoint.getServiceEndpoint(), e.getMessage() });
	    e.printStackTrace();
	    return false;
	} catch (Exception e) {
	    log.error("Could not send events to endpoint [{}] -> {}", new Object[] { endpoint.getServiceEndpoint(), e.getMessage() });
	    e.printStackTrace();
	    return false;
	}

    }
    
    private TLSClientParameters getTlsSettings(){
    	 TLSClientParameters tls = new TLSClientParameters();

  	    tls.setDisableCNCheck(true);//disable URL and CN on cert match

  	    //clients private
  	    KeyStore keyStoreKeyManager = KeyStore.getInstance("JKS");
  	    File keyStoreFile = new File("/Users/philsmart/Documents/Java/RaptorWorkspace/keys/raptor-ica.jks");
  	    keyStoreKeyManager.load(new FileInputStream(keyStoreFile),  "phil11".toCharArray());
  	    KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
  	    keyFactory.init(keyStoreKeyManager, "phil11".toCharArray());

  	    KeyManager[] km = keyFactory.getKeyManagers();
  	    tls.setKeyManagers(km);

  	    //servers public key
  	    KeyStore keyStore = KeyStore.getInstance("JKS");
  	    File truststore = new File(endpoint.getPublicKey());
  	    keyStore.load(new FileInputStream(truststore),  endpoint.getPublicKeyPassword().toCharArray());
  	    TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
  	    trustFactory.init(keyStore);

  	    TrustManager[] tm = trustFactory.getTrustManagers();
  	    tls.setTrustManagers(tm);

    }
}
