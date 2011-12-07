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
package uk.ac.cardiff.raptorweb.engine.reports;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.reports.beans.DynamicTableModel;
import uk.ac.cardiff.raptorweb.engine.reports.beans.GenericReportBean;
import uk.ac.cardiff.raptorweb.model.ManyRow;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.TableSeries;
import uk.ac.cardiff.raptorweb.model.WebSession;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * The Class GraphAndChartPDFReportGenerator.
 */
public class GraphAndChartPDFReportGenerator extends ReportConstructor {

    /** The report bean. */
    private GenericReportBean reportBean;

    /** The report xml file. */
    private String reportXMLFile;

    /** The log. */
    static Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptorweb.engine.reports.ReportConstructor#generateReport(uk.ac.cardiff.raptorweb.model.WebSession)
     */
    @Override
    public void generateReport(WebSession session) {
        // TODO there are common setup and store aspects to all reports that should be pushed higher
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

            String fileName = session.getGraphmodel().getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getUnitName().replaceAll(" ", "") + "-"
                    + dateFormat.format(date) + ".pdf";
            if (!session.getGraphmodel().getDownloadFilename().equals("")) {
                fileName = session.getGraphmodel().getDownloadFilename() + ".pdf";
            }
            dir = new File(dir.getAbsoluteFile() + "/" + fileName);

            File reportTemplateXMLFile = new File(baseDirectory.getFile().getCanonicalPath() + "/report-templates/" + reportXMLFile);
            log.debug("Creating PDF in file {}", reportTemplateXMLFile);

            JasperPrint jp = constructReport(reportTemplateXMLFile, session);
            JasperExportManager.exportReportToPdfFile(jp, dir.getCanonicalPath());

            String relativePath = dir.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(), "");
            Date now = new Date(System.currentTimeMillis());
            session.getReportmodel().addReportForDownload(dir, relativePath, now, this.getHandledReportType().displayName);
            log.info("Successfully created PDF...{}", session.getGraphmodel().getSelectedStatisticalUnit());

        } catch (JRException e) {
            log.error("Error Creating JasperReport", e);
        } catch (IOException e) {
            log.error("File expection when creating JasperReport", e);
        } catch (ColumnBuilderException e) {
            log.error("Could not build columns in JasperReport", e);
        }

    }

    /**
     * Construct report.
     * 
     * @param reportTemplateXMLFile
     *            the report template xml file
     * @param session
     *            the session
     * @return the jasper print
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws JRException
     *             the jR exception
     * @throws ColumnBuilderException
     *             the column builder exception
     */
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

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("image", image);
        parameters.put("subtitle", session.getGraphmodel().getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getPresentation().getGraphTitle());
        log.debug("Map: " + parameters);

        DynamicReport dr = drb.build();
        JRDataSource ds = new JRTableModelDataSource(model);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds, parameters);
        return jp;
    }

    /**
     * Max no rows.
     * 
     * @param currentTableGraph
     *            the current table graph
     * @return the int
     */
    private int maxNoRows(RaptorTableChartModel currentTableGraph) {
        int maxRows = 0;
        for (TableSeries tseries : currentTableGraph.getTableSeries()) {
            if (tseries.getRows().size() > maxRows)
                maxRows = tseries.getRows().size();
        }
        return maxRows;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptorweb.engine.reports.ReportConstructor#getRegisterHandledReportType()
     */
    @Override
    protected HandledReportTypes getRegisterHandledReportType() {
        return HandledReportTypes.pdf;
    }

    /**
     * Sets the report bean.
     * 
     * @param reportBean
     *            the new report bean
     */
    public void setReportBean(GenericReportBean reportBean) {
        this.reportBean = reportBean;
    }

    /**
     * Gets the report bean.
     * 
     * @return the report bean
     */
    public GenericReportBean getReportBean() {
        return reportBean;
    }

    /**
     * Sets the report xml file.
     * 
     * @param reportXMLFile
     *            the new report xml file
     */
    public void setReportXMLFile(String reportXMLFile) {
        this.reportXMLFile = reportXMLFile;
    }

    /**
     * Gets the report xml file.
     * 
     * @return the report xml file
     */
    public String getReportXMLFile() {
        return reportXMLFile;
    }

}
