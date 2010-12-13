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
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.TextType;


/**
 * TransitionType describes the Condition and next step in a transition. The Condition text is informational, and may be supplied in multiple, parallel-language form. The TargetStep holds the id of the next step in the process if the condition is met.
 * 
 * <p>Java class for TransitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TargetStep" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}IDType" minOccurs="0"/>
 *         &lt;element name="Condition" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common}TextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransitionType", propOrder = {
    "targetStep",
    "condition"
})
public class TransitionType {

    @XmlElement(name = "TargetStep")
    protected String targetStep;
    @XmlElement(name = "Condition")
    protected TextType condition;

    /**
     * Gets the value of the targetStep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetStep() {
        return targetStep;
    }

    /**
     * Sets the value of the targetStep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetStep(String value) {
        this.targetStep = value;
    }

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setCondition(TextType value) {
        this.condition = value;
    }

}
