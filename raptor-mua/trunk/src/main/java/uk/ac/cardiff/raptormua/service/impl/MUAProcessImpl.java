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

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.AdministrativeFunction.AdministrativeFunctionType;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptormua.engine.MUAEngine;
import uk.ac.cardiff.raptormua.service.MUAProcess;

/**
 * All operations should go through this service class, so as to obey locks and
 * synchronisation issues. Locks collisions are thrown use a
 * <code>SoapFault</code>. Fault codes are: Client (if a malformed input e.g.
 * statistic name is wrong) Server (we use for locks, as server side issue)
 * VersionMismatch MustUnderstand
 *
 * @author philsmart
 *
 */
public class MUAProcessImpl implements MUAProcess {

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(MUAProcessImpl.class);

	/** main engine of the MultiUnitAggregator */
	private MUAEngine engine;

	/**
	 * ReentrantLock to prevent more than at the same time
	 */
	final Lock lockR = new ReentrantLock();

	public void setEngine(MUAEngine engine) {
		this.engine = engine;
	}

	public MUAEngine getEngine() {
		return engine;
	}


	public AggregatorGraphModel performStatistic(String statisticName) throws SoapFault {
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
		throw new SoapFault("lock was hit on method performStatistic", new QName("Server"));

	}


	public Capabilities getCapabilities() {
		log.info("WebSservice call for get capabilities");
		return engine.getCapabilities();
	}


	@Override
	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault {
		boolean success = false;
		if (lockR.tryLock()) {
			try {
				log.info("Updating statistical unit {}", statisticalUnitInformation.getStatisticParameters().getUnitName());
				engine.updateStatisticalUnit(statisticalUnitInformation);
				success = true;
			} catch (Exception e) {
				// TODO either throw as service output, or deal with here
				log.error(e.getMessage());
				e.printStackTrace();
			} finally {
				lockR.unlock();
			}
		}
		if (!success) {
			log.warn("Lock was hit for method updateStatisticalUnit");
			throw new SoapFault("lock was hit on method updateStatisticalUnit", new QName("Server"));
		}
	}


	@Override
	public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault {
		if (lockR.tryLock()) {
			try {
				log.info("Performing administrative function {}, request orginates from {}",
						function.getAdministrativeFunction(), function.getRequester());
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
		throw new SoapFault("lock was hit on method performAdministrativeFunction", new QName("Server"));
	}

	/**
	 * Because this is perform async, the lock is not useful, its the exceptions that are.
	 */
	@Override
	public void addAuthentications(EventPushMessage pushed) throws SoapFault{
		boolean success = false;
		if (lockR.tryLock()) {
			try {
				log.info("MUA has received {} entries from {}", pushed.getEvents().size(), pushed.getClientMetadata().getClientName());
				engine.addAuthentications(pushed);
				success = true;
			} catch (Exception e) {
				log.error("Error trying to add authentications to this MUA, {}",e.getMessage());

			} finally {
				lockR.unlock();

			}
		}
		else
		    log.warn("Lock was hit for method addAuthentications");
		if (!success){
		    log.error("WARNING, technical fault, could not add events to this MUA");
		    throw new SoapFault("Technical fault at the server, could not add events to MUA ["+this.getEngine().getMuaMetadata().getServerName()+"]", new QName("Server"));
		}


	}

	@Override
	public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation)
			throws SoapFault {
		if (lockR.tryLock()) {
			try {
				log.info("Webservice call to update and perform statistic {}", statisticalUnitInformation
						.getStatisticParameters().getUnitName());
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
		throw new SoapFault("lock was hit on method updateAndInvokeStatisticalUnit", new QName("Server"));

	}

}
