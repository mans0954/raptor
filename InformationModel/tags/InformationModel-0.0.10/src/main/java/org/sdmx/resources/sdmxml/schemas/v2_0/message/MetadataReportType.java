//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.metadatareport.MetadataSetType;


/**
 * MetadataReportType defines the contents of a metadata structure definition-specific Metadata Report message.
 * 
 * <p>Java class for MetadataReportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MetadataReportType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message}MessageType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/metadatareport}MetadataSet"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetadataReportType", propOrder = {
    "metadataSet"
})
public class MetadataReportType
    extends MessageType
{

    @XmlElement(name = "MetadataSet", namespace = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/metadatareport", required = true)
    protected MetadataSetType metadataSet;

    /**
     * Gets the value of the metadataSet property.
     * 
     * @return
     *     possible object is
     *     {@link MetadataSetType }
     *     
     */
    public MetadataSetType getMetadataSet() {
        return metadataSet;
    }

    /**
     * Sets the value of the metadataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetadataSetType }
     *     
     */
    public void setMetadataSet(MetadataSetType value) {
        this.metadataSet = value;
    }

}
