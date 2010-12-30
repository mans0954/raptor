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
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.AnnotationsType;


/**
 * CrossSectionalMeasureType describes the observation values for multiple-measure cross-sectional data formats. For non-cross sectional key families, it is not necesary to specify any cross-sectional measures.The concept attribute points to the unique concept represented by the measure. The measureDimension attribute contains the concept name of the measure dimension. The code attribute contains the value of its corresponding code in the codelist used to represent the measure dimension. A CrossSectionalMeasure must be declared for each code in the codelist used to represent the measure dimension - these will replace the primary measure for multiple-measure cross-sectional data formats.The TextFormat element allows description of the contents of the observation value. The codelist attribute references a codelist if the observation value is coded. If this attribute is used, then codelistAgencyID must contain the ID of the maintenance agency of the referenced codelist. The codelistVersion attribute may be specified - if not, then the version of the referenced codelist is assumed to be "1.0".
 * 
 * <p>Java class for CrossSectionalMeasureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CrossSectionalMeasureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TextFormat" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}TextFormatType" minOccurs="0"/>
 *         &lt;element name="Annotations" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}AnnotationsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="conceptRef" use="required" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="conceptVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="conceptAgency" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="conceptSchemeRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="conceptSchemeAgency" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="codelist" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="codelistVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codelistAgency" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="measureDimension" use="required" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *       &lt;attribute name="code" use="required" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CrossSectionalMeasureType", propOrder = {
    "textFormat",
    "annotations"
})
public class CrossSectionalMeasureType {

    @XmlElement(name = "TextFormat")
    protected TextFormatType textFormat;
    @XmlElement(name = "Annotations")
    protected AnnotationsType annotations;
    @XmlAttribute(required = true)
    protected String conceptRef;
    @XmlAttribute
    protected String conceptVersion;
    @XmlAttribute
    protected String conceptAgency;
    @XmlAttribute
    protected String conceptSchemeRef;
    @XmlAttribute
    protected String conceptSchemeAgency;
    @XmlAttribute
    protected String codelist;
    @XmlAttribute
    protected String codelistVersion;
    @XmlAttribute
    protected String codelistAgency;
    @XmlAttribute(required = true)
    protected String measureDimension;
    @XmlAttribute(required = true)
    protected String code;

    /**
     * Gets the value of the textFormat property.
     * 
     * @return
     *     possible object is
     *     {@link TextFormatType }
     *     
     */
    public TextFormatType getTextFormat() {
        return textFormat;
    }

    /**
     * Sets the value of the textFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextFormatType }
     *     
     */
    public void setTextFormat(TextFormatType value) {
        this.textFormat = value;
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
     * Gets the value of the conceptRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptRef() {
        return conceptRef;
    }

    /**
     * Sets the value of the conceptRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptRef(String value) {
        this.conceptRef = value;
    }

    /**
     * Gets the value of the conceptVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptVersion() {
        return conceptVersion;
    }

    /**
     * Sets the value of the conceptVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptVersion(String value) {
        this.conceptVersion = value;
    }

    /**
     * Gets the value of the conceptAgency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptAgency() {
        return conceptAgency;
    }

    /**
     * Sets the value of the conceptAgency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptAgency(String value) {
        this.conceptAgency = value;
    }

    /**
     * Gets the value of the conceptSchemeRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptSchemeRef() {
        return conceptSchemeRef;
    }

    /**
     * Sets the value of the conceptSchemeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptSchemeRef(String value) {
        this.conceptSchemeRef = value;
    }

    /**
     * Gets the value of the conceptSchemeAgency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptSchemeAgency() {
        return conceptSchemeAgency;
    }

    /**
     * Sets the value of the conceptSchemeAgency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptSchemeAgency(String value) {
        this.conceptSchemeAgency = value;
    }

    /**
     * Gets the value of the codelist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodelist() {
        return codelist;
    }

    /**
     * Sets the value of the codelist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodelist(String value) {
        this.codelist = value;
    }

    /**
     * Gets the value of the codelistVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodelistVersion() {
        return codelistVersion;
    }

    /**
     * Sets the value of the codelistVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodelistVersion(String value) {
        this.codelistVersion = value;
    }

    /**
     * Gets the value of the codelistAgency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodelistAgency() {
        return codelistAgency;
    }

    /**
     * Sets the value of the codelistAgency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodelistAgency(String value) {
        this.codelistAgency = value;
    }

    /**
     * Gets the value of the measureDimension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureDimension() {
        return measureDimension;
    }

    /**
     * Sets the value of the measureDimension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureDimension(String value) {
        this.measureDimension = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

}
