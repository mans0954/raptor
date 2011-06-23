/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
public final class HashAttributeRule extends BaseAttributeRule {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(HashAttributeRule.class);

    private String attributeId;

    /** The hashing algorithm used, this property is not user definable, its fixed to SHA-256 */
    private final String HASH_ALGORITHM = "SHA-256";

    /**
     * Hash the attribute defined by the <code>attributeId</code> tag, with the entityId name of this service.
     * 
     * @throws AttributeFilterException
     */
    public void filterAttribute(final Event event, final ServiceMetadata metadata) throws AttributeFilterException {
        try {
            if (classHasAttribute(event, getAttributeId())) {
                final MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
                final Object value = getValueForObject(event, getAttributeId());
                if (value instanceof String) {
                    final String valueAsString = (String) value;
                    final String toHash = metadata.getEntityId() + valueAsString;
                    md.update(toHash.getBytes("UTF-8"));
                    final byte[] digest = md.digest();
                    final BigInteger number = new BigInteger(1, digest);
                    final String hashedValue = number.toString(16);
                    this.setValueForObject(event, hashedValue, getAttributeId());
                } else {
                    throw new AttributeFilterException("The hash filter requires attributes of type String");
                }

            }
        } catch (final NoSuchAlgorithmException e) {
            throw new AttributeFilterException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new AttributeFilterException(e);
        }

    }

    /**
     * @param attributeId the attributeId to set
     */
    public void setAttributeId(final String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * @return the attributeId
     */
    public String getAttributeId() {
        return attributeId;
    }

}
