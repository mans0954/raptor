//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.query;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.sdmx.resources.sdmxml.schemas.v2_0.query package. 
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

    private final static QName _Query_QNAME = new QName("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query", "Query");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.sdmx.resources.sdmxml.schemas.v2_0.query
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AttributeType }
     * 
     */
    public AttributeType createAttributeType() {
        return new AttributeType();
    }

    /**
     * Create an instance of {@link DataWhereType }
     * 
     */
    public DataWhereType createDataWhereType() {
        return new DataWhereType();
    }

    /**
     * Create an instance of {@link ReportingTaxonomyWhereType }
     * 
     */
    public ReportingTaxonomyWhereType createReportingTaxonomyWhereType() {
        return new ReportingTaxonomyWhereType();
    }

    /**
     * Create an instance of {@link DimensionType }
     * 
     */
    public DimensionType createDimensionType() {
        return new DimensionType();
    }

    /**
     * Create an instance of {@link DataflowWhereType }
     * 
     */
    public DataflowWhereType createDataflowWhereType() {
        return new DataflowWhereType();
    }

    /**
     * Create an instance of {@link StructureSetWhereType }
     * 
     */
    public StructureSetWhereType createStructureSetWhereType() {
        return new StructureSetWhereType();
    }

    /**
     * Create an instance of {@link StructureComponentType }
     * 
     */
    public StructureComponentType createStructureComponentType() {
        return new StructureComponentType();
    }

    /**
     * Create an instance of {@link CodelistType }
     * 
     */
    public CodelistType createCodelistType() {
        return new CodelistType();
    }

    /**
     * Create an instance of {@link CategoryType }
     * 
     */
    public CategoryType createCategoryType() {
        return new CategoryType();
    }

    /**
     * Create an instance of {@link DataProviderWhereType }
     * 
     */
    public DataProviderWhereType createDataProviderWhereType() {
        return new DataProviderWhereType();
    }

    /**
     * Create an instance of {@link MetadataWhereType }
     * 
     */
    public MetadataWhereType createMetadataWhereType() {
        return new MetadataWhereType();
    }

    /**
     * Create an instance of {@link ProcessWhereType }
     * 
     */
    public ProcessWhereType createProcessWhereType() {
        return new ProcessWhereType();
    }

    /**
     * Create an instance of {@link KeyFamilyWhereType }
     * 
     */
    public KeyFamilyWhereType createKeyFamilyWhereType() {
        return new KeyFamilyWhereType();
    }

    /**
     * Create an instance of {@link CategorySchemeWhereType }
     * 
     */
    public CategorySchemeWhereType createCategorySchemeWhereType() {
        return new CategorySchemeWhereType();
    }

    /**
     * Create an instance of {@link OrganisationSchemeWhereType }
     * 
     */
    public OrganisationSchemeWhereType createOrganisationSchemeWhereType() {
        return new OrganisationSchemeWhereType();
    }

    /**
     * Create an instance of {@link ConceptWhereType }
     * 
     */
    public ConceptWhereType createConceptWhereType() {
        return new ConceptWhereType();
    }

    /**
     * Create an instance of {@link HierarchicalCodelistWhereType }
     * 
     */
    public HierarchicalCodelistWhereType createHierarchicalCodelistWhereType() {
        return new HierarchicalCodelistWhereType();
    }

    /**
     * Create an instance of {@link AndType }
     * 
     */
    public AndType createAndType() {
        return new AndType();
    }

    /**
     * Create an instance of {@link MetadataStructureWhereType }
     * 
     */
    public MetadataStructureWhereType createMetadataStructureWhereType() {
        return new MetadataStructureWhereType();
    }

    /**
     * Create an instance of {@link MetadataflowWhereType }
     * 
     */
    public MetadataflowWhereType createMetadataflowWhereType() {
        return new MetadataflowWhereType();
    }

    /**
     * Create an instance of {@link TimeType }
     * 
     */
    public TimeType createTimeType() {
        return new TimeType();
    }

    /**
     * Create an instance of {@link OrType }
     * 
     */
    public OrType createOrType() {
        return new OrType();
    }

    /**
     * Create an instance of {@link CodelistWhereType }
     * 
     */
    public CodelistWhereType createCodelistWhereType() {
        return new CodelistWhereType();
    }

    /**
     * Create an instance of {@link QueryType }
     * 
     */
    public QueryType createQueryType() {
        return new QueryType();
    }

    /**
     * Create an instance of {@link AgencyWhereType }
     * 
     */
    public AgencyWhereType createAgencyWhereType() {
        return new AgencyWhereType();
    }

    /**
     * Create an instance of {@link ConceptSchemeWhereType }
     * 
     */
    public ConceptSchemeWhereType createConceptSchemeWhereType() {
        return new ConceptSchemeWhereType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query", name = "Query")
    public JAXBElement<QueryType> createQuery(QueryType value) {
        return new JAXBElement<QueryType>(_Query_QNAME, QueryType.class, null, value);
    }

}
