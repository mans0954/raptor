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
 * <p>Java class for RepresentationSchemeTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RepresentationSchemeTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="Codelist"/>
 *     &lt;enumeration value="Concept"/>
 *     &lt;enumeration value="Category"/>
 *     &lt;enumeration value="Organisation"/>
 *     &lt;enumeration value="External"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RepresentationSchemeTypeType")
@XmlEnum
public enum RepresentationSchemeTypeType {


    /**
     * Representation scheme is a codelist.
     * 
     */
    @XmlEnumValue("Codelist")
    CODELIST("Codelist"),

    /**
     * Representation scheme is a concept scheme.
     * 
     */
    @XmlEnumValue("Concept")
    CONCEPT("Concept"),

    /**
     * Representation scheme is a category scheme.
     * 
     */
    @XmlEnumValue("Category")
    CATEGORY("Category"),

    /**
     * Representation scheme is an organisation scheme.
     * 
     */
    @XmlEnumValue("Organisation")
    ORGANISATION("Organisation"),

    /**
     * Representation scheme is "external" to the known model - that is, it cannot be enumerated at the time the report is designed. This will only be valid if some maintained and changing object is to have metadata reported against it: for example, if the concepts of dimension objects are to be reported against for all of an agencies' key families, then it is not possible at design time to enumerate all of the concepts which will be used by that agencies' key families into the future. This value should not be used unless absolutely necessary, as it reduces the processability of the metadata report generated.
     * 
     */
    @XmlEnumValue("External")
    EXTERNAL("External");
    private final String value;

    RepresentationSchemeTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RepresentationSchemeTypeType fromValue(String v) {
        for (RepresentationSchemeTypeType c: RepresentationSchemeTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
