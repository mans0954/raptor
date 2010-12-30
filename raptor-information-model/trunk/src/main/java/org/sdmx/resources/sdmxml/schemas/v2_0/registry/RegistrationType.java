//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.registry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.ActionType;


/**
 * Registration provides the information needed for data and reference metadata set registration. LastUpdated can provide a time stamp; ValidFrom and ValidTo allow for effectivity, so that data visibility from the registry can be controlled by the registrant. A Datasource must be supplied here if not already provided in the provision agreement. The data set or metadata set must be associated with a provision agreement,  a metadataflow, or a dataflow definition. If possible, the provision agreement should be specified. Only in cases where this is not possible should the dataflow or metadataflow be used.
 * 
 * <p>Java class for RegistrationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegistrationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LastUpdated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ValidFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ValidTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}ActionType"/>
 *         &lt;element name="Datasource" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry}DatasourceType" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="DataflowRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry}DataflowRefType"/>
 *           &lt;element name="MetadataflowRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry}MetadataflowRefType"/>
 *           &lt;element name="ProvisionAgreementRef" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry}ProvisionAgreementRefType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationType", propOrder = {
    "lastUpdated",
    "validFrom",
    "validTo",
    "action",
    "datasource",
    "dataflowRef",
    "metadataflowRef",
    "provisionAgreementRef"
})
public class RegistrationType {

    @XmlElement(name = "LastUpdated")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdated;
    @XmlElement(name = "ValidFrom")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validFrom;
    @XmlElement(name = "ValidTo")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validTo;
    @XmlElement(name = "Action", required = true)
    protected ActionType action;
    @XmlElement(name = "Datasource")
    protected DatasourceType datasource;
    @XmlElement(name = "DataflowRef")
    protected DataflowRefType dataflowRef;
    @XmlElement(name = "MetadataflowRef")
    protected MetadataflowRefType metadataflowRef;
    @XmlElement(name = "ProvisionAgreementRef")
    protected ProvisionAgreementRefType provisionAgreementRef;

    /**
     * Gets the value of the lastUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the value of the lastUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdated(XMLGregorianCalendar value) {
        this.lastUpdated = value;
    }

    /**
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidFrom(XMLGregorianCalendar value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the validTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidTo() {
        return validTo;
    }

    /**
     * Sets the value of the validTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidTo(XMLGregorianCalendar value) {
        this.validTo = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link ActionType }
     *     
     */
    public ActionType getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionType }
     *     
     */
    public void setAction(ActionType value) {
        this.action = value;
    }

    /**
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link DatasourceType }
     *     
     */
    public DatasourceType getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasourceType }
     *     
     */
    public void setDatasource(DatasourceType value) {
        this.datasource = value;
    }

    /**
     * Gets the value of the dataflowRef property.
     * 
     * @return
     *     possible object is
     *     {@link DataflowRefType }
     *     
     */
    public DataflowRefType getDataflowRef() {
        return dataflowRef;
    }

    /**
     * Sets the value of the dataflowRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataflowRefType }
     *     
     */
    public void setDataflowRef(DataflowRefType value) {
        this.dataflowRef = value;
    }

    /**
     * Gets the value of the metadataflowRef property.
     * 
     * @return
     *     possible object is
     *     {@link MetadataflowRefType }
     *     
     */
    public MetadataflowRefType getMetadataflowRef() {
        return metadataflowRef;
    }

    /**
     * Sets the value of the metadataflowRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetadataflowRefType }
     *     
     */
    public void setMetadataflowRef(MetadataflowRefType value) {
        this.metadataflowRef = value;
    }

    /**
     * Gets the value of the provisionAgreementRef property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisionAgreementRefType }
     *     
     */
    public ProvisionAgreementRefType getProvisionAgreementRef() {
        return provisionAgreementRef;
    }

    /**
     * Sets the value of the provisionAgreementRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisionAgreementRefType }
     *     
     */
    public void setProvisionAgreementRef(ProvisionAgreementRefType value) {
        this.provisionAgreementRef = value;
    }

}
