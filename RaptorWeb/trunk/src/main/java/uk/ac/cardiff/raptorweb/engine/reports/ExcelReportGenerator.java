/**
 *
 */
package uk.ac.cardiff.raptorweb.engine.reports;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.records.Row;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.core.io.Resource;

/**
 * @author philsmart
 *
 */
public class ExcelReportGenerator extends ReportConstructor {
    static Logger log = Logger.getLogger(ExcelReportGenerator.class);

    public ExcelReportGenerator() {
	// set which type is handles
	this.setHandledReportType(HandledReportTypes.excel);
    }

    public String generateReport(GraphModel model, ReportModel report) {
	log.info("Generating Excel Report " + model.getSelectedStatisticalUnit());
	String relativePath = null;
	try {
	    File dir = saveDirectory.getFile();
	    log.debug("File exists: " + dir.exists());
	    if (!dir.exists())
		dir.mkdir();

	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	    java.util.Date date = new java.util.Date();
	    dir = new File(dir.getAbsoluteFile() + "/" + model.getSelectedStatisticalUnit().getStatisticParameters().getUnitName().replaceAll(" ", "") + "-report" + dateFormat.format(date) + ".xls");
	    WorkbookSettings ws = new WorkbookSettings();
	    ws.setLocale(new Locale("en", "EN"));
	    WritableWorkbook workbook = Workbook.createWorkbook(dir, ws);
	    WritableSheet s = workbook.createSheet("RaptorWeb Report " + model.getSelectedStatisticalUnit(), 0);

	    /* SET UP WRITING STYLE */

	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
	    WritableCellFormat cf = new WritableCellFormat(wf);
	    cf.setWrap(false);

	    /* DONE */

	    Label h1 = new Label(0, 0, "Series Label", cf);
	    s.addCell(h1);
	    Label h2 = new Label(1, 0, "Series Value", cf);
	    s.addCell(h2);

	    wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
	    cf = new WritableCellFormat(wf);

	    WritableCellFormat cf2 = new WritableCellFormat(NumberFormats.FLOAT);

	    int lineCount = 1;
	    for (Row row : model.getCurrentTableGraph().getRows()) {
		Label l = new Label(0, lineCount, row.getSeries(), cf);
		s.addCell(l);
		Double value = Double.parseDouble(row.getValue());
		Number l2 = new Number(1, lineCount, value.intValue(), cf2);
		s.addCell(l2);
		lineCount++;

	    }

	    relativePath = dir.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(), "");
	    report.addReportForDownload(dir, relativePath);
	    log.debug("Excel Report Created At: " + relativePath);
	    workbook.write();
	    workbook.close();

	} catch (IOException e) {
	    log.error("Problem generating excel report " + e.getMessage());

	} catch (WriteException e) {
	    log.error("Problem generating excel report " + e.getMessage());
	}

	log.info("Excel Created..." + model.getSelectedStatisticalUnit());
	return relativePath;
    }
}
