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
