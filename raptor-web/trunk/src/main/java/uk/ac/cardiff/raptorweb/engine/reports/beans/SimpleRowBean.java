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
package uk.ac.cardiff.raptorweb.engine.reports.beans;

public class SimpleRowBean {

    private String header;
    private String row;
    private String value;

    public SimpleRowBean(String header, String row, String value) {
	this.setRow(row);
	this.setHeader(header);
	this.setValue(value);
    }

    public void setHeader(String header) {
	this.header = header;
    }

    public String getHeader() {
	return header;
    }

    public void setRow(String row) {
	this.row = row;
    }

    public String getRow() {
	return row;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

}
