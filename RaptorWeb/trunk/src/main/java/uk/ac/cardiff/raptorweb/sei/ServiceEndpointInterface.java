package uk.ac.cardiff.raptorweb.sei;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.log4j.Logger;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.sei.MultiUnitAggregator;
import uk.ac.cardiff.sei.UnitAggregator;

/**
 * @author philsmart
 * 
 *         Instances of this class are responsible for retrieving data from a
 *         service endpoint
 * 
 */
public class ServiceEndpointInterface {
	static Logger log = Logger.getLogger(ServiceEndpointInterface.class);

	public static Capabilities discoverMUACapabilities(String endpoint) {
		Capabilities capabilities = null;
		try {
			ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
			factory.setServiceClass(MultiUnitAggregator.class);
			AegisDatabinding databinding = new AegisDatabinding();
			factory.setAddress(endpoint);
			factory.getServiceFactory().setDataBinding(databinding);

			MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
			log.debug("Accessing the MUA version " + client.getVersion());
			capabilities = client.getCapabilities();
			log.debug("Retrieved capabilities from the MUA [" + endpoint + "]");
			// for (Entry ent : auths){//
			// log.debug(ent.getEventTime()+" "+ent.getClass());//
			// }
		} catch (Exception e) {
			capabilities = new Capabilities();
			capabilities.setError(true);
			capabilities.setErrorMessage(e.getMessage());
		}
		return capabilities;

	}

	public static List getAuthentications(String endpoint) {
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setServiceClass(UnitAggregator.class);
		// factory.setServiceName(new
		// QName("http://impl.wsinterface.cf.ac.uk.main/",
		// "CollectorImplService"));
		AegisDatabinding databinding = new AegisDatabinding();

		Set<String> overrides = new HashSet();
		overrides.add(ShibbolethEntry.class.getName());
		overrides.add(AuthenticationEntry.class.getName());
		overrides.add(UsageEntry.class.getName());
		databinding.setOverrideTypes(overrides);

		factory.setAddress(endpoint);
		// factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
		factory.getServiceFactory().setDataBinding(databinding);

		UnitAggregator client = (UnitAggregator) factory.create();
		log.debug("Accessing the UA version " + client.getVersion());
		List<Entry> auths = client.getAllAuthentications();
		log.debug("Retrieved " + auths.size()
				+ " authentications from the UA [" + endpoint + "]");
		// for (Entry ent : auths){//
		// log.debug(ent.getEventTime()+" "+ent.getClass());//
		// }
		return auths;
	}

	public static List getUsages(String endpoint) {
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setServiceClass(UnitAggregator.class);
		// factory.setServiceName(new
		// QName("http://impl.wsinterface.cf.ac.uk.main/",
		// "CollectorImplService"));
		AegisDatabinding databinding = new AegisDatabinding();

		Set<String> overrides = new HashSet();
		overrides.add(ShibbolethEntry.class.getName());
		overrides.add(AuthenticationEntry.class.getName());
		overrides.add(UsageEntry.class.getName());
		databinding.setOverrideTypes(overrides);

		factory.setAddress(endpoint);
		// factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
		factory.getServiceFactory().setDataBinding(databinding);

		UnitAggregator client = (UnitAggregator) factory.create();
		log.debug("Accessing the ICA version " + client.getVersion());
		// List<Entry> auths = client.getAllUsages();
		// log.debug("Retrieved "+auths.size()+" usages");
		// for (Entry ent : auths){//
		// log.debug(ent+" "+ent.getClass());//
		// }
		return null;
	}

	public static void main(String args[]) {
		ServiceEndpointInterface
				.discoverMUACapabilities("http://localhost:8080/MUA/MultiUnitAggregator");
		// ServiceEndpointInterface.getAuthentications("http://localhost:8081/UA/UnitAggregator");
	}

}
