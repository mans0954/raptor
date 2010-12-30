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
 * <p>Java class for RepresentationTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RepresentationTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="Codelist"/>
 *     &lt;enumeration value="CategoryScheme"/>
 *     &lt;enumeration value="OrganisationScheme"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RepresentationTypeType")
@XmlEnum
public enum RepresentationTypeType {


    /**
     * Codelist
     * 
     */
    @XmlEnumValue("Codelist")
    CODELIST("Codelist"),

    /**
     * CategoryScheme
     * 
     */
    @XmlEnumValue("CategoryScheme")
    CATEGORY_SCHEME("CategoryScheme"),

    /**
     * OrganisationScheme
     * 
     */
    @XmlEnumValue("OrganisationScheme")
    ORGANISATION_SCHEME("OrganisationScheme");
    private final String value;

    RepresentationTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RepresentationTypeType fromValue(String v) {
        for (RepresentationTypeType c: RepresentationTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
