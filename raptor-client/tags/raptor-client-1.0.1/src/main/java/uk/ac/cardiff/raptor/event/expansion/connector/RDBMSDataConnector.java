/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.cardiff.raptor.event.expansion.connector;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.xmlbeans.impl.xb.xmlschema.BaseAttribute;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data connector that can retrieve information from a relational database through JDBC, version 3.
 */
public class RDBMSDataConnector implements DataConnector {

    /** Data types understood by this connector. */
    public static enum DATA_TYPES {
        BigDecimal, Boolean, Byte, ByteArray, Date, Double, Float, Integer, Long, Object, Short, String, Time,
        Timestamp, URL
    };

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(RDBMSDataConnector.class);

    /** JDBC data source for retrieving connections. */
    private DataSource dataSource;

    /** SQL query timeout in seconds. */
    private int queryTimeout;

    /** Whether the JDBC connection is read-only. */
    private boolean readOnlyConnection;

    /** Whether queries might use stored procedures. */
    private boolean usesStoredProcedure;

    /** Whether an empty result set is an error. */
    private boolean noResultIsError;

    /** Data cache. */
    private Map<String, Map<String, Map<String, String>>> cache;

    /** Set of column descriptors for managing returned data. [columnName => colmentDescriptr] */
    private Map<String, RDBMSColumnDescriptor> columnDescriptors;

    /** The database search filter template */
    private String searchTemplate;

    /** Whether to cache search results for the duration specified by <code>cacheTimeoutMs</code>. */
    private boolean cacheResults;

    /** How long the cache remains valid before it is cleared */
    private long cacheTimeoutMs;

    /** The time at which the cache was last reset */
    private long cacheResetTimeMs;

    /** Whether this data connector has been initialized. */
    private boolean initialized;

    /**
     * Constructor.
     * 
     */
    public RDBMSDataConnector() {
        super();
    }

    /**
     * Initializes the cache and prepares it for use. {@link #initialize()} must be called first or this method does
     * nothing.
     */
    protected void initializeCache() {
        if (cacheResults && initialized) {
            cache = new HashMap<String, Map<String, Map<String, String>>>();
            cacheResetTimeMs = System.currentTimeMillis();
        }
    }

    /**
     * Initializes the connector.
     */
    public void initialise() {
        if (!initialized) {
            log.debug("Initialising RDBMS data connector");
            initialized = true;
            initializeCache();
            readOnlyConnection = true;
            usesStoredProcedure = false;
            noResultIsError = false;
            columnDescriptors = new HashMap<String, RDBMSColumnDescriptor>();
        }
    }

    /**
     * Gets the timeout, in seconds, of the SQL query.
     * 
     * @return timeout, in seconds, of the SQL query
     */
    public int getQueryTimeout() {
        return queryTimeout;
    }

    /**
     * Sets the timeout, in seconds, of the SQL query.
     * 
     * @param timeout timeout, in seconds, of the SQL query
     */
    public void setQueryTimeout(int timeout) {
        queryTimeout = timeout;
    }

    /**
     * Gets whether this data connector uses read-only connections.
     * 
     * @return whether this data connector uses read-only connections
     */
    public boolean isConnectionReadOnly() {
        return readOnlyConnection;
    }

    /**
     * Sets whether this data connector uses read-only connections.
     * 
     * @param isReadOnly whether this data connector uses read-only connections
     */
    public void setConnectionReadOnly(boolean isReadOnly) {
        readOnlyConnection = isReadOnly;
    }

    /**
     * Gets whether queries made use stored procedures.
     * 
     * @return whether queries made use stored procedures
     */
    public boolean getUsesStoredProcedure() {
        return usesStoredProcedure;
    }

    /**
     * Sets whether queries made use stored procedures.
     * 
     * @param storedProcedure whether queries made use stored procedures
     */
    public void setUsesStoredProcedure(boolean storedProcedure) {
        usesStoredProcedure = storedProcedure;
    }

