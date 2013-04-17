/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.cardiff.raptorweb.model.wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds saved graphical wizard reports for loading, selection, execution.
 */
public class GraphWizardReports implements Serializable {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(GraphWizardReports.class);

    /**
     * generated serial UID.
     */
    private static final long serialVersionUID = 6153308664214180169L;

    /**
     * list of saved or loaded {@link GraphWizardModel}s.
     */
    private List<SavedGraphWizardModel> savedWizardModels;

    /**
     * Tmp variable for the currently selected report.
     */
    private SavedGraphWizardModel selectedReport;

    /**
     * @return Returns the savedWizardModels.
     */
    public List<SavedGraphWizardModel> getSavedWizardModels() {
        if (savedWizardModels != null) {
            log.trace("Getting {} saved wizard models from {}", savedWizardModels.size(), this);
        }
        return savedWizardModels;
    }

    /**
     * @param savedWizardModels The savedWizardModels to set.
     */
    public void setSavedWizardModels(List<SavedGraphWizardModel> savedWizardModels) {
        this.savedWizardModels = savedWizardModels;
    }

    public SavedGraphWizardModel initEditReport() {
        log.info("Returning the graph wizard model to edit");
        selectedReport.getGraphWizardModel().setRunImmediatly(false);
        selectedReport.setEdittingReport(true);
        return selectedReport;
    }

    public SavedGraphWizardModel initRunReport() {
        log.info("Returning the graph wizard model to edit");
        selectedReport.getGraphWizardModel().setRunImmediatly(true);
        return selectedReport;
    }

    public SavedGraphWizardModel initNewReport() {
        log.info("Returning a new graph wizard model");
        SavedGraphWizardModel model = new SavedGraphWizardModel();
        model.setGraphWizardModel(new GraphWizardModel());
        model.getGraphWizardModel().setRunImmediatly(false);
        log.trace("Returning the new saved graph wizard model [{}] and graph wizard model [{}]", model,
                model.getGraphWizardModel());
        return model;
    }

    /**
     * @param modelIn
     */
    public void addSavedWizardModel(SavedGraphWizardModel modelIn) {
        if (modelIn == null) {
            log.error("Will not add a null graph wizard model");
            return;
        }
        if (savedWizardModels == null) {
            savedWizardModels = new ArrayList<SavedGraphWizardModel>();
        }
        savedWizardModels.add(modelIn);
        log.trace("Added loaded graph wizard model, now loaded {}, to {}", savedWizardModels.size(), this);

    }

    /**
     * @return Returns the selectedReport.
     */
    public SavedGraphWizardModel getSelectedReport() {
        return selectedReport;
    }

    /**
     * @param selectedReport The selectedReport to set.
     */
    public void setSelectedReport(SavedGraphWizardModel selectedReport) {
        this.selectedReport = selectedReport;
    }
}
