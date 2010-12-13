//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.structure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsageStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UsageStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="Mandatory"/>
 *     &lt;enumeration value="Conditional"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UsageStatusType")
@XmlEnum
public enum UsageStatusType {


    /**
     * Reporting the associated attribute is mandatory - a value must be supplied.
     * 
     */
    @XmlEnumValue("Mandatory")
    MANDATORY("Mandatory"),

    /**
     * Reporting the associated attribute is not mandatory - a value may  be supplied, but is not required.
     * 
     */
    @XmlEnumValue("Conditional")
    CONDITIONAL("Conditional");
    private final String value;

    UsageStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsageStatusType fromValue(String v) {
        for (UsageStatusType c: UsageStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
