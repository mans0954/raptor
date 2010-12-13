//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.query;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachmentLevelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AttachmentLevelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="DataSet"/>
 *     &lt;enumeration value="Group"/>
 *     &lt;enumeration value="Series"/>
 *     &lt;enumeration value="Observation"/>
 *     &lt;enumeration value="Any"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AttachmentLevelType")
@XmlEnum
public enum AttachmentLevelType {


    /**
     * Attached at the Data Set level
     * 
     */
    @XmlEnumValue("DataSet")
    DATA_SET("DataSet"),

    /**
     * Attached at the Group level
     * 
     */
    @XmlEnumValue("Group")
    GROUP("Group"),

    /**
     * Attached at the Series level
     * 
     */
    @XmlEnumValue("Series")
    SERIES("Series"),

    /**
     * Attached at the Observation level
     * 
     */
    @XmlEnumValue("Observation")
    OBSERVATION("Observation"),

    /**
     * Attached at any attachment level
     * 
     */
    @XmlEnumValue("Any")
    ANY("Any");
    private final String value;

    AttachmentLevelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttachmentLevelType fromValue(String v) {
        for (AttachmentLevelType c: AttachmentLevelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
