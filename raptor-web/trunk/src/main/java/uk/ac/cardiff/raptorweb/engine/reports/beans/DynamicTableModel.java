/**
 *
 */
package uk.ac.cardiff.raptorweb.engine.reports.beans;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.engine.reports.ExcelReportGenerator;

import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * @author philsmart
 *
 */
public class DynamicTableModel extends AbstractTableModel {

    static Logger log = LoggerFactory.getLogger(DynamicTableModel.class);

    /** The column names. */
    private String[] columnNames;
    /** The data. */
    private Object[][] data;


    public DynamicTableModel(){
	super();
    }



    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
	return columnNames.length;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
	return data.length;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int col) {
	return columnNames[col];
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int col) {
	return data[row][col];
    }

    /**
     * Gets the column names.
     *
     * @return the column names
     */
    public String[] getColumnNames() {
	return columnNames;
    }

    /**
     * Sets the column names.
     *
     * @param columnNames
     *            the new column names
     */
    public void setColumnNames(String[] columnNames) {
	this.columnNames = columnNames;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public Object[][] getData() {
	return data;
    }

    /**
     * Sets the data.
     *
     * @param data
     *            the new data
     */
    public void setData(Object[][] data) {
	this.data = data;
    }


}
