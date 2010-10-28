/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;

/**
 * @author philsmart
 *
 */
public interface StatisticsPreProcessing {

	public List<Entry> preProcess(List<Entry> entries) throws PreprocessorException;

}
