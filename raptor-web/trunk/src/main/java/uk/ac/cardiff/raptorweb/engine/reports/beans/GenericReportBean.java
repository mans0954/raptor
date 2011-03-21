package uk.ac.cardiff.raptorweb.engine.reports.beans;

import java.awt.image.BufferedImage;
import java.util.List;

public class GenericReportBean {

    private java.awt.image.BufferedImage image;
    private String description;
    private List state;

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public BufferedImage getImage() {
	return image;
    }

    public void setImage(BufferedImage image) {
	this.image = image;
    }

    public void setState(List state) {
	this.state = state;
    }

    public List getState() {
	return state;
    }



}
