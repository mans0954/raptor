//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * ComponentMapType describes how a component (that is, dimension, attribute, or reported concept) in a referenced metadata structure definition or key family maps to a component in a referenced  "target" metadata structure definition or key family. The MapConceptRef contains the id of the concept in the metadata structure definition or key family referenced in the KeyFamilyRef or MetadataStructureRef element of the containing ComponentMap element. The MapTargetConceptRef contains the id of the concept in the metadata structure definition or key family referenced in the TargetKeyFamilyRef or TargetMetadataStructureRef element of the containing ComponentMap element. The RepresentationMapRef element contains a reference to the CodelistMap which describes how the coded representation of the referenced component maps to the coded representation of the target component. If the target component has an uncoded representation, then ToTextFormat is used to describe the un-coded representation to which the code of the referenced component should be transformed. The ToValueType element tells you whether the value, name, or description of the source value should be used in the resulting text field. The componentAlias attribute assigns a new ID to the relationship between these components. Note that of three components from different key families and/or metadata structure definitions are all equivalent, the two component maps can share a single alias. Note also that for metadata concepts which are represented not by codelists but rather by other types of item schemes (OrganisationSchemes or CategorySchemes), these can also be referenced using the RepresentationMapRef element. The preferredLanguage attribute specifies the language to use when translating coded values into their names or descriptions, if available, in the same form as xml:lang.
 * 
 * <p>Java class for ComponentMapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComponentMapType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MapConceptRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType"/>
 *         &lt;element name="MapTargetConceptRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType"/>
 *         &lt;choice>
 *           &lt;element name="RepresentationMapRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}RepresentationMapRefType"/>
 *           &lt;sequence>
 *             &lt;element name="ToTextFormat" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}TextFormatType"/>
 *             &lt;element name="ToValueType" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}ToValueTypeType"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="componentAlias" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="preferredLanguage" type="{http://www.w3.org/2001/XMLSchema}language" default="en" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponentMapType", propOrder = {
    "mapConceptRef",
    "mapTargetConceptRef",
    "representationMapRef",
    "toTextFormat",
    "toValueType"
})
public class ComponentMapType {

    @XmlElement(name = "MapConceptRef", required = true)
    protected String mapConceptRef;
    @XmlElement(name = "MapTargetConceptRef", required = true)
    protected String mapTargetConceptRef;
    @XmlElement(name = "RepresentationMapRef")
    protected RepresentationMapRefType representationMapRef;
    @XmlElement(name = "ToTextFormat")
    protected TextFormatType toTextFormat;
    @XmlElement(name = "ToValueType")
    protected ToValueTypeType toValueType;
    @XmlAttribute
    protected String componentAlias;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String preferredLanguage;

    /**
     * Gets the value of the mapConceptRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapConceptRef() {
        return mapConceptRef;
    }

    /**
     * Sets the value of the mapConceptRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapConceptRef(String value) {
        this.mapConceptRef = value;
    }

    /**
     * Gets the value of the mapTargetConceptRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapTargetConceptRef() {
        return mapTargetConceptRef;
    }

    /**
     * Sets the value of the mapTargetConceptRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapTargetConceptRef(String value) {
        this.mapTargetConceptRef = value;
    }

    /**
     * Gets the value of the representationMapRef property.
     * 
     * @return
     *     possible object is
     *     {@link RepresentationMapRefType }
     *     
     */
    public RepresentationMapRefType getRepresentationMapRef() {
        return representationMapRef;
    }

    /**
     * Sets the value of the representationMapRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link RepresentationMapRefType }
     *     
     */
    public void setRepresentationMapRef(RepresentationMapRefType value) {
        this.representationMapRef = value;
    }

    /**
     * Gets the value of the toTextFormat property.
     * 
     * @return
     *     possible object is
     *     {@link TextFormatType }
     *     
     */
    public TextFormatType getToTextFormat() {
        return toTextFormat;
    }

    /**
     * Sets the value of the toTextFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextFormatType }
     *     
     */
    public void setToTextFormat(TextFormatType value) {
        this.toTextFormat = value;
    }

    /**
     * Gets the value of the toValueType property.
     * 
     * @return
     *     possible object is
     *     {@link ToValueTypeType }
     *     
     */
    public ToValueTypeType getToValueType() {
        return toValueType;
    }

    /**
     * Sets the value of the toValueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToValueTypeType }
     *     
     */
    public void setToValueType(ToValueTypeType value) {
        this.toValueType = value;
    }

    /**
     * Gets the value of the componentAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentAlias() {
        return componentAlias;
    }

    /**
     * Sets the value of the componentAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentAlias(String value) {
        this.componentAlias = value;
    }

    /**
     * Gets the value of the preferredLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredLanguage() {
        if (preferredLanguage == null) {
            return "en";
        } else {
            return preferredLanguage;
        }
    }

    /**
     * Sets the value of the preferredLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredLanguage(String value) {
        this.preferredLanguage = value;
    }

}
