
package uk.ac.cardiff.raptorweb.service;

import uk.ac.cardiff.raptorweb.model.wizard.GraphWizardModel;

/**
 *
 */
public interface GraphWizardService {

    /**
     * Computes the current time range from the given qualitative time range in the input {@link GraphWizardModel}.
     * 
     * @param model
     */
    public void computeCurrentTimeRange(GraphWizardModel model);

    /**
     * Puts together a list of StatisticalUnitInformation objects from those graph options chosen in the graph wizard.
     * 
     * @param model
     */
    public void compileStatisticalUnitInformation(GraphWizardModel model);

    public void addSeriesToSelectedStatistic(GraphWizardModel model);

    public void addFilterToSelectedSeries(GraphWizardModel model);

    /**
     * Populates the suggestion values that assist users in selecting certain statistical parameters.
     * 
     * @param model
     */
    public void populateSuggestionValues(GraphWizardModel model);

    /**
     * @param model
     */
    void removeSelectedFilterFromSelectedStatistic(GraphWizardModel model);

    /**
     * @param model
     */
    void removeSeriesFromSelectedStatistic(GraphWizardModel model);

    public void generateExcelReport(GraphWizardModel model);

    public void generateCSVReport(GraphWizardModel model);

    public void generatePDFReport(GraphWizardModel model);

    public void loadSavedReports(GraphWizardModel model);

}
