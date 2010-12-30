//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.08 at 06:23:17 PM GMT 
//


package org.sdmx.resources.sdmxml.schemas.v2_0.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.sdmx.resources.sdmxml.schemas.v2_0.query.QueryType;


/**
 * QueryMessageType defines the contents of a QueryMessage.
 * 
 * <p>Java class for QueryMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryMessageType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message}MessageType">
 *       &lt;sequence>
 *         &lt;element name="Query" type="{http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query}QueryType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryMessageType", propOrder = {
    "query"
})
public class QueryMessageType
    extends MessageType
{

    @XmlElement(name = "Query", required = true)
    protected QueryType query;

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link QueryType }
     *     
     */
    public QueryType getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryType }
     *     
     */
    public void setQuery(QueryType value) {
        this.query = value;
    }

}
