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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.store.TransactionInProgressException;
import uk.ac.cardiff.raptormua.engine.MUAEngine;
import uk.ac.cardiff.raptormua.service.MUAProcess;
import uk.ac.cardiff.raptormua.upload.BatchFile;
import uk.ac.cardiff.raptormua.upload.FileUploadEngine;

/**
 * All operations should go through this service class, so as to obey locks and synchronisation issues. Locks collisions
 * are thrown use a <code>SoapFault</code> . Fault codes are: Client (if a malformed input e.g. statistic name is wrong)
 * Server (we use if a lock is hit, as a server side issue).
 * 
 * @author philsmart
 * 
 */
public class MUAProcessImpl implements MUAProcess {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(MUAProcessImpl.class);

    /**
     * main engine of the MultiUnitAggregator, that handles all common functions
     */
    private MUAEngine engine;

    /** Engine from which to scan for files in defined upload directories from which to parse Events */
    private FileUploadEngine fileUploadEngine;

    /**
     * ReentrantLock to prevent more than one operation at the same time
     */
    final Lock lockR = new ReentrantLock();

    public AggregatorGraphModel performStatistic(String statisticName) throws SoapFault {
        if (lockR.tryLock()) {
            try {
                log.info("WebSservice call for perform statistic [{}] ", statisticName);
                return engine.performStatistic(statisticName);
            } catch (Exception e) {
                log.error("{}", e);
            } finally {
                lockR.unlock();
            }
        }
        log.warn("Lock was hit for method [performStatistic]");
        throw new SoapFault("lock was hit on method performStatistic", new QName("Server"));

    }

    public void release() {
        if (lockR.tryLock()) {
            try {
                engine.release();
            } catch (Exception e) {
                log.error("Error trying to release events, reason = [{}]", e.getMessage());
            } finally {
                lockR.unlock();
            }
        } else {
            log.warn("Lock was hit for method [release]");
        }
    }

    public Capabilities getCapabilities() {
        log.info("WebSservice call for get capabilities");
        return engine.getCapabilities();
    }

    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault {
        boolean success = false;
        if (lockR.tryLock()) {
            try {
                log.info("Updating statistical unit {}", statisticalUnitInformation.getStatisticParameters()
                        .getUnitName());
                engine.updateStatisticalUnit(statisticalUnitInformation);
                success = true;
            } catch (Exception e) {
                log.error("{}", e);
                success = false;
            } finally {
                lockR.unlock();
            }
        }
        if (!success) {
            log.warn("Lock was hit for method [updateStatisticalUnit]");
            throw new SoapFault("lock was hit on method updateStatisticalUnit", new QName("Server"));
        }
    }

    public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault {
        if (lockR.tryLock()) {
            try {
                log.info("Performing administrative function {}, request orginates from {}",
                        function.getAdministrativeFunction(), function.getRequester());
                return engine.performAdministrativeFunction(function);
            } catch (Exception e) {
                log.error("{}", e);
                return false;
            } finally {
                lockR.unlock();

            }

        }
        log.warn("Lock was hit for method [performAdministrativeFunction]");
        throw new SoapFault("lock was hit on method performAdministrativeFunction", new QName("Server"));
    }

    /**
     * Because this is performed async, the lock is not useful (it will be released immediately).
     * 
     * @param pushed the events to add to this MUA.
     */
    public void addAuthentications(EventPushMessage pushed) throws SoapFault {
        boolean success = false;
        if (lockR.tryLock()) {
            try {
                log.info("MUA has received {} entries from {} [{}]", new Object[] {pushed.getEvents().size(),
                        pushed.getClientMetadata().getEntityId(), pushed.getClientMetadata().getServiceName()});
                engine.addAuthentications(pushed);
                success = true;
            } catch (TransactionInProgressException e) {
                log.warn("Error trying to add authentications to this MUA, {}, client should retry automatically",
                        e.getMessage());
                success = false;
            } catch (Exception e) {
                log.error("Error trying to add authentications to this MUA", e);
                success = false;
            } finally {
                lockR.unlock();

            }
        } else
            log.warn("Lock was hit for method [addAuthentications]");
        if (!success) {
            throw new SoapFault("Technical fault at the server, could not add events to MUA ["
                    + this.getEngine().getMuaMetadata().getServiceName() + "]", new QName("Server"));
        }

    }

    public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation)
            throws SoapFault {
        if (lockR.tryLock()) {
            try {
                log.info("Webservice call to update and perform statistic [{}]", statisticalUnitInformation
                        .getStatisticParameters().getUnitName());
                engine.updateStatisticalUnit(statisticalUnitInformation);
                return engine.performStatistic(statisticalUnitInformation.getStatisticParameters().getUnitName());
            } catch (Exception e) {
                log.error("{}", e);
            } finally {
                lockR.unlock();
            }
        }
        log.warn("Lock was hit for method [updateAndInvokeStatisticalUnit]");
        throw new SoapFault("lock was hit on method updateAndInvokeStatisticalUnit", new QName("Server"));

    }

    public List<LogFileUploadResult> batchUpload(List<LogFileUpload> uploadFiles) throws SoapFault {
        List<LogFileUploadResult> result = new ArrayList<LogFileUploadResult>();
        boolean success = false;
        if (lockR.tryLock()) {
            try {
                log.info("Webservice call to parse {} batch log file(s)", uploadFiles.size());

                for (LogFileUpload logfile : uploadFiles) {
                    log.debug("Log File details: name [{}], MIME type [{}], Length [{}], ID [{}]", new Object[] {
                            logfile.getName(), logfile.getMime(), logfile.getData().length, logfile.getId()});
                }
                result = engine.batchParse(uploadFiles);
                success = true;
            } catch (TransactionInProgressException e) {
                log.error("Could not parse and store batch upload {}", e.getMessage());
                success = false;

            } finally {
                lockR.unlock();
            }
        } else {
            log.warn("Lock was hit for method [batchUpload]");
            throw new SoapFault("lock was hit on method batchUpload", new QName("Server"));
        }
        if (!success) {
            throw new SoapFault("Could not parse and store batch upload", new QName("Server"));
        }
        return result;

    }

    public void uploadFromDirectory() {
        List<BatchFile> files = fileUploadEngine.scanDirectories();
        if (files != null && files.size() > 0) {
            if (lockR.tryLock()) {
                try {
                    log.info("Uploading {} files from directory", files.size());
                    engine.batchParseFiles(files);
                } catch (TransactionInProgressException e) {
                    log.error("Could not parse and store batch uploads, will retry on next run");

                } finally {
                    lockR.unlock();
                }
            } else {
                log.warn("Lock was hit for method [batchUpload]");
            }
        }
    }

    public void resourceClassification() {
        log.info("Resource classification background thread called");
        // backgroundServices.resourceClassification();
    }

    public void saveResourceMetadata(List<ResourceMetadata> resourceMetadata) {
        log.info("Saving resource metadata (classification) for {} resources", resourceMetadata.size());
        engine.saveAndApplyResourceClassification(resourceMetadata);

    }

    public void setEngine(MUAEngine engine) {
        this.engine = engine;
    }

    public MUAEngine getEngine() {
        return engine;
    }

    /**
     * @param fileUploadEngine the fileUploadEngine to set
     */
    public void setFileUploadEngine(FileUploadEngine fileUploadEngine) {
        this.fileUploadEngine = fileUploadEngine;
    }

    /**
     * @return the fileUploadEngine
     */
    public FileUploadEngine getFileUploadEngine() {
        return fileUploadEngine;
    }

}
