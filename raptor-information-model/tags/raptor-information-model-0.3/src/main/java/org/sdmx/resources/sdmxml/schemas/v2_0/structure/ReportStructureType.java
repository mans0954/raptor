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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.TextType;


/**
 * The report structure describes the presentation of the reported concepts, and associates them with target identifiers, full or partial. It can be given a name and/or annotations. It must be given an ID, using the id attribute, which must be unique within the MetadataStructureDefinition element. It contains one or more MetadataAttribute elements, each of which may either hold a value, or may have subordinate MetadataAttribute elements. The target attribute holds the ID of a full or partial identifier, which is the identifier of the target against which the metadata attributes are reported.
 * 
 * <p>Java class for ReportStructureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportStructureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TextType" maxOccurs="unbounded"/>
 *         &lt;element name="Description" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TextType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MetadataAttribute" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}MetadataAttributeType" maxOccurs="unbounded"/>
 *         &lt;element name="Annotations" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}AnnotationsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="urn" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="target" use="required" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportStructureType", propOrder = {
    "name",
    "description",
    "metadataAttribute",
    "annotations"
})
public class ReportStructureType {

    @XmlElement(name = "Name", required = true)
    protected List<TextType> name;
    @XmlElement(name = "Description")
    protected List<TextType> description;
    @XmlElement(name = "MetadataAttribute", required = true)
    protected List<MetadataAttributeType> metadataAttribute;
    @XmlElement(name = "Annotations")
    protected AnnotationsType annotations;
    @XmlAttribute(required = true)
    protected String id;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String urn;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlAttribute(required = true)
    protected String target;

    /**
     * Gets the value of the name property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getName() {
        if (name == null) {
            name = new ArrayList<TextType>();
        }
        return this.name;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getDescription() {
        if (description == null) {
            description = new ArrayList<TextType>();
        }
        return this.description;
    }

    /**
     * Gets the value of the metadataAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metadataAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetadataAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetadataAttributeType }
     * 
     * 
     */
    public List<MetadataAttributeType> getMetadataAttribute() {
        if (metadataAttribute == null) {
            metadataAttribute = new ArrayList<MetadataAttributeType>();
        }
        return this.metadataAttribute;
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
     * Gets the value of the urn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrn() {
        return urn;
    }

    /**
     * Sets the value of the urn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrn(String value) {
        this.urn = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

}
