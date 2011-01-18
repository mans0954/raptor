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
package uk.ac.cardiff.raptormua.service.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.AdministrativeFunction.AdministrativeFunctionType;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.UAEntryPush;
import uk.ac.cardiff.raptormua.engine.MUAEngine;
import uk.ac.cardiff.raptormua.service.MUAProcess;

/**
 * @author philsmart
 *
 */
public class MUAProcessImpl implements MUAProcess {

	/* class logger */
    static Logger log = LoggerFactory.getLogger(MUAProcessImpl.class);

    private MUAEngine engine;

    /*
     * ReentrantLock to prevent more than at the same time
     */
    final Lock lockR = new ReentrantLock();

    public void setEngine(MUAEngine engine) {
	this.engine = engine;
    }

    public MUAEngine getEngine() {
	return engine;
    }

    /*
     * The MUA no longer polls for data from the UA, but this is still here in case it is needed
     *
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.raptormua.service.MUAProcess#poll()
     */
    public void poll() {
	if (lockR.tryLock()) {
	    try {
		engine.poll();
	    } catch (Exception e) {
		// TODO either throw as service output, or deal with here
		log.error(e.getMessage());
		e.printStackTrace();
	    } finally {
		lockR.unlock();
	    }
	}

    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.raptormua.service.MUAProcess#performStatistics(java.lang.String)
     */
    public AggregatorGraphModel performStatistic(String statisticName) {
	if (lockR.tryLock()) {
	    try {
		log.info("WebSservice call for perform statistic {} ", statisticName);
		return engine.performStatistic(statisticName);
	    } catch (Exception e) {
		// TODO either throw as service output, or deal with here
		log.error(e.getMessage());
		e.printStackTrace();
	    } finally {
		lockR.unlock();
	    }
	}
	log.warn("Lock was hit for method performStatistic");
	return null;

    }

    /*
     * Method does not need to be locked. (non-Javadoc)
     *
     * @see uk.ac.cardiff.raptormua.service.MUAProcess#getCapabilities()
     */
    public Capabilities getCapabilities() {
	log.info("WebSservice call for get capabilities");
	return engine.getCapabilities();
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.raptormua.service.MUAProcess#setStatisticalUnit(uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation)
     */
    @Override
    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
	boolean success= false;
	if (lockR.tryLock()) {
	    try {
		engine.updateStatisticalUnit(statisticalUnitInformation);
		success=true;
	    } catch (Exception e) {
		// TODO either throw as service output, or deal with here
		log.error(e.getMessage());
		e.printStackTrace();
	    } finally {
		lockR.unlock();
	    }
	}
	if (!success)
	    log.warn("Lock was hit for method updateStatisticalUnit");
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.raptormua.service.MUAProcess#performAdministrativeFunction(uk.ac.cardiff.model.AdministrativeFunction.AdministrativeFunctionType)
     */
    @Override
    public boolean performAdministrativeFunction(AdministrativeFunction function) {
	if (lockR.tryLock()) {
	    try {
		log.info("Performing administrative function {}, request orginates from {}", function.getAdministrativeFunction(), function.getRequester());
		return engine.performAdministrativeFunction(function);
	    } catch (Exception e) {
		// TODO either throw as service output, or deal with here
		log.error(e.getMessage());
		e.printStackTrace();
		return false;
	    } finally {
		lockR.unlock();

	    }

	}
	log.warn("Lock was hit for method performAdministrativeFunction");
	return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.raptormua.service.MUAProcess#addAuthentications(uk.ac.cardiff.model.wsmodel.UAEntryPush)
     */
    @Override
    public void addAuthentications(UAEntryPush pushed) {
	boolean success = false;
	if (lockR.tryLock()) {
	    try {
		log.debug("MUA has received {} entries from {}", pushed.getEntries().size(), pushed.getUaMetaData().getUaName());
		engine.addAuthentications(pushed);
		success = true;
	    } catch (Exception e) {
		// TODO either throw as service output, or deal with here
		log.error(e.getMessage());
		e.printStackTrace();
	    } finally {
		lockR.unlock();

	    }
	}
	if (!success)
	    log.warn("Lock was hit for method addAuthentications");

    }

	@Override
	public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
		if (lockR.tryLock()) {
		    try {
		    	log.info("Webservice call to update and perform statistic {}",statisticalUnitInformation.getStatisticParameters().getUnitName());
		    	engine.updateStatisticalUnit(statisticalUnitInformation);
				return engine.performStatistic(statisticalUnitInformation.getStatisticParameters().getUnitName());
		    } catch (Exception e) {
				// TODO either throw as service output, or deal with here
				log.error(e.getMessage());
				e.printStackTrace();
		    } finally {
		    	lockR.unlock();
		    }
		}
		log.warn("Lock was hit for method updateAndInvokeStatisticalUnit");
		return null;

	}

}
