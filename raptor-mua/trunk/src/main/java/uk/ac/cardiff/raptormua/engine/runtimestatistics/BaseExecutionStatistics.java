
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import java.util.Date;

public class BaseExecutionStatistics {

    /** The date from which the statistics should be considered valid. */
    protected Date validAt;

    /**
     * @return the validAt
     */
    public Date getValidAt() {
        return validAt;
    }

}
