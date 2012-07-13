/**
 * 
 */
package uk.ac.cardiff.raptorweb.service;

/**
 * This service handles all functions for performing various garbage collection tasks in raptor-web (usually as background threads trigger by a scheduler).
 * 
 */
public interface GarbageCollectionService {

    /**
     * Each new graph image in raptor is saved with a unique filename so the browser does not cache the image. These images are cleaned (removed) based on the
     * criteria specified by the implementing method.
     */
    public void cleanOldGraphsDirectory();

}
