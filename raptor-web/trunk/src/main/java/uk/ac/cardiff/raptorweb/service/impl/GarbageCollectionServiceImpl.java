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
package uk.ac.cardiff.raptorweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.engine.garbage.GraphDirectoryCleaner;
import uk.ac.cardiff.raptorweb.service.GarbageCollectionService;

public final class GarbageCollectionServiceImpl implements GarbageCollectionService {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(GarbageCollectionServiceImpl.class);

    private GraphDirectoryCleaner graphDirectoryCleaner;

    @Override
    public void cleanOldGraphsDirectory() {
        graphDirectoryCleaner.clean();
    }

    /**
     * @param graphDirectoryCleaner
     *            the graphDirectoryCleaner to set
     */
    public void setGraphDirectoryCleaner(GraphDirectoryCleaner graphDirectoryCleaner) {
        this.graphDirectoryCleaner = graphDirectoryCleaner;
    }

}
