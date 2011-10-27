
package uk.ac.cardiff.raptor.store;

/**
 * An incremental event handler should be capable of storing only 'new' events, and capable of removing 'old' events
 * while still be capable of parsing events from the date and time of the last removed event.
 * 
 */
public interface IncrementalEventHandler extends EventHandler {

    /**
     * Rests this <code>IncrementalEntryHandler</code> back to its initial state
     * 
     */
    public void reset();

}
