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
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CategorySchemesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CodeListsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.ConceptsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.DataflowsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.HierarchicalCodelistsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.KeyFamiliesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.MetadataStructureDefinitionsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.MetadataflowsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.OrganisationSchemesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.ProcessesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.ReportingTaxonomiesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.StructureSetsType;


/**
 * QueryStructureResponse is sent in response to a QueryStructureRequest. It carries the status of the response, with any relevant error messages, and then also carries all information found in the rersult set.
 * 
 * <p>Java class for QueryStructureResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryStructureResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusMessage" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry}StatusMessageType"/>
 *         &lt;element name="OrganisationSchemes" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}OrganisationSchemesType" minOccurs="0"/>
 *         &lt;element name="Dataflows" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}DataflowsType" minOccurs="0"/>
 *         &lt;element name="Metadataflows" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}MetadataflowsType" minOccurs="0"/>
 *         &lt;element name="CategorySchemes" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}CategorySchemesType" minOccurs="0"/>
 *         &lt;element name="CodeLists" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}CodeListsType" minOccurs="0"/>
 *         &lt;element name="HierarchicalCodelists" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}HierarchicalCodelistsType" minOccurs="0"/>
 *         &lt;element name="Concepts" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}ConceptsType" minOccurs="0"/>
 *         &lt;element name="MetadataStructureDefinitions" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}MetadataStructureDefinitionsType" minOccurs="0"/>
 *         &lt;element name="KeyFamilies" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}KeyFamiliesType" minOccurs="0"/>
 *         &lt;element name="StructureSets" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}StructureSetsType" minOccurs="0"/>
 *         &lt;element name="ReportingTaxonomies" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}ReportingTaxonomiesType" minOccurs="0"/>
 *         &lt;element name="Processes" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure}ProcessesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryStructureResponseType", propOrder = {
    "statusMessage",
    "organisationSchemes",
    "dataflows",
    "metadataflows",
    "categorySchemes",
    "codeLists",
    "hierarchicalCodelists",
    "concepts",
    "metadataStructureDefinitions",
    "keyFamilies",
    "structureSets",
    "reportingTaxonomies",
    "processes"
})
public class QueryStructureResponseType {

    @XmlElement(name = "StatusMessage", required = true)
    protected StatusMessageType statusMessage;
    @XmlElement(name = "OrganisationSchemes")
    protected OrganisationSchemesType organisationSchemes;
    @XmlElement(name = "Dataflows")
    protected DataflowsType dataflows;
    @XmlElement(name = "Metadataflows")
    protected MetadataflowsType metadataflows;
    @XmlElement(name = "CategorySchemes")
    protected CategorySchemesType categorySchemes;
    @XmlElement(name = "CodeLists")
    protected CodeListsType codeLists;
    @XmlElement(name = "HierarchicalCodelists")
    protected HierarchicalCodelistsType hierarchicalCodelists;
    @XmlElement(name = "Concepts")
    protected ConceptsType concepts;
    @XmlElement(name = "MetadataStructureDefinitions")
    protected MetadataStructureDefinitionsType metadataStructureDefinitions;
    @XmlElement(name = "KeyFamilies")
    protected KeyFamiliesType keyFamilies;
    @XmlElement(name = "StructureSets")
    protected StructureSetsType structureSets;
    @XmlElement(name = "ReportingTaxonomies")
    protected ReportingTaxonomiesType reportingTaxonomies;
    @XmlElement(name = "Processes")
    protected ProcessesType processes;

    /**
     * Gets the value of the statusMessage property.
     * 
     * @return
     *     possible object is
     *     {@link StatusMessageType }
     *     
     */
    public StatusMessageType getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the value of the statusMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusMessageType }
     *     
     */
    public void setStatusMessage(StatusMessageType value) {
        this.statusMessage = value;
    }

    /**
     * Gets the value of the organisationSchemes property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationSchemesType }
     *     
     */
    public OrganisationSchemesType getOrganisationSchemes() {
        return organisationSchemes;
    }

    /**
     * Sets the value of the organisationSchemes property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationSchemesType }
     *     
     */
    public void setOrganisationSchemes(OrganisationSchemesType value) {
        this.organisationSchemes = value;
    }

    /**
     * Gets the value of the dataflows property.
     * 
     * @return
     *     possible object is
     *     {@link DataflowsType }
     *     
     */
    public DataflowsType getDataflows() {
        return dataflows;
    }

    /**
     * Sets the value of the dataflows property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataflowsType }
     *     
     */
    public void setDataflows(DataflowsType value) {
        this.dataflows = value;
    }

    /**
     * Gets the value of the metadataflows property.
     * 
     * @return
     *     possible object is
     *     {@link MetadataflowsType }
     *     
     */
    public MetadataflowsType getMetadataflows() {
        return metadataflows;
    }

    /**
     * Sets the value of the metadataflows property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetadataflowsType }
     *     
     */
    public void setMetadataflows(MetadataflowsType value) {
        this.metadataflows = value;
    }

    /**
     * Gets the value of the categorySchemes property.
     * 
     * @return
     *     possible object is
     *     {@link CategorySchemesType }
     *     
     */
    public CategorySchemesType getCategorySchemes() {
        return categorySchemes;
    }

    /**
     * Sets the value of the categorySchemes property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategorySchemesType }
     *     
     */
    public void setCategorySchemes(CategorySchemesType value) {
        this.categorySchemes = value;
    }

    /**
     * Gets the value of the codeLists property.
     * 
     * @return
     *     possible object is
     *     {@link CodeListsType }
     *     
     */
    public CodeListsType getCodeLists() {
        return codeLists;
    }

    /**
     * Sets the value of the codeLists property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeListsType }
     *     
     */
    public void setCodeLists(CodeListsType value) {
        this.codeLists = value;
    }

    /**
     * Gets the value of the hierarchicalCodelists property.
     * 
     * @return
     *     possible object is
     *     {@link HierarchicalCodelistsType }
     *     
     */
    public HierarchicalCodelistsType getHierarchicalCodelists() {
        return hierarchicalCodelists;
    }

    /**
     * Sets the value of the hierarchicalCodelists property.
     * 
     * @param value
     *     allowed object is
     *     {@link HierarchicalCodelistsType }
     *     
     */
    public void setHierarchicalCodelists(HierarchicalCodelistsType value) {
        this.hierarchicalCodelists = value;
    }

    /**
     * Gets the value of the concepts property.
     * 
     * @return
     *     possible object is
     *     {@link ConceptsType }
     *     
     */
    public ConceptsType getConcepts() {
        return concepts;
    }

    /**
     * Sets the value of the concepts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConceptsType }
     *     
     */
    public void setConcepts(ConceptsType value) {
        this.concepts = value;
    }

    /**
     * Gets the value of the metadataStructureDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link MetadataStructureDefinitionsType }
     *     
     */
    public MetadataStructureDefinitionsType getMetadataStructureDefinitions() {
        return metadataStructureDefinitions;
    }

    /**
     * Sets the value of the metadataStructureDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetadataStructureDefinitionsType }
     *     
     */
    public void setMetadataStructureDefinitions(MetadataStructureDefinitionsType value) {
        this.metadataStructureDefinitions = value;
    }

    /**
     * Gets the value of the keyFamilies property.
     * 
     * @return
     *     possible object is
     *     {@link KeyFamiliesType }
     *     
     */
    public KeyFamiliesType getKeyFamilies() {
        return keyFamilies;
    }

    /**
     * Sets the value of the keyFamilies property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyFamiliesType }
     *     
     */
    public void setKeyFamilies(KeyFamiliesType value) {
        this.keyFamilies = value;
    }

    /**
     * Gets the value of the structureSets property.
     * 
     * @return
     *     possible object is
     *     {@link StructureSetsType }
     *     
     */
    public StructureSetsType getStructureSets() {
        return structureSets;
    }

    /**
     * Sets the value of the structureSets property.
     * 
     * @param value
     *     allowed object is
     *     {@link StructureSetsType }
     *     
     */
    public void setStructureSets(StructureSetsType value) {
        this.structureSets = value;
    }

    /**
     * Gets the value of the reportingTaxonomies property.
     * 
     * @return
     *     possible object is
     *     {@link ReportingTaxonomiesType }
     *     
     */
    public ReportingTaxonomiesType getReportingTaxonomies() {
        return reportingTaxonomies;
    }

    /**
     * Sets the value of the reportingTaxonomies property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportingTaxonomiesType }
     *     
     */
    public void setReportingTaxonomies(ReportingTaxonomiesType value) {
        this.reportingTaxonomies = value;
    }

    /**
     * Gets the value of the processes property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessesType }
     *     
     */
    public ProcessesType getProcesses() {
        return processes;
    }

    /**
     * Sets the value of the processes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessesType }
     *     
     */
    public void setProcesses(ProcessesType value) {
        this.processes = value;
    }

}
