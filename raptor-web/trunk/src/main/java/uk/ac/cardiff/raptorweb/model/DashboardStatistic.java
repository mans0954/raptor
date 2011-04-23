package uk.ac.cardiff.raptorweb.model;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardStatistic {
    
    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(DashboardStatistic.class);
    
    /** The name of ths statistical unit in the MUA that is invoked*/
    private String statisticalUnitName;
    
    /** Whether the statistic is to be computed */
    private boolean enabled;
    
    
    /** The type of event this statistic should work over. For example shibboleth or ezproxy*/
    private StartModel.EventType eventType;


    public void setStatisticalUnitName(String statisticalUnitName) {
        this.statisticalUnitName = statisticalUnitName;
    }

    public String getStatisticalUnitName() {
        return statisticalUnitName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEventType(StartModel.EventType eventType) {
        this.eventType = eventType;
    }

    public StartModel.EventType getEventType() {
        return eventType;
    }

}
