/**
 *
 */
package uk.ac.cardiff.raptormua.model;

import java.util.concurrent.Callable;

import uk.ac.cardiff.raptormua.dao.MUADataConnection;

/**
 * @author philsmart
 *
 */
public class PersistentEntriesTask implements Callable{

    private MUADataConnection dataConnection;

    public Object call() throws Exception {
        this.wait(10000);
        return null;
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

}
