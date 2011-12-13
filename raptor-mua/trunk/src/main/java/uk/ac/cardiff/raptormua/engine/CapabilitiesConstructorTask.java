
package uk.ac.cardiff.raptormua.engine;

import java.util.concurrent.Callable;

import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * Task that constructs capabilities in a background worker thread, before returning control back to the callback class.
 * 
 */
public class CapabilitiesConstructorTask implements Callable<Capabilities> {

    public Capabilities call() throws Exception {
        Capabilities capabilites = doConstructCapabilities();
        return capabilites;
    }

    private Capabilities doConstructCapabilities() {
        return new Capabilities();
    }
}
