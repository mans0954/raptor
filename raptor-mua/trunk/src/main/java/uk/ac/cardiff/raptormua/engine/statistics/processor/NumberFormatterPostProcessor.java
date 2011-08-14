/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */

package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;

/**
 * @author philsmart
 * 
 */
public class NumberFormatterPostProcessor implements StatisticsPostProcessor {

    /* class logger */
    static Logger log = LoggerFactory.getLogger(NumberFormatterPostProcessor.class);

    /*
     * <p> performs all actions directly ('live') on the input object, and passes that back as a reference to conform
     * with the <code>StatisticsPostProcessor</code> interface </p>
     * 
     * (non-Javadoc)
     * 
     * @see
     * uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor#postProcess(uk.ac.cardiff.raptormua.engine.
     * statistics.records.Observation[])
     */
    public Observation[] postProcess(Observation[] observations) throws PostprocessorException {
        log.debug("{} post processor called, entries into postprocessor: {}", this.getClass(), observations.length);
        for (Observation obs : observations) {

        }
        return observations;
    }

    private String formatDoubleWithCommas(double number) {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        String formattedNumber = df.format((int) number);
        return formattedNumber;
    }

}
