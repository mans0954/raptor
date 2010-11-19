/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;
import uk.ac.cardiff.raptormua.exceptions.PostprocessorException;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;

/**
 * @author philsmart
 *
 */
public interface StatisticsPostProcessor {

	public void postProcess(Observation[] observations) throws PostprocessorException;
}
