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

package uk.ac.cardiff.raptorweb.service;

import uk.ac.cardiff.raptorweb.model.wizard.GraphWizardModel;
import uk.ac.cardiff.raptorweb.model.wizard.GraphWizardReports;
import uk.ac.cardiff.raptorweb.model.wizard.SavedGraphWizardModel;

public interface SavedWizardReportsService {

    /**
     * Marshals the {@link GraphWizardModel} to XML.
     * 
     * @param model
     */
    public void save(SavedGraphWizardModel model, String user);

    /**
     * Loads the set of reports into the {@link GraphWizardReports} from xml files in the users report directory.
     * 
     * @param user
     * @return
     */
    public void load(GraphWizardReports reports, String user);

    /**
     * @param reports
     */
    public void removeReport(GraphWizardReports reports);

    /**
     * Loads a report from the URL specified in the {@link GraphWizardReports#reportToDownloadUrl} and saves it to the
     * current users home reports directory.
     * 
     * @param reports
     */
    public void loadReportFromUrl(GraphWizardReports reports, String user);

}
