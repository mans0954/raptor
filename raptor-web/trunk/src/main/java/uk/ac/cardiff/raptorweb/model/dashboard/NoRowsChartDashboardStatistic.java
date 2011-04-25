package uk.ac.cardiff.raptorweb.model.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;

public class NoRowsChartDashboardStatistic extends AbstractDashboardStatistic {

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(NoRowsChartDashboardStatistic.class);

    public Object processStatistic(AggregatorGraphModel model, StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException {
        log.info("Processing Statistic Using [{}]",NoRowsChartDashboardStatistic.class.getCanonicalName());
        
        
        RaptorTableChartModel table = chartProcessor.constructRaptorTableChartModel(model);

        if (table.getTableSeries().size() == 1) {
            if (table.getTableSeries().get(0).getRows() != null) {
                double result = table.getTableSeries().get(0).getRows().size();
                return result;
            } else {
                log.error("Table series 1 has no rows");
                throw new DashboardStatisticException("Table series 1 has no rows");
            }
        } else {
            log.error("Table did not have 1 and only 1 series");
            throw new DashboardStatisticException("Table did not have 1 and only 1 series");
        }
    }

}
