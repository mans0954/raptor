//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.structure;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.AnnotationsType;


/**
 * TargetIdentifiers are the set of objects against which reference metadata is reported (data providers, data flows, data or metadata structures, etc.). There are two types of TargetIdentifiers: the "full" target identifier, which lists every object used to attach metadata to in the metadata structure definition, and the partial target identifiers, which indicate sub-sets of those concepts against which reference metadata may be reported. It is sometimes the case that metadata will also be reported against the full target identifier. 
 * 			
 * 			An example of this is as follows: we might wish to collect some metadata concepts such as contact information for some of the objects described by the SDMX Information Model - for each instance of a metadata flow or a data provider, for example. Our concepts would be the concepts associated with contact information: CONTACT_NAME, CONTACT_PHONE_NUMBER, etc. We would determine how these concepts are associated with the objects in the model: do we want a contact for each data provider broken out by data flow? Or do we want only a single contact reported for each data provider (who might provide several data flows)? Each object or combination of objects we need to have metadata reported for becomes a partial target identifier, unless it happens to contain the full target identifier, in which case we use that instead. Thus, our valid partial target identifiers here would be "data flow" and "data provider", because "data flow by data provider" is a full target identifier. For each target identifier, we could have some set of our concepts reported.
 * 
 * <p>Java class for TargetIdentifiersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TargetIdentifiersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FullTargetIdentifier" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}FullTargetIdentifierType"/>
 *         &lt;element name="PartialTargetIdentifier" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}PartialTargetIdentifierType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Annotations" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}AnnotationsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetIdentifiersType", propOrder = {
    "fullTargetIdentifier",
    "partialTargetIdentifier",
    "annotations"
})
public class TargetIdentifiersType {

    @XmlElement(name = "FullTargetIdentifier", required = true)
    protected FullTargetIdentifierType fullTargetIdentifier;
    @XmlElement(name = "PartialTargetIdentifier")
    protected List<PartialTargetIdentifierType> partialTargetIdentifier;
    @XmlElement(name = "Annotations")
    protected AnnotationsType annotations;

    /**
     * Gets the value of the fullTargetIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link FullTargetIdentifierType }
     *     
     */
    public FullTargetIdentifierType getFullTargetIdentifier() {
        return fullTargetIdentifier;
    }

    /**
     * Sets the value of the fullTargetIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullTargetIdentifierType }
     *     
     */
    public void setFullTargetIdentifier(FullTargetIdentifierType value) {
        this.fullTargetIdentifier = value;
    }

    /**
     * Gets the value of the partialTargetIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partialTargetIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartialTargetIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartialTargetIdentifierType }
     * 
     * 
     */
    public List<PartialTargetIdentifierType> getPartialTargetIdentifier() {
        if (partialTargetIdentifier == null) {
            partialTargetIdentifier = new ArrayList<PartialTargetIdentifierType>();
        }
        return this.partialTargetIdentifier;
    }

    /**
     * Gets the value of the annotations property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationsType }
     *     
     */
    public AnnotationsType getAnnotations() {
        return annotations;
    }

    /**
     * Sets the value of the annotations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationsType }
     *     
     */
    public void setAnnotations(AnnotationsType value) {
        this.annotations = value;
    }

}
