
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

public interface StatisticRegistry {

    /**
     * updates a statistical unit based on the values in the <code>statisticalUnitInformation</code> parameter.
     * 
     * @param statisticalUnitInformation the information used to update the relevant statistic
     */
    public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation);

    /**
     * Gets the statistic associated to the <code>statisticName</code> parameter.
     * 
     * @param statisticName - the unit name of the statistic to receive.
     * @return
     */
    public Statistic getStatistic(String statisticName);

    /**
     * Registers a List of statistics with this StatisticsHandler
     * 
     * @param statisticalUnits - the <code>Statistic</code>s to register
     */
    public void setStatisticalUnits(List<Statistic> statisticalUnits);

    /**
     * Returns a <code>List</code> of currently registered statistics
     * 
     * @return a <code>List</code> of currently registered statistics
     */
    public List<Statistic> getStatisticalUnits();

}
