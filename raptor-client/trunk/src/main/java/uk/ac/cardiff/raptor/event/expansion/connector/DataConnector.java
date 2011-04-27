/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion.connector;

import java.util.Map;

/**
 * @author philsmart
 *
 */
public interface DataConnector {

    public  Map<String, String> lookup(String principal) throws AttributeAssociationException;

    public void initialise();

    public void setReturnAttributes(String[] s);

}
