package uk.ac.cardiff.raptorweb.engine.reports;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.model.WebSession;

public class GraphAndChartPDFReportGenerator extends ReportConstructor {

    static Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

    @Override
    public String generateReport(WebSession session) {
	log.info("Generating PDF report for both graph and chart, for {}",session.getGraphmodel().getSelectedStatisticalUnit());
	
	
	
	return null;
    }

    @Override
    protected HandledReportTypes getRegisterHandledReportType() {
	return HandledReportTypes.pdf;
    }

}
