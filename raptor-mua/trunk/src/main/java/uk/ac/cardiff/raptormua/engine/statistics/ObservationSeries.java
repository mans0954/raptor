package uk.ac.cardiff.raptormua.engine.statistics;

import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;

public class ObservationSeries {
	
	private String seriesName;
	
    /*
     * each statistical method produces objects (observations) which are stored in this array variable ready for postprocessing or construction of an
     * <code>AggregatorGraphModel</code>
     */
    private Observation[] observations;

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setObservations(Observation[] observations) {
		this.observations = observations;
	}

	public Observation[] getObservations() {
		return observations;
	}

}
