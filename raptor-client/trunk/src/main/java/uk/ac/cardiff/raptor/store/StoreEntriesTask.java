/**
 *
 */
package uk.ac.cardiff.raptor.store;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.store.dao.MUADataConnection;
import uk.ac.cardiff.raptor.store.impl.PersistantEntryHandler;


/**
 * @author philsmart
 *
 */
public class StoreEntriesTask implements Callable<Boolean>{
	
	/** class logger */
	private final Logger log = LoggerFactory.getLogger(StoreEntriesTask.class);

    private MUADataConnection dataConnection;
    
    private AsynchronousEntryStorage asynchronousEntryStorage;

    public Boolean call() throws Exception {
    	log.debug("Storing entries");
        for (int i=0; i < 10000000; i++){
        	//System.out.println("1");
        	
        }
        return true;
    }

    /**
     * @param dataConnection the dataConnection to set
     */
    public void setDataConnection(MUADataConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    /**
     * @return the dataConnection
     */
    public MUADataConnection getDataConnection() {
        return dataConnection;
    }

	public void setAsynchronousEntryStorage(AsynchronousEntryStorage asynchronousEntryStorage) {
		this.asynchronousEntryStorage = asynchronousEntryStorage;
	}

	public AsynchronousEntryStorage getAsynchronousEntryStorage() {
		return asynchronousEntryStorage;
	}

}
