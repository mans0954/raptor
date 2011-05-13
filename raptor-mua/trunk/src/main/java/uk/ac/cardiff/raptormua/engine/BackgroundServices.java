/**
 *
 */
package uk.ac.cardiff.raptormua.engine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * All background services that work directly off the database store.
 * Is not abstracted through the storage engine or entry handler, so changing
 * the persistence layer may break the functions of this class.
 *
 * @author philsmart
 *
 */
public class BackgroundServices {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(BackgroundServices.class);

    /** The MUA's data connection */
    private RaptorDataConnection dataConnection;


    public void resourceClassification(){
        List<String> uniqueResource = (List<String>) dataConnection.runQuery("select resourceId from Event group by resourceId",null);
        List<ResourceMetadata> resourceClassification = (List<ResourceMetadata>) dataConnection.runQuery("from ResourceMetadata",null);


        for (String resourceId : uniqueResource){
            boolean resourceExists=false;
            for (ResourceMetadata resource : resourceClassification){
                 if (resource.getResourceId().equals(resourceId)){
                     resourceExists = true;
                 }
            }
            if (!resourceExists){
                ResourceMetadata resourceMetadata = new ResourceMetadata();
                resourceMetadata.setResourceId(resourceId);
                resourceMetadata.setExternal(true);
                resourceMetadata.setInternal(false);
                resourceClassification.add(resourceMetadata);
            }


        }
        log.debug("Background Service has created or ammended {} resourceId classifications",resourceClassification.size());
        try{
            dataConnection.saveAll(resourceClassification);
        }
        catch(DataAccessException e){
            log.error("Resource Classification Saving Error",e);
        }

        //sets those that are not set. Does not change existing events
        try{
            List<Event> eventsNotClassified = (List<Event>) dataConnection.runQuery("from Event where resourceIdCategory=0", null);

            log.debug("Background Resource Classification Service is appending classifications to {} events",eventsNotClassified.size());
            for (Event event : eventsNotClassified){
                for (ResourceMetadata resource : resourceClassification){
                    if (event.getResourceId().equals(resource.getResourceId())){
                        if (resource.isExternal()){
                            event.setResourceIdCategory(2);
                        }
                        else if (resource.isInternal()){
                            event.setResourceIdCategory(1);
                        }
                    }

                }
            }
            dataConnection.saveAll(eventsNotClassified);
            log.debug("Finished adding event classifications");
        }
        catch(DataAccessException e){
            log.error("Error adding event classifications to events without existing classification",e);
        }


    }


    /**
     * @param dataConnection the dataConnection to set
     */
    public void setDataConnection(RaptorDataConnection dataConnection) {
        this.dataConnection = dataConnection;
    }


    /**
     * @return the dataConnection
     */
    public RaptorDataConnection getDataConnection() {
        return dataConnection;
    }


}
