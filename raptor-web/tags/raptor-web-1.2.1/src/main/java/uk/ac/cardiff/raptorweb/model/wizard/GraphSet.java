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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.DynamicStatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.StatisticalUnitInformationView;

/**
 *
 */
public class GraphSet implements Serializable {

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(GraphSet.class);

	/**
	 * Default generated serial UID.
	 */
	private static final long serialVersionUID = 1772750085993032745L;

	/**
	 * Stores the tabular graph form.
	 */
	private RaptorTableChartModel tableModel;

	/**
	 * Holds the visual graph form.
	 */
	private RaptorJFreeChartModel graphModel;

	/**
	 * A list of {@link StatisticFunctionType}s that are used to select
	 * (dynamically) the type of graph function the required to construct a
	 * graph. This is a copy of the global one held by the
	 * {@link GraphWizardModel}, so that it is possible to select the correct
	 * one from a String input.
	 * **/
	private List<StatisticFunctionType> statisticFunctionTypes;

	/**
	 * The technical description of the statistic to perform.
	 */
	private StatisticalUnitInformationView statisticalUnitInformation;

	/**
	 * Which of the statisticFunctionTypes has been choosen.
	 */
	private StatisticFunctionType selectedStatisticFunctionType;

	/**
	 * Tmp storage of event type information.
	 */
	private String eventType;

	/**
	 * The compiled/encapsulated (information copied into) version of a
	 * {@link StatisticalUnitInformation} and {@link StatisticFunctionType}.
	 */
	private DynamicStatisticalUnitInformation dynamicStatisticalUnitInformation;

	public SelectItem[] getStatisticFunctionTypesSelectItems() {
		List<SelectItem> itemsList = new ArrayList<SelectItem>();
		itemsList.add(new SelectItem("", "Please Select Function Type"));
		
		for (StatisticFunctionType type : statisticFunctionTypes) {
			if (type.getAppliesToEventTypes() != null
					&& type.getAppliesToEventTypes().contains(eventType)) {
				itemsList.add(new SelectItem(type.getStatisticClass(), type
						.getFriendlyName() + " - " + type.getDescription()));
			}
		}
		return itemsList.toArray(new SelectItem[0]);
	}

	/**
	 * @return Returns the selectedStatisticFunctionType.
	 */
	public StatisticFunctionType getSelectedStatisticFunctionType() {
		return selectedStatisticFunctionType;
	}

	/**
	 * @param selectedStatisticFunctionType
	 *            The selectedStatisticFunctionType to set.
	 */
	public void setSelectedStatisticFunctionType(
			StatisticFunctionType selectedStatisticFunctionType) {
		this.selectedStatisticFunctionType = selectedStatisticFunctionType;
	}

	/**
	 * @return Returns the statistic class name of the
	 *         selectedStatisticFunctionType.
	 */
	public String getSelectedStatisticFunctionTypeString() {
		if (selectedStatisticFunctionType != null) {
			log.trace("Getting selected statistical function as {}",
					selectedStatisticFunctionType.getStatisticClass());
		}
		if (selectedStatisticFunctionType != null) {
			return selectedStatisticFunctionType.getStatisticClass();
		}
		return null;
	}

	/**
	 * @param selectedStatisticFunctionType
	 *            The selectedStatisticFunctionType to set.
	 */
	public void setSelectedStatisticFunctionTypeString(
			String selectedStatisticFunctionType) {
		log.trace("Setting selected statistic function type = [{}]",
				selectedStatisticFunctionType);
		for (StatisticFunctionType type : statisticFunctionTypes) {
			if (type.getStatisticClass().equals(selectedStatisticFunctionType)) {
				this.selectedStatisticFunctionType = type;
			}
		}
	}

	/**
	 * @return Returns the graphModel.
	 */
	public RaptorJFreeChartModel getGraphModel() {
		return graphModel;
	}

	/**
	 * @param graphModel
	 *            The graphModel to set.
	 */
	public void setGraphModel(RaptorJFreeChartModel graphModel) {
		this.graphModel = graphModel;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public RaptorTableChartModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(RaptorTableChartModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the statisticalUnitInformation.
	 */
	public StatisticalUnitInformationView getStatisticalUnitInformation() {
		return statisticalUnitInformation;
	}

	/**
	 * @param statisticalUnitInformation
	 *            The statisticalUnitInformation to set.
	 */
	public void setStatisticalUnitInformation(
			StatisticalUnitInformationView statisticalUnitInformation) {
		this.statisticalUnitInformation = statisticalUnitInformation;
	}

	/**
	 * @return Returns the eventType.
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            The eventType to set.
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return Returns the statisticFunctionTypes.
	 */
	public List<StatisticFunctionType> getStatisticFunctionTypes() {
		return statisticFunctionTypes;
	}

	/**
	 * @param statisticFunctionTypes
	 *            The statisticFunctionTypes to set.
	 */
	public void setStatisticFunctionTypes(
			List<StatisticFunctionType> statisticFunctionTypes) {
		this.statisticFunctionTypes = statisticFunctionTypes;
	}

	/**
	 * @return Returns the dynamicStatisticalUnitInformation.
	 */
	public DynamicStatisticalUnitInformation getDynamicStatisticalUnitInformation() {
		return dynamicStatisticalUnitInformation;
	}

	/**
	 * @param dynamicStatisticalUnitInformation
	 *            The dynamicStatisticalUnitInformation to set.
	 */
	public void setDynamicStatisticalUnitInformation(
			DynamicStatisticalUnitInformation dynamicStatisticalUnitInformation) {
		this.dynamicStatisticalUnitInformation = dynamicStatisticalUnitInformation;
	}

}
