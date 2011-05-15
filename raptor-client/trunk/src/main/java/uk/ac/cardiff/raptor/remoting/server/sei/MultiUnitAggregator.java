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
package uk.ac.cardiff.raptor.remoting.server.sei;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.binding.soap.SoapFault;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;


/**
 * @author philsmart
 *
 */

@WebService
public interface MultiUnitAggregator {

	public String getVersion();

	public Capabilities getCapabilities();

	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;

	public AggregatorGraphModel invokeStatisticalUnit(String statisticName) throws SoapFault;

	/**
	 * A single shot interface that both updates and then performs a statistic (returning the result)
	 * in one go. This is useful if you want to perform both operations while there is a lock on the class
	 * so as other operations do not take place between update and invoke.
	 *
	 * @param statisticalUnitInformation
	 * @return
	 */
	public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;

	public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault;

	public void addAuthentications(EventPushMessage pushed) throws SoapFault;

	/** Send a batch of log files to the MUA to store */
	public List<LogFileUploadResult>  batchUpload(List<LogFileUpload> uploadFiles);


	public void saveResourceMetadata(List<ResourceMetadata> resourceMetadata);

}
