package uk.ac.cardiff.raptorweb.engine.reports;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.reports.beans.GenericReportBean;
import uk.ac.cardiff.raptorweb.model.WebSession;

public class GraphAndChartPDFReportGenerator extends ReportConstructor {

    private GenericReportBean reportBean;
    private String reportXMLFile;

    static Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

    @Override
    public void generateReport(WebSession session) {
	log.info("Generating PDF report for both graph and chart, for {}", session.getGraphmodel().getSelectedStatisticalUnit());

	
	List charts = new ArrayList();	
	BufferedImage image = ChartProcessor.extractBufferedImage(session.getGraphmodel().getCurrentJFreeGraph().getChart(), session.getGraphmodel().getChartOptions());
	
	reportBean.setImage(image);
	charts.add(reportBean);
	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(charts);;
	
	try {
	    File baseGraphDirectory = saveDirectory.getFile();
	    if (!baseGraphDirectory.exists())
		baseGraphDirectory.mkdir();

	    // append username, to create username specific directories
	    File dir = new File(saveDirectory.getFile().getCanonicalPath() + "/" + session.getUser().getName());
	    log.debug("Save Directory exists: " + dir.exists());
	    if (!dir.exists())
		dir.mkdir();

	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	    java.util.Date date = new java.util.Date();
	    dir = new File(dir.getAbsoluteFile() + "/" + session.getGraphmodel().getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getUnitName().replaceAll(" ", "") + "-" + dateFormat.format(date) + ".pdf");

	    
	    File reportTemplateXMLFile = new File(baseDirectory.getFile().getCanonicalPath()+"/report-templates/"+reportXMLFile);
	    log.debug("Creating PDF in file {}",reportTemplateXMLFile);
	    
	    //TODO no need to compile the report (which mostly will not change) each time this method is called (move it).
	    JasperDesign jasperDesign = JRXmlLoader.load(reportTemplateXMLFile);
	    
	    Map parameters = new HashMap();
	    
	    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	    JasperExportManager.exportReportToPdfFile(jasperPrint,dir.getCanonicalPath());
	    
	    String relativePath = dir.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(), "");
	    session.getReportmodel().addReportForDownload(dir, relativePath);
	    log.info("Successfully created PDF...{}", session.getGraphmodel().getSelectedStatisticalUnit());
		
	    
	} catch (JRException e) {
	    log.error("Error Creating JasperReport",e);
	} catch (IOException e) {
	    log.error("File expection when creating JasperReport",e);
	}
	
    }



    @Override
    protected HandledReportTypes getRegisterHandledReportType() {
	return HandledReportTypes.pdf;
    }

    public void setReportBean(GenericReportBean reportBean) {
	this.reportBean = reportBean;
    }

    public GenericReportBean getReportBean() {
	return reportBean;
    }

    public void setReportXMLFile(String reportXMLFile) {
	this.reportXMLFile = reportXMLFile;
    }

    public String getReportXMLFile() {
	return reportXMLFile;
    }

}
