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
public class CSVReportGenerator extends ReportConstructor{
	static Logger log = Logger.getLogger(CSVReportGenerator.class);


	public CSVReportGenerator(){
		//set which type is handles
		this.setHandledReportType(HandledReportTypes.csv);
	}

	public String generateReport(GraphModel model, ReportModel report){
		log.info("Generating CSV Report "+model.getSelectedStatisticalUnit());
		String relativePath = null;
		try {
			File dir = saveDirectory.getFile();
			log.debug("Save Directory exists: "+dir.exists());
			if (!dir.exists())dir.mkdir();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			java.util.Date date = new java.util.Date();
			dir = new File(dir.getAbsoluteFile() + "/"+model.getSelectedStatisticalUnit().getStatisticParameters().getUnitName().replaceAll(" ", "")+"-"+ dateFormat.format(date) + ".csv");


			StringBuilder fieldnames = new StringBuilder();
			fieldnames.append("Series Name, Series Values\n");

			StringBuilder document = new StringBuilder();
			for (Row row : model.getCurrentTableGraph().getRows()) {
				document.append(row.getSeries()+","+row.getValue()+"\n");
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(dir));
			writer.write(fieldnames.toString());
			writer.write(document.toString());
			writer.close();

			relativePath = dir.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(),"");
			report.addReportForDownload(dir, relativePath);
			log.debug("CSV Report Created At: "+relativePath);


		} catch (IOException e) {
			log.error("Problem generating CSV report "+e.getMessage());

		}

		log.info("CSV Created..."+model.getSelectedStatisticalUnit());
		return relativePath;
	}
}
