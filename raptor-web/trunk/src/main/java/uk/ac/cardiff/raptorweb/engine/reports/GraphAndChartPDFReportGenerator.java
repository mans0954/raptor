package uk.ac.cardiff.raptorweb.engine.reports;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.reports.beans.DynamicTableModel;
import uk.ac.cardiff.raptorweb.engine.reports.beans.GenericReportBean;
import uk.ac.cardiff.raptorweb.model.ManyRow;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.TableSeries;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.model.records.Row;

public class GraphAndChartPDFReportGenerator extends ReportConstructor {

    private GenericReportBean reportBean;
    private String reportXMLFile;

    static Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

    @Override
    public void generateReport(WebSession session) {
	log.info("Generating PDF report for both graph and chart, for {}", session.getGraphmodel().getSelectedStatisticalUnit());

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

	    File reportTemplateXMLFile = new File(baseDirectory.getFile().getCanonicalPath() + "/report-templates/" + reportXMLFile);
	    log.debug("Creating PDF in file {}", reportTemplateXMLFile);

	    JasperPrint jp = constructReport(reportTemplateXMLFile, session);
	    JasperExportManager.exportReportToPdfFile(jp, dir.getCanonicalPath());

	    String relativePath = dir.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(), "");
	    session.getReportmodel().addReportForDownload(dir, relativePath);
	    log.info("Successfully created PDF...{}", session.getGraphmodel().getSelectedStatisticalUnit());

	} catch (JRException e) {
	    log.error("Error Creating JasperReport", e);
	} catch (IOException e) {
	    log.error("File expection when creating JasperReport", e);
	} catch (ColumnBuilderException e) {
	    log.error("Could not build columns in JasperReport", e);
	}

    }

    private JasperPrint constructReport(File reportTemplateXMLFile, WebSession session) throws IOException, JRException, ColumnBuilderException {
	log.info("Constructing Report Using DynamicJasper");
	// List source = new ArrayList();
	// BufferedImage image = ChartProcessor.extractBufferedImage(session.getGraphmodel().getCurrentJFreeGraph().getChart(),
	// session.getGraphmodel().getChartOptions());
	// reportBean.setImage(image);
	// ArrayList<String> values = new ArrayList();
	// values.add("One");
	// values.add("Two");
	// reportBean.setState(values);
	// source.add(reportBean);
	//
	// DynamicReportBuilder drb = new DynamicReportBuilder();
	// drb.setTemplateFile(reportTemplateXMLFile.getCanonicalPath());
	//
	// drb.addField("image", java.awt.image.BufferedImage.class.getName());
	//
	// //session.getGraphmodel().getCurrentTableGraph().ge
	//
	// AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty("state",List.class.getName()).setTitle("State").setWidth(85).build();
	// drb.addColumn(columnState);
	// DynamicReport dynamicReport = drb.build();
	//
	// JRDataSource ds = new JRBeanCollectionDataSource(source);
	// JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), ds);
	String[] columns = new String[session.getGraphmodel().getCurrentTableGraph().getTableSeries().size()+1];
	columns[0] = "Group";//always group
	int headerCount=1;
	for (TableSeries tseries : session.getGraphmodel().getCurrentTableGraph().getTableSeries()) {
	    columns[headerCount++] = tseries.getSeriesLabel();
	}

	Object[][] data = new Object[maxNoRows(session.getGraphmodel().getCurrentTableGraph())][columns.length];



	int rowCount=0;
	for (ManyRow mrow : session.getGraphmodel().getCurrentTableGraph().getRowsForView()) {
	    data[rowCount][0]=mrow.getGroupLabel();
	    Iterator<?> it =mrow.getValues().iterator();
	    headerCount=1;
	    while (it.hasNext()){
		String value = it.next().toString();
		data[rowCount][headerCount++] = value;
	    }
	    rowCount++;
	}
	log.debug("{}",Arrays.toString(data));

	DynamicTableModel model = new DynamicTableModel();
	model.setColumnNames(columns);
	model.setData(data);

	BufferedImage image = ChartProcessor.extractBufferedImage(session.getGraphmodel().getCurrentJFreeGraph().getChart(),session.getGraphmodel().getChartOptions());
	model.setImage(image);

	FastReportBuilder drb = new FastReportBuilder();
	//drb.setTemplateFile(reportTemplateXMLFile.getCanonicalPath());
	//drb.addField("image", java.awt.image.BufferedImage.class.getName());
	Style columDetail = new Style();
	columDetail.setBorder(Border.THIN);
	Style columDetailWhite = new Style();
	columDetailWhite.setBorder(Border.THIN);
	columDetailWhite.setBackgroundColor(Color.WHITE);
	Style columDetailWhiteBold = new Style();
	columDetailWhiteBold.setBorder(Border.THIN);
	columDetailWhiteBold.setBackgroundColor(Color.WHITE);
	Style titleStyle = new Style();
	titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));
	Style numberStyle = new Style();
	numberStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
	Style amountStyle = new Style();
	amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
	amountStyle.setBackgroundColor(Color.cyan);
	amountStyle.setTransparency(Transparency.OPAQUE);
	Style oddRowStyle = new Style();
	oddRowStyle.setBorder(Border.NO_BORDER);
	Color veryLightGrey = new Color(230, 230, 230);
	oddRowStyle.setBackgroundColor(veryLightGrey);
	oddRowStyle.setTransparency(Transparency.OPAQUE);

	// table name column
	String[] headings = model.getColumnNames();
	for (int i = 0; i < headings.length; i++) {
	    String key = headings[i];
	    AbstractColumn column = ColumnBuilder.getNew().setColumnProperty(key, String.class.getName()).setTitle(key).setWidth(new Integer(100)).setStyle(columDetailWhite).build();
	    drb.addColumn(column);

	}
	drb.setTitle("Sample Report").setTitleStyle(titleStyle).setTitleHeight(new Integer(30)).setSubtitleHeight(new Integer(20)).setDetailHeight(new Integer(15))
	// .setLeftMargin(margin)
		// .setRightMargin(margin)
		// .setTopMargin(margin)
		// .setBottomMargin(margin)
		.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(oddRowStyle).setColumnsPerPage(new Integer(1)).setUseFullPageWidth(true).setColumnSpace(new Integer(5));
	DynamicReport dr = drb.build();

	JRDataSource ds = new JRTableModelDataSource(model);
	JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
	return jp;
    }

    /**
     * @param currentTableGraph
     * @return
     */
    private int maxNoRows(RaptorTableChartModel currentTableGraph) {
	int maxRows = 0;
	 for (TableSeries tseries :currentTableGraph.getTableSeries()){
	     if (tseries.getRows().size()>maxRows)
		 maxRows=tseries.getRows().size();
	 }
	 return maxRows;
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
