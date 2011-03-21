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
