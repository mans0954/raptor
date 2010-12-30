//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The KeyFamilyWhere element representes a query for a key family or key families. It contains all of the clauses in that query, represented by its child elements. Values are the IDs of the referenced object.
 * 
 * <p>Java class for KeyFamilyWhereType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KeyFamilyWhereType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="KeyFamily" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Dimension" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}DimensionType"/>
 *         &lt;element name="Attribute" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}AttributeType"/>
 *         &lt;element name="Codelist" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}CodelistType"/>
 *         &lt;element name="Category" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}CategoryType"/>
 *         &lt;element name="Concept" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AgencyID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Or" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}OrType"/>
 *         &lt;element name="And" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}AndType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyFamilyWhereType", propOrder = {
    "keyFamily",
    "dimension",
    "attribute",
    "codelist",
    "category",
    "concept",
    "agencyID",
    "version",
    "or",
    "and"
})
public class KeyFamilyWhereType {

    @XmlElement(name = "KeyFamily")
    protected String keyFamily;
    @XmlElement(name = "Dimension")
    protected DimensionType dimension;
    @XmlElement(name = "Attribute")
    protected AttributeType attribute;
    @XmlElement(name = "Codelist")
    protected CodelistType codelist;
    @XmlElement(name = "Category")
    protected CategoryType category;
    @XmlElement(name = "Concept")
    protected String concept;
    @XmlElement(name = "AgencyID")
    protected String agencyID;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "Or")
    protected OrType or;
    @XmlElement(name = "And")
    protected AndType and;

    /**
     * Gets the value of the keyFamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyFamily() {
        return keyFamily;
    }

    /**
     * Sets the value of the keyFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyFamily(String value) {
        this.keyFamily = value;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * @return
     *     possible object is
     *     {@link DimensionType }
     *     
     */
    public DimensionType getDimension() {
        return dimension;
    }

    /**
     * Sets the value of the dimension property.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionType }
     *     
     */
    public void setDimension(DimensionType value) {
        this.dimension = value;
    }

    /**
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeType }
     *     
     */
    public AttributeType getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeType }
     *     
     */
    public void setAttribute(AttributeType value) {
        this.attribute = value;
    }

    /**
     * Gets the value of the codelist property.
     * 
     * @return
     *     possible object is
     *     {@link CodelistType }
     *     
     */
    public CodelistType getCodelist() {
        return codelist;
    }

    /**
     * Sets the value of the codelist property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodelistType }
     *     
     */
    public void setCodelist(CodelistType value) {
        this.codelist = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryType }
     *     
     */
    public CategoryType getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryType }
     *     
     */
    public void setCategory(CategoryType value) {
        this.category = value;
    }

    /**
     * Gets the value of the concept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcept() {
        return concept;
    }

    /**
     * Sets the value of the concept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcept(String value) {
        this.concept = value;
    }

    /**
     * Gets the value of the agencyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyID() {
        return agencyID;
    }

    /**
     * Sets the value of the agencyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyID(String value) {
        this.agencyID = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the or property.
     * 
     * @return
     *     possible object is
     *     {@link OrType }
     *     
     */
    public OrType getOr() {
        return or;
    }

    /**
     * Sets the value of the or property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrType }
     *     
     */
    public void setOr(OrType value) {
        this.or = value;
    }

    /**
     * Gets the value of the and property.
     * 
     * @return
     *     possible object is
     *     {@link AndType }
     *     
     */
    public AndType getAnd() {
        return and;
    }

    /**
     * Sets the value of the and property.
     * 
     * @param value
     *     allowed object is
     *     {@link AndType }
     *     
     */
    public void setAnd(AndType value) {
        this.and = value;
    }

}
