//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Attribute elements contain the (single) value of an attribute being queried for. The id attribute contains the id of the attribute. The attachmentLevel attribute specifies the attachment level of the attribute. If the content of Attribute is empty, then the search is for the specified attribute (and attachment level). If the name attribute is not specified, then the search is on any attribute. If the attachmentLevel attribute is not specified, then the query is for an attribute at any attachment level, as the value defaults to "Any".
 * 
 * <p>Java class for AttributeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttributeType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="attachmentLevel" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}AttachmentLevelType" default="Any" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeType", propOrder = {
    "value"
})
public class AttributeType {

    @XmlValue
    protected String value;
    @XmlAttribute
    protected String id;
    @XmlAttribute
    protected AttachmentLevelType attachmentLevel;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the attachmentLevel property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentLevelType }
     *     
     */
    public AttachmentLevelType getAttachmentLevel() {
        if (attachmentLevel == null) {
            return AttachmentLevelType.ANY;
        } else {
            return attachmentLevel;
        }
    }

    /**
     * Sets the value of the attachmentLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentLevelType }
     *     
     */
    public void setAttachmentLevel(AttachmentLevelType value) {
        this.attachmentLevel = value;
    }

}
