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

package uk.ac.cardiff.raptor.remoting.server.sei;

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

    /**
     * Gets the internal version number of this MUA. This does not have to correspond to the Maven or SCM raptor-mua
     * release version
     * 
     * @return the internal version number of this MUA
     */
    public String getVersion();

    /**
     * Returns the capabilities of this MUA. This includes:
     * <ol>
     * <li>A list of statistical operations this MUA can perform.</li>
     * <li>The Service Metadata associated to this MUA.</li>
     * <li>The number of events this MUA has stored</li>
     * <li>Metadata describing the all resource this MUA has 'seen'.</li>
     * <li>Flags and error messages describing processing status</li>
     * </ol>
     * 
     * @return
     */
    public Capabilities getCapabilities();

    /**
     * Updates the statistical unit described in the <code>statisticalUnitInformation</code> parameter.
     * 
     * @param statisticalUnitInformation information about the statistical unit to update
     * @throws SoapFault
     */
    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;

    /**
     * Invokes the statistical unit with name <code>statisticName</code> and returns the result of that unit as a
     * <code>AggregatorGraphModel</code>
     * 
     * @param statisticName the <code>String</code> name of the statistical unit to invoke
     * @return the result of invoking the statistical unit as a <code>AggregatorGraphModel</code>
     * @throws SoapFault
     */
    public AggregatorGraphModel invokeStatisticalUnit(String statisticName) throws SoapFault;

    /**
     * A single shot interface that both updates and then performs a statistic (returning the result) in one go. This is
     * useful if you want to perform both operations while there is a lock on the class so as other operations do not
     * take place between update and invoke.
     * 
     * @param statisticalUnitInformation
     * @return
     */
    public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation)
            throws SoapFault;

    /**
     * Asks this MUA to perform an administrative function e.g. removeall stored events.
     * 
     * @param function information about the function to peform
     * @return true if the administrative function performed successfully, false otherwise
     * @throws SoapFault
     */
    public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault;

    /**
     * Adds events to this MUA
     * 
     * @param pushed the <code>EventPushMessage</code> that the events this MUA is to store are encapsulated in
     * @throws SoapFault
     */
    public void addAuthentications(EventPushMessage pushed) throws SoapFault;

    /**
     * Send a batch of log files to the MUA to store
     * 
     * @param uploadFiles the files to parse and store events from
     * @return
     */
    public List<LogFileUploadResult> batchUpload(List<LogFileUpload> uploadFiles);

    /**
     * Save resource information in the <code>resourceMetadata</code> parameter to this MUA's persistent store.
     * 
     * @param resourceMetadata
     */
    public void saveResourceMetadata(List<ResourceMetadata> resourceMetadata);

}
