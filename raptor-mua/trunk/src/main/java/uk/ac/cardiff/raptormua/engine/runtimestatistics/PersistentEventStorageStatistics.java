
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.store.QueryableEventHandler;

/**
 * Queries the persistence layer to determine the number and type of events stored for different time periods.
 * 
 * 
 */
public class PersistentEventStorageStatistics {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(PersistentEventStorageStatistics.class);

    /**
     * Event handler used to query the underlaying persistence store.
     */
    private QueryableEventHandler eventHandler;

    /**
     * Periods from which to gather statistics.
     */
    private List<StatisticPeriod> periods;

    public String getAllPeriodStatistics() {
        StringBuilder builder = new StringBuilder();
        for (StatisticPeriod period : periods) {
            DateTime start = period.getStart();
            DateTime end = period.getEnd();
            builder.append("[period start = ");
            builder.append(start.toString());
            builder.append(", period end = ");
            builder.append(end.toString());
            builder.append("] = ");
            builder.append(getCountForPeriod(start, end));
            builder.append(System.getProperty("line.separator"));

        }
        return builder.toString();
    }

    public String computePeriodStatistic(String periodName) {
        for (StatisticPeriod period : periods) {
            if (period.getPeriodIdentifier().equals(periodName)) {
                return getCountForPeriod(period.getStart(), period.getEnd());
            }
        }
        return "unknown";
    }

    private String getCountForPeriod(DateTime start, DateTime end) {
        try {
            String query = "select count(*) from Event where (eventTime between ? and ?)";
            Object[] parameters = new Object[] {start, end};
            log.debug("Query {}, with params {},{}", new Object[] {query, start, end});
            Object result = eventHandler.queryUnique(query, parameters);
            log.trace("result {}", result);
            if (result instanceof Integer) {
                return ((Integer) result).toString();
            } else if (result instanceof Long) {
                return ((Long) result).toString();
            }
        } catch (Exception e) {
            log.error("error trying to find count for period", e);
        }
        return "no results for that time period";
    }

    /**
     * @param eventHandler the eventHandler to set
     */
    public void setEventHandler(QueryableEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * @return the eventHandler
     */
    public QueryableEventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * @param periods the periods to set
     */
    public void setPeriods(List<StatisticPeriod> periods) {
        this.periods = periods;
    }

    /**
     * @return the periods
     */
    public List<StatisticPeriod> getPeriods() {
        return periods;
    }

}