    /**
     * This returns whether this connector will throw an exception if no search results are found. The default is false.
     * 
     * @return <code>boolean</code>
     */
    public boolean isNoResultIsError() {
        return noResultIsError;
    }

    /**
     * This sets whether this connector will throw an exception if no search results are found.
     * 
     * @param isError <code>boolean</code>
     */
    public void setNoResultIsError(boolean isError) {
        noResultIsError = isError;
    }

    /**
     * Gets the set of column descriptors used to deal with result set data. The name of the database column is the
     * map's key. This list is unmodifiable.
     * 
     * @return column descriptors used to deal with result set data
     */
    public Map<String, RDBMSColumnDescriptor> getColumnDescriptor() {
        return columnDescriptors;
    }

    /**
     * This removes all entries from the cache. {@link #initialize()} must be called first or this method does nothing.
     */
    protected void clearCache() {
        if (cacheResults && initialized) {
            cache.clear();
            cacheResetTimeMs = System.currentTimeMillis();
        }
    }

    private void checkCacheValidity() {
        if (cacheTimeoutMs == 0 || !cacheResults) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        boolean shouldReset = (currentTimeMillis - cacheResetTimeMs) > cacheTimeoutMs;
        log.trace("Should reset cache?{} - time left {}", shouldReset, (currentTimeMillis - cacheResetTimeMs));
        if (shouldReset) {
            log.info("Ldap cache was cleared, timeout reached");
            clearCache();
        }
    }

    /** {@inheritDoc} */
    public void validate() throws AttributeAssociationException {
        log.debug("RDBMS data connector - Validating configuration.");

        if (dataSource == null) {
            log.error("RDBMS data connector - Datasource is null");
            throw new AttributeAssociationException("Datasource is null");
        }

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                log.error("RDBMS data connector - Unable to create connections");
                throw new AttributeAssociationException("Unable to create connections for RDBMS data connector ");
            }

            DatabaseMetaData dbmd = connection.getMetaData();
            if (!dbmd.supportsStoredProcedures() && usesStoredProcedure) {
                log.error("RDBMS data connector - Database does not support stored procedures.");
                throw new AttributeAssociationException("Database does not support stored procedures.");
            }

