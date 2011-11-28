
package uk.ac.cardiff.raptor.event.expansion.connector;

import uk.ac.cardiff.raptor.event.expansion.connector.RDBMSDataConnector.DATA_TYPES;

/**
 * Describes how to express a given result set column as an attribute and value.
 */
public class RDBMSColumnDescriptor {

    /** Name of the database column. */
    private String columnName;

    /** Name of the attribute to map the column to. */
    private String attributeName;

    /** Java data type to express the database value as. */
    private DATA_TYPES dataType;

    /**
     * Constructor.
     * 
     * @param column name of the database column
     * @param attribute name of the attribute to map the column to
     * @param type Java data type to express the database value as
     */
    public RDBMSColumnDescriptor(String column, String attribute, DATA_TYPES type) {
        columnName = column;
        attributeName = attribute;
        dataType = type;
    }

    /**
     * Gets the name of the database column.
     * 
     * @return name of the database column
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Gets the name of the attribute to map the column to.
     * 
     * @return name of the attribute to map the column to
     */
    public String getAttributeID() {
        return attributeName;
    }

    /**
     * Gets the Java data type to express the database value as.
     * 
     * @return Java data type to express the database value as
     */
    public DATA_TYPES getDataType() {
        return dataType;
    }

    /** {@inheritDoc} */
    public String toString() {
        return "RBDMSColumnDescriptor{columnName=" + columnName + ", attributeId=" + attributeName + ", dataType="
                + dataType + "}";
    }
}