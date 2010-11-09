//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.message;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * CompactDataType defines the contents of a CompactData message.
 * 
 * <p>Java class for CompactDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompactDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message}MessageType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/compact}DataSet"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompactDataType", propOrder = {
    "dataSet"
})
public class CompactDataType
    extends MessageType
{

    @XmlElementRef(name = "DataSet", namespace = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/compact", type = JAXBElement.class)
    protected JAXBElement<? extends org.sdmx.resources.sdmxml.schemas.v2_0.compact.DataSetType> dataSet;

    /**
     * Gets the value of the dataSet property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link org.sdmx.resources.sdmxml.schemas.v2_0.compact.DataSetType }{@code >}
     *     {@link JAXBElement }{@code <}{@link uk.ac.cardiff.raptor.sdmx.compact.DataSetType }{@code >}
     *     
     */
    public JAXBElement<? extends org.sdmx.resources.sdmxml.schemas.v2_0.compact.DataSetType> getDataSet() {
        return dataSet;
    }

    /**
     * Sets the value of the dataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link org.sdmx.resources.sdmxml.schemas.v2_0.compact.DataSetType }{@code >}
     *     {@link JAXBElement }{@code <}{@link uk.ac.cardiff.raptor.sdmx.compact.DataSetType }{@code >}
     *     
     */
    public void setDataSet(JAXBElement<? extends org.sdmx.resources.sdmxml.schemas.v2_0.compact.DataSetType> value) {
        this.dataSet = ((JAXBElement<? extends org.sdmx.resources.sdmxml.schemas.v2_0.compact.DataSetType> ) value);
    }

}
