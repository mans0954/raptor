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

import uk.ac.cardiff.model.wsmodel.Capabilities;

public abstract class BaseCapabilitiesContructor {
	
	/**
	 * 
	 * When building the cache, should it be whole or part recomputed.
	 *
	 */
	public enum ItemsToCompute{
		/**
		 * Compute ALL aspects of the cache.
		 */
		ALL,
		/**
		 * Compute only the statistical units.
		 */
		STATISTICS
	}

    public abstract Capabilities getCapabilities();

    /** If things have changed, the cache should be invalidated immediately. */
    public abstract void invalidateCache(ItemsToCompute items);

    /** called to initialise this capabiliies constructor. If needed. */
    public abstract void initialiseCapabilities();
}
