package uk.ac.cardiff.raptor.store;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;


public class AsynchronousEntryStorage implements StoreEntriesTaskCallbackInterface{

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(AsynchronousEntryStorage.class);
		
	public void storageResultCallback(Object result) {
		log.debug("Storage task completed");
		
	}
	
	public void store(List<Event> events){
		StoreEntriesTask storeEntryTask = new StoreEntriesTask();
		storeEntryTask.setAsynchronousEntryStorage(this);
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(storeEntryTask);
//		try {
//			task.get();
//			log.debug("Result from task.get () = ");
//		} catch (Exception e) {
//			log.error("Error adding events to the database...events stored for future storage...",e);
//		}
//		es.shutdown();
	}

}
