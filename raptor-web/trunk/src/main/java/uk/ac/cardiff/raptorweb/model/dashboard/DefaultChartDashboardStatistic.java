package uk.ac.cardiff.raptorweb.model.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;

public class DefaultChartDashboardStatistic extends AbstractDashboardStatistic{

    /** Class Logger */
    private final Logger log = LoggerFactory.getLogger(DefaultChartDashboardStatistic.class);

    public Object processStatistic(AggregatorGraphModel model,StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException {
        log.info("Processing Statistic Using {}",DefaultChartDashboardStatistic.class.getCanonicalName());
        RaptorTableChartModel table = chartProcessor.constructRaptorTableChartModel(model);
        return table;
    }

}
