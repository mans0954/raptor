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
