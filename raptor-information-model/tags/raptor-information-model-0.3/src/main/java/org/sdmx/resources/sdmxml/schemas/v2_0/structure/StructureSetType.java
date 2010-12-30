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
 * StructureSetType describes the relationships between two or more key families and/or metadata structure definitions, including the mapping between category schemes and concept schemes, to provide for the mapping of representations. This can include inheritance and extension of properties, or total or partial equivalencies. It also includes mapping of concepts existing in metadata structure definitions to those used in key families, and vice-versa. A human-readable name is provided in the Name element, which may include several language-specific variants. A longer human-readable description may also be provided, in the Description element, which may also have language-specific variants provided. The Annotations element may be used to provide annotations. The StructureRefs element references all of the key families and/or metadata structure definitions included in the Structure Set - these must be provided if a StructureMap element is used, but is not required if the structure set is only used to provide codelist mappings, concept mappings, or category mappings. The StructureMap element indicates which components in the included data and metadata structures are equivalent; CodelistMap indicates which codes map to other codelists. CategorySchemeMap indicates which categories in one scheme map to those in another scheme. ConceptSchemeMap indicates which concepts in one scheme map to those in another scheme. OrganisationSchemeMap describes how one organisation scheme maps to another. The id attribute takes an id which is unique to all structure sets maintained by the agency specified in the agency attribute. version specifies a version number (by default "1.0"). The uri attribute holds a URL where a valid SDMX Structure messgae can be found which provides full details of the StructureSet, and it must be used if the isExternalReference attribute has a value of true. The urn attribute holds a valid SDMX Registry URN as described in the SDMX Registry specification. A true value in the isFinal attribute indicates that the contents of the structure set may not be changed without versioning. The validFrom and validTo attributes provide inclusive dates for providing supplemental validity information about the version.
 * 
 * <p>Java class for StructureSetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TextType" maxOccurs="unbounded"/>
 *         &lt;element name="Description" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TextType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RelatedStructures" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}RelatedStructuresType" minOccurs="0"/>
 *         &lt;element name="StructureMap" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}StructureMapType" minOccurs="0"/>
 *         &lt;element name="CodelistMap" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}CodelistMapType" minOccurs="0"/>
 *         &lt;element name="CategorySchemeMap" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}CategorySchemeMapType" minOccurs="0"/>
 *         &lt;element name="ConceptSchemeMap" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}ConceptSchemeMapType" minOccurs="0"/>
 *         &lt;element name="OrganisationSchemeMap" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}OrganisationSchemeMapType" minOccurs="0"/>
 *         &lt;element name="Annotations" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}AnnotationsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="agencyID" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="urn" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="isFinal" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isExternalReference" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="validFrom" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TimePeriodType" />
 *       &lt;attribute name="validTo" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TimePeriodType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureSetType", propOrder = {
    "name",
    "description",
    "relatedStructures",
    "structureMap",
    "codelistMap",
    "categorySchemeMap",
    "conceptSchemeMap",
    "organisationSchemeMap",
    "annotations"
})
public class StructureSetType {

    @XmlElement(name = "Name", required = true)
    protected List<TextType> name;
    @XmlElement(name = "Description")
    protected List<TextType> description;
    @XmlElement(name = "RelatedStructures")
    protected RelatedStructuresType relatedStructures;
    @XmlElement(name = "StructureMap")
    protected StructureMapType structureMap;
    @XmlElement(name = "CodelistMap")
    protected CodelistMapType codelistMap;
    @XmlElement(name = "CategorySchemeMap")
    protected CategorySchemeMapType categorySchemeMap;
    @XmlElement(name = "ConceptSchemeMap")
    protected ConceptSchemeMapType conceptSchemeMap;
    @XmlElement(name = "OrganisationSchemeMap")
    protected OrganisationSchemeMapType organisationSchemeMap;
    @XmlElement(name = "Annotations")
    protected AnnotationsType annotations;
    @XmlAttribute(required = true)
    protected String id;
    @XmlAttribute
    protected String agencyID;
    @XmlAttribute
    protected String version;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String urn;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlAttribute
    protected Boolean isFinal;
    @XmlAttribute
    protected Boolean isExternalReference;
    @XmlAttribute
    protected String validFrom;
    @XmlAttribute
    protected String validTo;

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
     * Gets the value of the relatedStructures property.
     * 
     * @return
     *     possible object is
     *     {@link RelatedStructuresType }
     *     
     */
    public RelatedStructuresType getRelatedStructures() {
        return relatedStructures;
    }

    /**
     * Sets the value of the relatedStructures property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedStructuresType }
     *     
     */
    public void setRelatedStructures(RelatedStructuresType value) {
        this.relatedStructures = value;
    }

    /**
     * Gets the value of the structureMap property.
     * 
     * @return
     *     possible object is
     *     {@link StructureMapType }
     *     
     */
    public StructureMapType getStructureMap() {
        return structureMap;
    }

    /**
     * Sets the value of the structureMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link StructureMapType }
     *     
     */
    public void setStructureMap(StructureMapType value) {
        this.structureMap = value;
    }

    /**
     * Gets the value of the codelistMap property.
     * 
     * @return
     *     possible object is
     *     {@link CodelistMapType }
     *     
     */
    public CodelistMapType getCodelistMap() {
        return codelistMap;
    }

    /**
     * Sets the value of the codelistMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodelistMapType }
     *     
     */
    public void setCodelistMap(CodelistMapType value) {
        this.codelistMap = value;
    }

    /**
     * Gets the value of the categorySchemeMap property.
     * 
     * @return
     *     possible object is
     *     {@link CategorySchemeMapType }
     *     
     */
    public CategorySchemeMapType getCategorySchemeMap() {
        return categorySchemeMap;
    }

    /**
     * Sets the value of the categorySchemeMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategorySchemeMapType }
     *     
     */
    public void setCategorySchemeMap(CategorySchemeMapType value) {
        this.categorySchemeMap = value;
    }

    /**
     * Gets the value of the conceptSchemeMap property.
     * 
     * @return
     *     possible object is
     *     {@link ConceptSchemeMapType }
     *     
     */
    public ConceptSchemeMapType getConceptSchemeMap() {
        return conceptSchemeMap;
    }

    /**
     * Sets the value of the conceptSchemeMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConceptSchemeMapType }
     *     
     */
    public void setConceptSchemeMap(ConceptSchemeMapType value) {
        this.conceptSchemeMap = value;
    }

    /**
     * Gets the value of the organisationSchemeMap property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationSchemeMapType }
     *     
     */
    public OrganisationSchemeMapType getOrganisationSchemeMap() {
        return organisationSchemeMap;
    }

    /**
     * Sets the value of the organisationSchemeMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationSchemeMapType }
     *     
     */
    public void setOrganisationSchemeMap(OrganisationSchemeMapType value) {
        this.organisationSchemeMap = value;
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
     * Gets the value of the isFinal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFinal() {
        return isFinal;
    }

    /**
     * Sets the value of the isFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFinal(Boolean value) {
        this.isFinal = value;
    }

    /**
     * Gets the value of the isExternalReference property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsExternalReference() {
        return isExternalReference;
    }

    /**
     * Sets the value of the isExternalReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsExternalReference(Boolean value) {
        this.isExternalReference = value;
    }

    /**
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFrom(String value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the validTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * Sets the value of the validTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTo(String value) {
        this.validTo = value;
    }

}
