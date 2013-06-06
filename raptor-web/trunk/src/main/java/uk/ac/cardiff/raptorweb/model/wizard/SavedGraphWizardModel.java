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
     * If the report has been generated, set this to true. If so, this can be used as an indiciation that all fields
     * have been set and any modifictions can be made in part to the model.
     */
    private boolean finishedReport = false;

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

    /**
     * @return Returns the finishedReport.
     */
    public boolean isFinishedReport() {
        return finishedReport;
    }

    /**
     * @param finishedReport The finishedReport to set.
     */
    public void setFinishedReport(boolean finishedReport) {
        this.finishedReport = finishedReport;
    }

}
