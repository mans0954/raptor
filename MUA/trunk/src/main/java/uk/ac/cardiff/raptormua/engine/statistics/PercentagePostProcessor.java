/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;
import uk.ac.cardiff.raptormua.exceptions.PostprocessorException;

/**
 * @author philsmart
 *
 */
public class PercentagePostProcessor implements StatisticsPostProcessor{

	/* class logger */
	static Logger log = Logger.getLogger(PercentagePostProcessor.class);


	/*
	 * <p> performs all actions directly ('live') on the input object, and passes that back as a reference
	 * to conform with the <code>StatisticsPostProcessor</code> interface </p>
	 *
	 * (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor#postProcess(uk.ac.cardiff.raptormua.engine.statistics.records.Observation[])
	 */
	@Override
	public void postProcess(Observation[] observations)	throws PostprocessorException {
		log.debug("Entries into preprocessor: "+observations.length);
		log.info("Post Processor "+this.getClass());

		//find total value (sum)
		double sum =0;
		for (Observation obs : observations){
			sum+= obs.getValue();
		}

		//now set all values as percentages of the max
		for (Observation obs: observations){
			double percentage = (obs.getValue()/sum)*100;
			obs.setValue(percentage);
		}

	}

}
