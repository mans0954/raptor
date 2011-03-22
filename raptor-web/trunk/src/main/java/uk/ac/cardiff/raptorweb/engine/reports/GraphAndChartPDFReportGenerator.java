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
import uk.ac.cardiff.raptorweb.engine.reports.beans.SimpleRowBean;
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

	// construct the table
	String[] columns = new String[session.getGraphmodel().getCurrentTableGraph().getTableSeries().size() + 1];
	columns[0] = "Group";// always group
	int headerCount = 1;
	for (TableSeries tseries : session.getGraphmodel().getCurrentTableGraph().getTableSeries()) {
	    columns[headerCount++] = tseries.getSeriesLabel();
	}

	Object[][] data = new Object[maxNoRows(session.getGraphmodel().getCurrentTableGraph())][columns.length];

	int rowCount = 0;
	for (ManyRow mrow : session.getGraphmodel().getCurrentTableGraph().getRowsForView()) {
	    data[rowCount][0] = mrow.getGroupLabel();
	    Iterator<?> it = mrow.getValues().iterator();
	    headerCount = 1;
	    while (it.hasNext()) {
		String value = it.next().toString();
		data[rowCount][headerCount++] = value;
	    }
	    rowCount++;
	}
	log.debug("{}", Arrays.toString(data));

	DynamicTableModel model = new DynamicTableModel();
	model.setColumnNames(columns);
	model.setData(data);

	// construct report and set the template
	DynamicReportBuilder drb = new DynamicReportBuilder();
	drb.setTemplateFile(reportTemplateXMLFile.getCanonicalPath());

	Style columDetailWhite = new Style();
	columDetailWhite.setBorder(Border.THIN);
	columDetailWhite.setBackgroundColor(Color.WHITE);
	columDetailWhite.setHorizontalAlign(HorizontalAlign.CENTER);
	Style columDetailWhiteBold = new Style();
	columDetailWhiteBold.setBorder(Border.THIN);
	columDetailWhiteBold.setBackgroundColor(Color.WHITE);



	Style titleStyle = new Style();
	titleStyle.setFont(new Font(12, Font._FONT_VERDANA, true));
	titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
	titleStyle.setBorder(Border.THIN);
	Style oddRowStyle = new Style();
	oddRowStyle.setBorder(Border.NO_BORDER);
	Color veryLightGrey = new Color(230, 230, 230);
	oddRowStyle.setBackgroundColor(veryLightGrey);
	oddRowStyle.setTransparency(Transparency.OPAQUE);

	// table name column
	String[] headings = model.getColumnNames();
	for (int i = 0; i < headings.length; i++) {
	    if (i == 0) {
		// group category
		String key = headings[i];
		AbstractColumn column = ColumnBuilder.getNew().setColumnProperty(key, String.class.getName()).setTitle(key).setWidth(new Integer(100)).setStyle(columDetailWhiteBold).build();
		column.setHeaderStyle(titleStyle);
		drb.addColumn(column);
	    } else {
		String key = headings[i];
		AbstractColumn column = ColumnBuilder.getNew().setColumnProperty(key, String.class.getName()).setTitle(key).setWidth(new Integer(100)).setStyle(columDetailWhite).build();
		column.setHeaderStyle(titleStyle);
		drb.addColumn(column);
	    }

	}

	drb.setPrintBackgroundOnOddRows(true);
	drb.setOddRowBackgroundStyle(oddRowStyle);
	drb.setColumnsPerPage(new Integer(1));
	drb.setUseFullPageWidth(true);
	drb.setColumnSpace(new Integer(5));
	drb.setDetailHeight(new Integer(15));

	// add any additional parameters that are mapped to the template
	BufferedImage image = ChartProcessor.extractBufferedImage(session.getGraphmodel().getCurrentJFreeGraph().getChart(), session.getGraphmodel().getChartOptions());

	Map parameters = new HashMap();
	parameters.put("image", image);
	parameters.put("subtitle", session.getGraphmodel().getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getPresentation().getGraphTitle());
	log.debug("Map: " + parameters);

	DynamicReport dr = drb.build();
	JRDataSource ds = new JRTableModelDataSource(model);
	JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds, parameters);
	return jp;
    }

    /**
     * @param currentTableGraph
     * @return
     */
    private int maxNoRows(RaptorTableChartModel currentTableGraph) {
	int maxRows = 0;
	for (TableSeries tseries : currentTableGraph.getTableSeries()) {
	    if (tseries.getRows().size() > maxRows)
		maxRows = tseries.getRows().size();
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
