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
package uk.ac.cardiff.raptormua.wsinterface.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.binding.soap.SoapFault;


import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.AdministrativeFunction.AdministrativeFunctionType;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;
import uk.ac.cardiff.raptormua.service.MUAProcess;

/**
 * @author philsmart
 *
 */

@WebService(endpointInterface = "uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator")
public class MultiUnitAggregatorImpl implements MultiUnitAggregator{

	private MUAProcess processService;

	public String getVersion() {
		return "Alpha";
	}

	public void setProcessService(MUAProcess processService) {
		this.processService = processService;
	}

	public MUAProcess getProcessService() {
		return processService;
	}

	public Capabilities getCapabilities() {
		// TODO Auto-generated method stub
		return processService.getCapabilities();
	}


	public AggregatorGraphModel invokeStatisticalUnit(String statisticName) throws SoapFault {
		return processService.performStatistic(statisticName);
	}


	@Override
	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault {
	    processService.updateStatisticalUnit(statisticalUnitInformation);

	}


	@Override
	public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault {
	    return processService.performAdministrativeFunction(function);
	}


	@Override
	public void addAuthentications(EventPushMessage pushed) throws SoapFault{
	    processService.addAuthentications(pushed);

	}

	@Override
	public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault {
		return processService.updateAndInvokeStatisticalUnit(statisticalUnitInformation);
	}

	public boolean batchUpload(List<LogFileUpload> uploadFiles) {
		return processService.batchUpload(uploadFiles);
	}

}
