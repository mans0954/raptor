package uk.ac.cardiff.raptorweb.engine.reports.beans;

import java.awt.image.BufferedImage;

public class GenericReportBean {

    private java.awt.image.BufferedImage image;
    private String description;

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

}
