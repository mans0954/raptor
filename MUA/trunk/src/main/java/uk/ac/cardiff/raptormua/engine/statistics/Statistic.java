/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;
import uk.ac.cardiff.raptormua.runtimeutils.EntryClone;

/**
 * @author philsmart
 * Holds a statistics unit or one statistics operation on one piece of data
 */
public class Statistic {

	static Logger log = Logger.getLogger(Statistic.class);

	private List<Entry> entries;
	private String field;
	private String unitName;
	private String methodName;
	private List<String> methodParams;

	/** add a preprocessing module to the statistical method */
	private StatisticsPreProcessing preprocessor;


	/**
	 * <p> Always creates a copy of the entries, as the statisical method is
	 * not safe, in that it may alter the state of the original data i.e. a
	 * preprocessing method </p>
	 *
	 * @param authEntries
	 */
	public void setEntries(List<Entry> authEntries) {
		ArrayList<Entry> entriesCopy = EntryClone.cloneEntries((ArrayList<Entry>) authEntries);
		if (preprocessor!=null)
			try {
				log.info("Invoking statistical preprocessor "+preprocessor.getClass());
				entriesCopy = (ArrayList<Entry>) preprocessor.preProcess(entriesCopy);
			} catch (PreprocessorException e) {
				log.error("Could not preprocess entries "+preprocessor.getClass());
			}
		this.entries = entriesCopy;
	}

	public List<Entry> getAuthEntries() {
		return entries;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodParams(List<String> methodParams) {
		this.methodParams = methodParams;
	}

	public List<String> getMethodParams() {
		return methodParams;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setPreprocessor(StatisticsPreProcessing preprocessor) {
		this.preprocessor = preprocessor;
	}

	public StatisticsPreProcessing getPreprocessor() {
		return preprocessor;
	}



}