            log.debug("RDBMS data connector - Connector configuration is valid.");
        } catch (SQLException e) {
            if (e.getSQLState() != null) {
                log.error("RDBMS data connector - Invalid connector configuration; SQL state: {}, SQL Code: {}",
                        new Object[] {e.getSQLState(), e.getErrorCode()}, e);
            } else {
                log.error("RDBMS data connector - Invalid connector configuration", e);
            }
            throw new AttributeAssociationException("Invalid connector configuration", e);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("RDBMS data connector - Error closing database connection; SQL State: {}, SQL Code: {}",
                        new Object[] {e.getSQLState(), e.getErrorCode()}, e);
            }
        }
    }

    /** {@inheritDoc} */
    public Map<String, String> lookup(String principal) throws AttributeAssociationException {

        String query = searchTemplate.replace("[principal]", principal);
        query = query.trim();

        checkCacheValidity();

        Map<String, String> resolvedAttributes = null;
        if (cacheResults) {
            resolvedAttributes = retrieveAttributesFromCache(principal, query);
        }

        if (resolvedAttributes == null) {
            resolvedAttributes = retrieveAttributesFromDatabase(query);
        }

        if (cacheResults) {
            cacheResult(principal, query, resolvedAttributes);
        }

        return resolvedAttributes;
    }

    /**
     * Attempts to retrieve the attributes from the cache.
     * 
     * @param principal the principal name of the user the attributes are for
     * @param query query used to generate the attributes
     * 
     * @return cached attributes
     * 
     * @throws AttributeResolutionException thrown if there is a problem retrieving data from the cache
     */
    protected Map<String, String> retrieveAttributesFromCache(String principal, String query)
            throws AttributeAssociationException {

        Map<String, String> attributes = null;
        if (cacheResults) {
            if (cache.containsKey(principal)) {
                Map<String, Map<String, String>> results = cache.get(principal);
                attributes = results.get(query);
                log.trace("RDBMS data connector - Fetched attributes from cache for principal {}", principal);
            }
        }
        return attributes;

    }

    /**
     * Attributes returned from the database connector are defined by the SELECT statement in the SQL query, not through
     * the <code>attributes</code> array. This is a no-op method.
     */
    public void setReturnAttributes(String[] attributes) {

    }

    /**
     * Attempts to retrieve the attribute from the database.
     * 
     * @param query query used to get the attributes
     * 
     * @return attributes gotten from the database
     * 
     * @throws AttributeResolutionException thrown if there is a problem retrieving data from the database or
     *             transforming that data into {@link BaseAttribute}s
     */
    protected Map<String, String> retrieveAttributesFromDatabase(String query) throws AttributeAssociationException {
        Map<String, String> resolvedAttributes;
        Connection connection = null;
        ResultSet queryResult = null;

        try {
            connection = dataSource.getConnection();
            if (readOnlyConnection) {
                connection.setReadOnly(true);
            }
            log.trace("RDBMS data connector - Querying database for attributes with query {}", query);
            Statement stmt = connection.createStatement();
            stmt.setQueryTimeout(queryTimeout);
            queryResult = stmt.executeQuery(query);
            resolvedAttributes = processResultSet(queryResult);
            if (resolvedAttributes.isEmpty() && noResultIsError) {
                log.debug("RDBMS data connector - No attributes from query");
                throw new AttributeAssociationException("No attributes returned from query");
            }
            log.debug("RDBMS data connector - Retrieved attributes: {}", resolvedAttributes.keySet());
            return resolvedAttributes;
        } catch (SQLException e) {
            log.debug("RDBMS data connector - Unable to execute SQL query {}; SQL State: {}, SQL Code: {}",
                    new Object[] {query, e.getSQLState(), e.getErrorCode(),}, e);
            throw new AttributeAssociationException("Unable to execute SQL query", e);
        } finally {
            try {
                if (queryResult != null) {
                    queryResult.close();
                }

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.debug("RDBMS data connector - Unable to close database connection; SQL State: {}, SQL Code: {}",
                        new Object[] {e.getSQLState(), e.getErrorCode()}, e);
            }
        }
    }

    /**
     * Converts a SQL query results set into a set of {@link String} values. ALL attributes are converted to Strings.
     * ALL multi-valued attributes are converted to comma delimited lists of string.
     * 
     * @param resultSet the result set to convert
     * 
     * @return the resultant set of attributes
     * 
     * @throws AttributeResolutionException thrown if there is a problem converting the result set into attributes
     */
    protected Map<String, String> processResultSet(ResultSet resultSet) throws AttributeAssociationException {
        Map<String, String> attributes = new HashMap<String, String>();

        try {
            if (!resultSet.next()) {
                return attributes;
            }

            ResultSetMetaData resultMD = resultSet.getMetaData();
            int numOfCols = resultMD.getColumnCount();
            String columnName;
            RDBMSColumnDescriptor columnDescriptor;
            String attributeId;
            List<Object> attributeValues;

            do {
                for (int i = 1; i <= numOfCols; i++) {
                    columnName = resultMD.getColumnName(i);
                    columnDescriptor = columnDescriptors.get(columnName);

                    if (columnDescriptor == null || columnDescriptor.getAttributeID() == null) {
                        attributeId = columnName;
                    } else {
                        attributeId = columnDescriptor.getAttributeID();
                    }

                    attributeValues = new ArrayList<Object>();

                    if (columnDescriptor == null || columnDescriptor.getDataType() == null) {
                        attributeValues.add(resultSet.getObject(i));
                    } else {
                        addValueByType(attributeValues, columnDescriptor.getDataType(), resultSet, i);
                    }
                    StringBuilder builder = new StringBuilder();
                    if (attributeValues != null && !attributeValues.isEmpty()) {
                        int count = 0;
                        for (Object valueObj : attributeValues) {
                            String value = valueObj.toString();
                            if (!DatatypeHelper.isEmpty(value)) {
                                builder.append(DatatypeHelper.safeTrimOrNullString(value));
                                if (count < attributeValues.size() - 1) {
                                    builder.append(",");
                                }
                                count++;
                            }
                        }
                    }

                    attributes.put(attributeId, builder.toString());

                }
            } while (resultSet.next());
        } catch (SQLException e) {
            log.debug("RDBMS data connector - Unable to read data from query result; SQL State: {}, SQL Code: {}",
                    new Object[] {e.getSQLState(), e.getErrorCode()}, e);
        }

        return attributes;
    }

    /**
     * Adds a value extracted from the result set as a specific type into the value set.
     * 
     * @param values set to add values into
     * @param type type the value should be extracted as
     * @param resultSet result set, on the current row, to extract the value from
     * @param columnIndex index of the column from which to extract the attribute
     * 
     * @throws SQLException thrown if value can not retrieved from the result set
     */
    protected void addValueByType(Collection values, DATA_TYPES type, ResultSet resultSet, int columnIndex)
            throws SQLException {
        switch (type) {
            case BigDecimal:
                values.add(resultSet.getBigDecimal(columnIndex));
                break;
            case Boolean:
                values.add(resultSet.getBoolean(columnIndex));
                break;
            case Byte:
                values.add(resultSet.getByte(columnIndex));
                break;
            case ByteArray:
                values.add(resultSet.getBytes(columnIndex));
                break;
            case Date:
                values.add(resultSet.getDate(columnIndex));
                break;
            case Double:
                values.add(resultSet.getDouble(columnIndex));
                break;
            case Float:
                values.add(resultSet.getFloat(columnIndex));
                break;
            case Integer:
                values.add(resultSet.getInt(columnIndex));
                break;
            case Long:
                values.add(resultSet.getLong(columnIndex));
                break;
            case Object:
                values.add(resultSet.getObject(columnIndex));
                break;
            case Short:
                values.add(resultSet.getShort(columnIndex));
                break;
            case Time:
                values.add(resultSet.getTime(columnIndex));
                break;
            case Timestamp:
                values.add(resultSet.getTimestamp(columnIndex));
                break;
            case URL:
                values.add(resultSet.getURL(columnIndex));
                break;
            default:
                values.add(resultSet.getString(columnIndex));
        }
    }

    /**
     * Caches the attributes resulting from a query.
     * 
     * @param principal the principal name of the user the attributes are for
     * @param query the query that generated the attributes
     * @param attributes the results of the query
     */
    protected void cacheResult(String principal, String query, Map<String, String> attributes) {

        Map<String, Map<String, String>> results = null;
        if (cache.containsKey(principal)) {
            results = cache.get(principal);
        } else {
            results = new HashMap<String, Map<String, String>>();
            cache.put(principal, results);
        }
        results.put(query, attributes);

        log.trace("RDBMS data connector - Caching attributes for principal {}", principal);

    }

    /**
     * @param searchTemplate the searchTemplate to set
     */
    public void setSearchTemplate(String searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    /**
     * @return the searchTemplate
     */
    public String getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * @param cacheTimeoutMs the cacheTimeoutMs to set
     */
    public void setCacheTimeoutMs(long cacheTimeoutMs) {
        this.cacheTimeoutMs = cacheTimeoutMs;
    }

    /**
     * @return the cacheTimeoutMs
     */
    public long getCacheTimeoutMs() {
        return cacheTimeoutMs;
    }

    /**
     * @param cacheResetTimeMs the cacheResetTimeMs to set
     */
    public void setCacheResetTimeMs(long cacheResetTimeMs) {
        this.cacheResetTimeMs = cacheResetTimeMs;
    }

    /**
     * @return the cacheResetTimeMs
     */
    public long getCacheResetTimeMs() {
        return cacheResetTimeMs;
    }

    /**
     * @param cacheResults the cacheResults to set
     */
    public void setCacheResults(boolean cacheResults) {
        this.cacheResults = cacheResults;
    }

    /**
     * @return the cacheResults
     */
    public boolean isCacheResults() {
        return cacheResults;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}