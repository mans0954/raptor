/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.records.Row;

/**
 * @author philsmart
 *
 * Takes a chart from the MUA, and wraps it inside the current view technologies (trinidads) graph model
 */
public class ChartProcessor {
	static Logger log = LoggerFactory.getLogger(ChartProcessor.class);

	public static RaptorGraphModel constructRaptorGraphModel(AggregatorGraphModel gmodel){
		RaptorGraphModel rgraph = new RaptorGraphModel();
		log.debug("Constructing Graph from : "+gmodel);
		rgraph.setGroupLabels(gmodel.getGroupLabels());
		rgraph.setSeriesLabels(gmodel.getSeriesLabels());
		rgraph.setChartValues(gmodel.getYValues());



		return rgraph;
	}

	/**
	 * @param gmodel
	 * @return
	 */
	public static RaptorTableChartModel constructRaptorTableChartModel(AggregatorGraphModel gmodel) {


		RaptorTableChartModel tableModel = new RaptorTableChartModel();

		for (int i=0; i < gmodel.getGroupLabels().size(); i++){
			Row<Double> row = new Row<Double>();
			row.setSeries(gmodel.getGroupLabels().get(i));
			/* important, as currently we only operate with one group, the second
			 * list of Doubles in the YValues will only have a List of size 1, which
			 * is assumed here, hence get(0).
			 */
			row.setValue(gmodel.getYValues().get(i).get(0));
			tableModel.addRow(row);
		}


		return tableModel;
	}

}
