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
package uk.ac.cardiff.raptormua.service;

import org.apache.cxf.binding.soap.SoapFault;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.AdministrativeFunction.AdministrativeFunctionType;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

/**
 * @author philsmart
 *
 */
public interface MUAProcess {

	/**
	 * @param statisticName
	 */
	public AggregatorGraphModel performStatistic(String statisticName) throws SoapFault;

	/**
	 * @return
	 */
	public Capabilities getCapabilities();

	/**
	 * @param statisticalUnitInformation
	 */
	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;

	/**
	 * @param function
	 */
	public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault;

	/**
	 * @param pushed
	 */
	public void addAuthentications(EventPushMessage pushed) throws SoapFault;

	/**
	 *
	 * @param statisticalUnitInformation
	 * @return
	 */
	public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;
}
