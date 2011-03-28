package uk.ac.cardiff.raptor.remoting.client.sei.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.cxf.aegis.DatabindingException;
import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.aegis.type.TypeUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AuthenticationEvent;
import uk.ac.cardiff.model.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.raptor.remoting.client.sei.ServiceEndpointInterface;
import uk.ac.cardiff.sei.MultiUnitAggregator;

public class CxfServiceEndpointInterface implements ServiceEndpointInterface {

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(CxfServiceEndpointInterface.class);

	@Override
	public boolean sendEvents(EventPushMessage pushed, String endpointURL) {
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
			overrides.add(UsageEntry.class.getName());
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

			factory.setAddress(endpointURL);
			factory.getServiceFactory().setDataBinding(databinding);

			MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
			log.debug("Accessing the endpoint version " + client.getVersion());
			Set<ShibbolethIdpAuthenticationEvent> newEntries = new LinkedHashSet<ShibbolethIdpAuthenticationEvent>();
			client.addAuthentications(pushed);
			log.debug("Sent {} events", pushed.getEvents().size());
			return true;
		} catch (SoapFault e) {
		    log.error("Could not send events to endpoint [{}] -> {}", new Object[]{endpointURL, e.getMessage()});
		    return false;
		}catch (Exception e) {
		    log.error("Could not send events to endpoint [{}] -> {}", new Object[]{endpointURL, e.getMessage()});
		    return false;
		}

	}
}
