
package uk.ac.cardiff.raptorweb.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

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

    private final String DATE_SAVED_FORMAT = "EEE dd yyyy (HH:mm)";

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
                filename =
                        directory + "/report-" + model.getGraphWizardModel().getGraphTitle() + "-"
                                + model.getGraphWizardModel().getDateSavedFormatted().replace(":", "-") + ".xml";
            }

            os = new FileOutputStream(filename);
            log.debug("Saving report to [{}]", filename);
            this.marshaller.marshal(model.getGraphWizardModel(), new StreamResult(os));
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
