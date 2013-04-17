
package uk.ac.cardiff.raptorweb.model.wizard;

import java.io.File;
import java.io.Serializable;

/**
 *
 */
public class SavedGraphWizardModel implements Serializable {

    /**
     * Generated serial UID
     */
    private static final long serialVersionUID = 2879383890406013653L;

    /**
     * The actual graph wizard model to use.
     */
    private GraphWizardModel graphWizardModel;

    /**
     * Locqtion of the report on the filesystem;
     */
    private File filename;

    /**
     * If the report is being edited. If false, the report is new. If edited it should overwrite the old saved report.
     */
    private boolean edittingReport = false;

    /**
     * @return Returns the graphWizardModel.
     */
    public GraphWizardModel getGraphWizardModel() {
        return graphWizardModel;
    }

    /**
     * @param graphWizardModel The graphWizardModel to set.
     */
    public void setGraphWizardModel(GraphWizardModel graphWizardModel) {
        this.graphWizardModel = graphWizardModel;
    }

    /**
     * @return Returns the filename.
     */
    public File getFilename() {
        return filename;
    }

    /**
     * @param filename The filename to set.
     */
    public void setFilename(File filename) {
        this.filename = filename;
    }

    /**
     * @return Returns the edittingReport.
     */
    public boolean isEdittingReport() {
        return edittingReport;
    }

    /**
     * @param edittingReport The edittingReport to set.
     */
    public void setEdittingReport(boolean edittingReport) {
        this.edittingReport = edittingReport;
    }

}
