/**
 *
 */
package uk.ac.cardiff.raptor.attribute.filtering;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.attribute.filtering.match.MatchRule;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * @author philsmart
 *
 */
public class NullAttributeRule extends AttributeRule{

    /* needs to be the exact field name of the attribute */
    private String attributeId;


    public void filterAttribute(Event event, ServiceMetadata metadata) {
        String attributeID = getAttributeId();
        if (classHasAttribute(event, attributeID)) {
               nullAttribute(event, attributeID);
        }
    }


    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }
    public String getAttributeId() {
        return attributeId;
    }


}
