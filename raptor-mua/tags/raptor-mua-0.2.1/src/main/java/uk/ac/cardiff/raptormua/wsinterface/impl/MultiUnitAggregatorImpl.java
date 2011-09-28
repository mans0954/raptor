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
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;
import uk.ac.cardiff.raptormua.service.MUAProcess;

/**
 * The Class MultiUnitAggregatorImpl.
 * 
 * @author philsmart
 */

@WebService(endpointInterface = "uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator")
public class MultiUnitAggregatorImpl implements MultiUnitAggregator {

    /** The process service. */
    private MUAProcess processService;

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#getVersion()
     */
    public String getVersion() {
        return "Alpha";
    }

    /**
     * Sets the process service.
     * 
     * @param processService the new process service
     */
    public void setProcessService(MUAProcess processService) {
        this.processService = processService;
    }

    /**
     * Gets the process service.
     * 
     * @return the process service
     */
    public MUAProcess getProcessService() {
        return processService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#getCapabilities()
     */
    public Capabilities getCapabilities() {
        // TODO Auto-generated method stub
        return processService.getCapabilities();
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#invokeStatisticalUnit(java.lang.String)
     */
    public AggregatorGraphModel invokeStatisticalUnit(String statisticName) throws SoapFault {
        return processService.performStatistic(statisticName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#updateStatisticalUnit(uk.ac.cardiff.model.wsmodel
     * .StatisticalUnitInformation)
     */
    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault {
        processService.updateStatisticalUnit(statisticalUnitInformation);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#performAdministrativeFunction(uk.ac.cardiff.model
     * .AdministrativeFunction)
     */
    public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault {
        return processService.performAdministrativeFunction(function);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#addAuthentications(uk.ac.cardiff.model.wsmodel.
     * EventPushMessage)
     */
    public void addAuthentications(EventPushMessage pushed) throws SoapFault {
        processService.addAuthentications(pushed);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#updateAndInvokeStatisticalUnit(uk.ac.cardiff.model
     * .wsmodel.StatisticalUnitInformation)
     */
    public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation)
            throws SoapFault {
        return processService.updateAndInvokeStatisticalUnit(statisticalUnitInformation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#batchUpload(java.util.List)
     */
    public List<LogFileUploadResult> batchUpload(List<LogFileUpload> uploadFiles) throws SoapFault {
        return processService.batchUpload(uploadFiles);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator#saveResourceMetadata(java.util.List)
     */
    public void saveResourceMetadata(List<ResourceMetadata> resourceMetadata) {
        processService.saveResourceMetadata(resourceMetadata);

    }

}
