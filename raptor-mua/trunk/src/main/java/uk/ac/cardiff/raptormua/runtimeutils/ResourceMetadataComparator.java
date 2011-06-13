package uk.ac.cardiff.raptormua.runtimeutils;

import java.util.Comparator;

import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.resource.ResourceMetadata;

public class ResourceMetadataComparator implements Comparator<ResourceMetadata>{
	
	 /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceMetadataComparator.class);


	/**
	 * Compares the <code>resourceId</code> from two <code>ResourceMetadata</code>
	 * instances. First it removes the protocol from the <code>resourceId</code> URL. Hence,
	 * it compares the hostname and path only.
	 */
	public int compare(ResourceMetadata one, ResourceMetadata two) {
		String first = one.getResourceId();
		String second = two.getResourceId();
		if (first.contains("://")){
			first = first.substring(first.indexOf('/'),first.length()-2);
		}
		if (second.contains("://")){
			second = second.substring(second.indexOf('/'),second.length()-1);
		}
		
		return first.compareTo(second);
	}
	
	

}
