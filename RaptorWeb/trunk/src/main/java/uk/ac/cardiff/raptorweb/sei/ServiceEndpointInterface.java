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
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
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
			e.printStackTrace();
		}
		return capabilities;

	}



	public static void main(String args[]) {
		ServiceEndpointInterface.discoverMUACapabilities("http://localhost:8080/MUA/MultiUnitAggregator");
		// ServiceEndpointInterface.getAuthentications("http://localhost:8081/UA/UnitAggregator");
	}

	/**
	 * @param selectedStatisticalUnit
	 */
	public static AggregatorGraphModel invokeStatisticalUnit(String endpoint, String selectedStatisticalUnit) {
		try {
			ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
			factory.setServiceClass(MultiUnitAggregator.class);
			AegisDatabinding databinding = new AegisDatabinding();
			factory.setAddress(endpoint);
			factory.getServiceFactory().setDataBinding(databinding);

			MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
			//client.invokeStatisticalUnit(selectedStatisticalUnit);
			log.debug("Accessing the MUA version " + client.getVersion());
			AggregatorGraphModel gmodel = client.invokeStatisticalUnit(selectedStatisticalUnit);
			log.debug("Retrieved Graph Model from the MUA [" + endpoint + "]");
			// for (Entry ent : auths){//
			// log.debug(ent.getEventTime()+" "+ent.getClass());//
			// }
			return gmodel;
		} catch (Exception e) {
			log.error("Problem trying to invoke statistical unit "+selectedStatisticalUnit+" on MUA -> "+e.getMessage());
		}
		return null;
		}

	}


