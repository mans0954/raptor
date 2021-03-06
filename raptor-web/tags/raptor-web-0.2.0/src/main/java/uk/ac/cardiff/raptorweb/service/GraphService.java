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
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 * 
 */
public interface GraphService {

    public List<StatisticalUnitInformation> getStatisticalUnits();

    public void populateStatisticalUnits(WebSession websession);

    public void invokeStatisticalUnit(WebSession websession);

    public void updateAndInvokeStatisticalUnit(WebSession websession);

    public void generateExcelReport(WebSession websession);

    public void generateCSVReport(WebSession websession);

    public void generatePDFReport(WebSession websession);

    public void loadSavedReports(WebSession websession);

    public void updateMUAStatistic(WebSession websession);

    public void removeReport(WebSession websession);

    public Capabilities getAttachedCapabilities();

    public MUAEntry getCurrentlyAttached();

    public void removeSeriesFromSelectedStatistic(WebSession websession);

    public void removeSelectedFilterFromSelectedStatistic(WebSession websession);

    public void addSeriesToSelectedStatistic(WebSession websession);

    public void addFilterToSelectedSeries(WebSession websession);

    public void rerenderGraph(WebSession websession);

    public void addPostProcessorToSelectedStatistic(WebSession websession);

    public void removePostProcessorFromSelectedStatistic(WebSession websession);

}
