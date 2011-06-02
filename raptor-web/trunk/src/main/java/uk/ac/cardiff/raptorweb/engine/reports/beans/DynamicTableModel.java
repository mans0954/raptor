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
