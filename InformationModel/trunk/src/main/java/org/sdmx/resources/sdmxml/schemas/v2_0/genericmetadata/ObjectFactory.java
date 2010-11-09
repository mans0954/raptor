//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.genericmetadata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.sdmx.resources.sdmxml.schemas.v2_0.genericmetadata package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MetadataSet_QNAME = new QName("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/genericmetadata", "MetadataSet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.sdmx.resources.sdmxml.schemas.v2_0.genericmetadata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TargetValuesType }
     * 
     */
    public TargetValuesType createTargetValuesType() {
        return new TargetValuesType();
    }

    /**
     * Create an instance of {@link AttributeValueSetType }
     * 
     */
    public AttributeValueSetType createAttributeValueSetType() {
        return new AttributeValueSetType();
    }

    /**
     * Create an instance of {@link ReportedAttributeType }
     * 
     */
    public ReportedAttributeType createReportedAttributeType() {
        return new ReportedAttributeType();
    }

    /**
     * Create an instance of {@link ComponentValueType }
     * 
     */
    public ComponentValueType createComponentValueType() {
        return new ComponentValueType();
    }

    /**
     * Create an instance of {@link MetadataSetType }
     * 
     */
    public MetadataSetType createMetadataSetType() {
        return new MetadataSetType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/genericmetadata", name = "MetadataSet")
    public JAXBElement<MetadataSetType> createMetadataSet(MetadataSetType value) {
        return new JAXBElement<MetadataSetType>(_MetadataSet_QNAME, MetadataSetType.class, null, value);
    }

}
