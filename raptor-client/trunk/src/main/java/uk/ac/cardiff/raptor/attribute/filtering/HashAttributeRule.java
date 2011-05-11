/**
 *
 */
package uk.ac.cardiff.raptor.attribute.filtering;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 *
 */
public class HashAttributeRule extends AttributeRule{

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(HashAttributeRule.class);

    private String attributeId;

    /** The hashing algorithm used, this property is not user definable, its fixed to SHA-256*/
    private final String HASH_ALGORITHM="SHA-256";

    /**
     * Hash the attribute defined by the <code>attributeId</code> tag, with the
     * entityId name of this service.
     *
     * @throws AttributeFilterException
     */
    public void filterAttribute(Event event, ServiceMetadata metadata) throws AttributeFilterException {
        log.debug("Filtering event {}",event);
        try{
            if (classHasAttribute(event, getAttributeId())) {
                MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
                md.reset();
                Object value = getValueForObject(event, getAttributeId());
                log.debug("Value [{}]",metadata.getEntityId()+value);
                if (value instanceof String){
                    String valueAsString = (String) value;
                    String toHash = metadata.getEntityId()+valueAsString;
                    md.update(toHash.getBytes("UTF-8"));
                    byte[] digest = md.digest();
                    BigInteger number = new BigInteger(1,digest);
                    String hashedValue = number.toString(16);
                    this.setValueForObject(event, hashedValue, getAttributeId());
                    log.debug("hash: "+number.toString(16));
                }

            }
        }
        catch (NoSuchAlgorithmException e){
            throw new AttributeFilterException(e);
        } catch (UnsupportedEncodingException e) {
            throw new AttributeFilterException(e);
        }

    }

    /**
     * @param attributeId the attributeId to set
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * @return the attributeId
     */
    public String getAttributeId() {
        return attributeId;
    }



}
