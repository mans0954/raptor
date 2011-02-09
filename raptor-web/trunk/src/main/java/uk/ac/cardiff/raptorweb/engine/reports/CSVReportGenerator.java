/**
 *
 */
package uk.ac.cardiff.raptorweb.engine.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.TableSeries;
import uk.ac.cardiff.raptorweb.model.WebSession;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * @author philsmart
 *
 */
public class CSVReportGenerator extends ReportConstructor {
    static Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

    public CSVReportGenerator() {
	// set which type is handles
	this.setHandledReportType(HandledReportTypes.csv);
    }

    public String generateReport(WebSession session) {
	log.info("Generating CSV Report " + session.getGraphmodel().getSelectedStatisticalUnit());
	String relativePath = null;
	try {
	    // make sure base directory exists first
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
	    dir = new File(dir.getAbsoluteFile() + "/" + session.getGraphmodel().getSelectedStatisticalUnit().getStatisticParameters().getUnitName().replaceAll(" ", "") + "-" + dateFormat.format(date) + ".csv");


	    StringBuilder document = new StringBuilder();

	    int rowCount = 0;
	    for (int lineCount=0; lineCount < maxNoRows(session.getGraphmodel().getCurrentTableGraph());lineCount++){
		if (lineCount == 0) {
		    // do headers
		    for (TableSeries tseries : session.getGraphmodel().getCurrentTableGraph().getTableSeries()) {
			document.append(tseries.getSeriesLabel()+",values,");
		    }
		    document.append("\n");
		}
		else{
        	    for (TableSeries tseries : session.getGraphmodel().getCurrentTableGraph().getTableSeries()){
        		if (tseries.getRows().size()>rowCount)
        		    document.append(tseries.getRows().get(rowCount).getGroup() + "," + tseries.getRows().get(rowCount).getValue() + ",");
        	    }
        	    document.append("\n");
        	    rowCount++;
		}
	    }

	    BufferedWriter writer = new BufferedWriter(new FileWriter(dir));
	    writer.write(document.toString());
	    writer.close();

	    relativePath = dir.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(), "");
	    session.getReportmodel().addReportForDownload(dir, relativePath);
	    log.debug("CSV Report Created At: " + relativePath);

	} catch (IOException e) {
	    log.error("Problem generating CSV report " + e.getMessage());

	}

	log.info("CSV Created..." + session.getGraphmodel().getSelectedStatisticalUnit());
	return relativePath;
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
}
