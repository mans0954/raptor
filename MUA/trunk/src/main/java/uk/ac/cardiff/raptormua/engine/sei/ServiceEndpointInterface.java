package uk.ac.cardiff.raptormua.engine.sei;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.log4j.Logger;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.sei.Collector;
import uk.ac.cardiff.sei.UnitAggregator;

/**
 * @author philsmart
 *
 * Instances of this class are responsible for retrieving data from a service endpoint
 *
 */
public class ServiceEndpointInterface {
    static Logger log = Logger.getLogger(ServiceEndpointInterface.class);

    public static List getAuthentications(String endpoint){
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(UnitAggregator.class);
	//factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();


        Set<String> overrides = new HashSet();
        overrides.add(ShibbolethEntry.class.getName());
        overrides.add(AuthenticationEntry.class.getName());
        overrides.add(UsageEntry.class.getName());
        databinding.setOverrideTypes(overrides);

	factory.setAddress(endpoint);
	//factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	UnitAggregator client = (UnitAggregator) factory.create();
	log.debug("Accessing the UA version "+client.getVersion());
//	List<Entry> auths = client.getAllAuthentications();
//	log.debug("Retrieved "+auths.size()+" authentications");
//	for (Entry ent : auths){//
//	    log.debug(ent+" "+ent.getClass());//
//	}
	return null;
    }

    public static List getUsages(String endpoint){
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(UnitAggregator.class);
	//factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();


        Set<String> overrides = new HashSet();
        overrides.add(ShibbolethEntry.class.getName());
        overrides.add(AuthenticationEntry.class.getName());
        overrides.add(UsageEntry.class.getName());
        databinding.setOverrideTypes(overrides);

	factory.setAddress(endpoint);
	//factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	UnitAggregator client = (UnitAggregator) factory.create();
	log.debug("Accessing the ICA version "+client.getVersion());
//	List<Entry> auths = client.getAllUsages();
//	log.debug("Retrieved "+auths.size()+" usages");
//	for (Entry ent : auths){//
//	    log.debug(ent+" "+ent.getClass());//
//	}
	return null;
    }

    public static void main(String args[]){
    	ServiceEndpointInterface.getAuthentications("http://localhost:8080/UA/UnitAggregator");
    }

}
