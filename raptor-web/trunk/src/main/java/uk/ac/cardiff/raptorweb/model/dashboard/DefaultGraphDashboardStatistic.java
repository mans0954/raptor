package uk.ac.cardiff.raptorweb.model.dashboard;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;

public class DefaultGraphDashboardStatistic extends AbstractDashboardStatistic{
    
    


    public Object processStatistic(AggregatorGraphModel model, StatisticalUnitInformation statisticalUnitInformation) throws DashboardStatisticException {
        DateTime chosenStartTime = statisticalUnitInformation.getStatisticParameters().getStartTimeAsDate();
        DateTime currentDateTime = statisticalUnitInformation.getStatisticParameters().getEndTimeAsDate();
        
        RaptorJFreeChartModel jfreeChart = chartProcessor.constructJFreeGraph(GraphPresentation.FRONT, ChartType.AREA, model, 1270, 350,
               (chosenStartTime.toString("ddMMyyyHH-mm") + "-" + currentDateTime.toString("ddMMyyyHH-mm")));
        
        return jfreeChart;
    }

}
