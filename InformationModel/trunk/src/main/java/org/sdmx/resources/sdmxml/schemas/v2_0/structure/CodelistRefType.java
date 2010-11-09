//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * The CodelistRefType provides the structure for a codelist reference. (Note that this is structured differently than a similarly-named type in the Registry namespace.) At a minimum, either: AgencyID has the ID of an agency as a value; CodelistID takes the ID of a codelist maintained by that agency; and Version specifies the version of the codelist; or URN supplies a valid SDMX Registry URN (see the SDMX Registry Specification). Alias is used to carry the identifier for the referenced codelist, so that codes from that list can be easily referenced by the CodeRefs contained in the parent Hierarchy, without having to repeat the agency and version for each reference. The Alias must be unique within the parent Hierarchical Codelist.
 * 
 * <p>Java class for CodelistRefType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodelistRefType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="URN" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="AgencyID" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" minOccurs="0"/>
 *         &lt;element name="CodelistID" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alias" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodelistRefType", propOrder = {
    "urn",
    "agencyID",
    "codelistID",
    "version",
    "alias"
})
public class CodelistRefType {

    @XmlElement(name = "URN")
    @XmlSchemaType(name = "anyURI")
    protected String urn;
    @XmlElement(name = "AgencyID")
    protected String agencyID;
    @XmlElement(name = "CodelistID")
    protected String codelistID;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "Alias")
    protected String alias;

    /**
     * Gets the value of the urn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURN() {
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
    public void setURN(String value) {
        this.urn = value;
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
     * Gets the value of the codelistID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodelistID() {
        return codelistID;
    }

    /**
     * Sets the value of the codelistID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodelistID(String value) {
        this.codelistID = value;
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
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

}
