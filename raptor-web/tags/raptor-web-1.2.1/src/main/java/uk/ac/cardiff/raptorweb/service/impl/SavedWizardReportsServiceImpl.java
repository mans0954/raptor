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

package uk.ac.cardiff.raptorweb.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import uk.ac.cardiff.raptorweb.engine.util.MessageGenerator;
import uk.ac.cardiff.raptorweb.model.wizard.GraphWizardModel;
import uk.ac.cardiff.raptorweb.model.wizard.GraphWizardReports;
import uk.ac.cardiff.raptorweb.model.wizard.SavedGraphWizardModel;
import uk.ac.cardiff.raptorweb.service.SavedWizardReportsService;

/**
 *
 */
public class SavedWizardReportsServiceImpl implements SavedWizardReportsService {

    /**
     * The marshaller used to save the model object.
     */
    private Marshaller marshaller;

    /**
     * The unmarshaller used to load the model objects.
     */
    private Unmarshaller unmarshaller;

    /**
     * The base directory where to save reports.
     */
    private Resource baseDirectory;

    /**
     * The actual directory to save reports.
     */
    private Resource saveDirectory;

    private final String DATE_SAVED_FORMAT = "EEE, dd MMM yyyy (HH:mm z)";

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(SavedWizardReportsServiceImpl.class);

    public String getRootDirectory(String user) {
        String root = null;
        try {
            // make sure base directory exists first
            File baseGraphDirectory = saveDirectory.getFile();
            if (!baseGraphDirectory.exists())
                baseGraphDirectory.mkdir();

            // then check for the existence of the users directory within the base
            root = saveDirectory.getFile().getCanonicalPath() + "/" + user;
            File dir = new File(root);
            log.debug("Save directory for charts exists: " + dir.exists());
            if (!dir.exists())
                dir.mkdir();

        } catch (IOException e) {
            log.error("Could not create save directory for JFree charts, {}", e.getMessage());
        }
        return root;
    }

    @Override
    public void removeReport(GraphWizardReports reports) {
        log.info("Removing saved report [{}]");
        // remove from the list of saved reports immediately
        reports.getSavedWizardModels().remove(reports.getSelectedReport());
        // remove from the filesystem.
        try {
            reports.getSelectedReport().getFilename().delete();
        } catch (Exception e) {
            log.error("Could not delete report", e);
        }

    }

    @Override
    public void loadReportFromUrl(GraphWizardReports reports, String user) {
        log.info("Loading a report from URL [{}]", reports.getReportToDownloadUrl());
        String filenameFromUrl =
                reports.getReportToDownloadUrl().substring(reports.getReportToDownloadUrl().lastIndexOf('/'),
                        reports.getReportToDownloadUrl().length());
        try {
            filenameFromUrl = URLDecoder.decode(filenameFromUrl, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            log.error("Filename from URL could not be URL decoded, hence the resultant filename may contain URL encoded characters");
        }
        String filename = getRootDirectory(user) + "/" + filenameFromUrl;
        log.debug("Saving report with filename [{}]", filename);
        try {
            URL reportUrl = new URL(reports.getReportToDownloadUrl());
            log.debug("Report URL location constructed [{}]", reportUrl);
            BufferedInputStream in = new BufferedInputStream(reportUrl.openStream());
            FileOutputStream out = new FileOutputStream(filename);
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);
            }
            out.close();

        } catch (MalformedURLException e) {
            log.error("Report can not be loaded from URL [{}], URL is not valid", reports.getReportToDownloadUrl());
        } catch (IOException e) {
            log.error("Report can not be saved to the file system [filename={}]", filename, e);
        } catch (Exception e) {
            log.error("Could not save remote report, general exception thrown", e);
        }

    }

    /** {@inheritDoc} */
    @Override
    public void save(SavedGraphWizardModel model, String user) {
        log.info("Saving graph wizard model");
        FileOutputStream os = null;
        try {

            String filename = null;
            if (model.isEdittingReport()) {
                filename = model.getFilename().getCanonicalPath();
                model.getGraphWizardModel().setDateModifiedFormatted(savedDate());
            } else {
                String directory = getRootDirectory(user);
                model.getGraphWizardModel().setDateSavedFormatted(savedDate());
                model.getGraphWizardModel().setDateModifiedFormatted(savedDate());
                model.getGraphWizardModel().setCreatedBy(user);

                filename =
                        directory + "/report-" + model.getGraphWizardModel().getGraphTitle() + "-"
                                + model.getGraphWizardModel().getDateSavedFormatted().replace(":", "-") + ".xml";
                model.setFilename(new File(filename));
            }

            os = new FileOutputStream(filename);
            log.debug("Saving report to [{}]", filename);
            this.marshaller.marshal(model.getGraphWizardModel(), new StreamResult(os));
            if (model.isEdittingReport()) {
                MessageGenerator.addInfo("ReSaved [" + new Date() + "]");
            } else {
                MessageGenerator.addInfo("Saved [" + new Date() + "]");
            }

        } catch (FileNotFoundException e) {
            log.error("Could not save graph wizard report", e);
        } catch (XmlMappingException e) {
            log.error("Could not save graph wizard report", e);
        } catch (IOException e) {
            log.error("Could not save graph wizard report", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("Could not close saved graph wizard report xml", e);
                }
            }
        }
    }

    private String savedDate() {
        DateTime now = new DateTime();
        return now.toString(DATE_SAVED_FORMAT);
    }

    @Override
    public void load(GraphWizardReports reports, String user) {

        String directory = getRootDirectory(user);

        log.info("Loading graph wizard reports from directory [{}]", directory);
        File directoryFile = new File(directory);
        if (!directoryFile.isDirectory()) {
            log.error("Report directory was not a directory, no reports to return");
            return;
        }
        for (File file : directoryFile.listFiles()) {
            try {
                if (!file.isHidden()) {
                    FileInputStream input = new FileInputStream(file);
                    GraphWizardModel modelIn =
                            (GraphWizardModel) unmarshaller.unmarshal(new StreamSource(input, "UTF8"));
                    SavedGraphWizardModel savedModel = new SavedGraphWizardModel();
                    savedModel.setGraphWizardModel(modelIn);
                    savedModel.setFilename(file);
                    reports.addSavedWizardModel(savedModel);
                    log.debug("Loaded report [{}]", file.getCanonicalPath());
                }
            } catch (Exception e) {
                log.warn("Could not load reporting file {}", file, e);
            }
        }

    }

    /**
     * @return Returns the marshaller.
     */
    public Marshaller getMarshaller() {
        return marshaller;
    }

    /**
     * @param marshaller The marshaller to set.
     */
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    /**
     * @return Returns the unmarshaller.
     */
    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    /**
     * @param unmarshaller The unmarshaller to set.
     */
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    /**
     * @return Returns the baseDirectory.
     */
    public Resource getBaseDirectory() {
        return baseDirectory;
    }

    /**
     * @param baseDirectory The baseDirectory to set.
     */
    public void setBaseDirectory(Resource baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * @return Returns the saveDirectory.
     */
    public Resource getSaveDirectory() {
        return saveDirectory;
    }

    /**
     * @param saveDirectory The saveDirectory to set.
     */
    public void setSaveDirectory(Resource saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

}
