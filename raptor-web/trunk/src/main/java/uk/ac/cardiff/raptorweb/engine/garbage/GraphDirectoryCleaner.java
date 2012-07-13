package uk.ac.cardiff.raptorweb.engine.garbage;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import uk.ac.cardiff.raptorweb.engine.util.DirectoryDeleter;

public final class GraphDirectoryCleaner {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(GraphDirectoryCleaner.class);

    /**
     * The {@link File} representation of the graphs directory that should be cleaned.
     */
    private File graphsDirectoryToClean;

    /**
     * The number of days (from today) that the cleaner should use as the date from which to remove graph directories.
     */
    private int daysBehindToRemoveFrom;

    public void clean() {
        if (graphsDirectoryToClean == null) {
            log.warn("Graphs Directory to clean does not exist, can not clean graphs directory");
            return;
        }

        DateTime today = new DateTime();
        DateTime fromDateTimeToRemove = today.minusDays(daysBehindToRemoveFrom);
        LocalDate fromDateToRemove = fromDateTimeToRemove.toLocalDate();
        log.debug("Cleaning Graphs Directory by removing graph folders on or before [{}]", fromDateToRemove);

        // remove from the root of a graphs directory set.
        for (File individualGraphDir : graphsDirectoryToClean.listFiles()) {
            DateTime lastModifiedDateTime = new DateTime(individualGraphDir.lastModified());
            LocalDate lastModifiedDate = lastModifiedDateTime.toLocalDate();
            boolean shouldRemove = false;

            if (lastModifiedDate.compareTo(fromDateToRemove) == -1 || lastModifiedDate.compareTo(fromDateToRemove) == 0) {
                shouldRemove = true;
            }

            if (shouldRemove) {
                DirectoryDeleter.deleteDirectory(individualGraphDir);
            }
            log.trace("graph directory {}, last modified {} with {}, can remove [{}]", new Object[] { individualGraphDir, lastModifiedDate, fromDateToRemove, shouldRemove });
        }

    }

    /**
     * If the input resource does not represent a file, the <code>graphsDirectoryToClean</code> is left null and an IllegalArgumentException is thrown.
     * 
     * @param graphsDirectoryToCleanResource
     *            the graphsDirectoryToCleanResource to set
     */
    public void setGraphsDirectoryToClean(Resource graphsDirectoryToCleanResource) {
        try {
            graphsDirectoryToClean = graphsDirectoryToCleanResource.getFile();
            log.debug("Graph directory to clean isDirectory [{}], is File [{}], exists ={}", new Object[] { graphsDirectoryToClean.isDirectory(), graphsDirectoryToClean.isFile(),
                    (graphsDirectoryToClean != null) });
            if (graphsDirectoryToClean.isDirectory() == false) {
                graphsDirectoryToClean = null;
                throw new IllegalArgumentException("Graphs location is not a directory, must be a directory");
            }
            log.info("Garbage Collection Service has been set to clean the directory [{}]", graphsDirectoryToClean);
        } catch (IOException e) {
            throw new IllegalArgumentException("Graphs location is not a directory or can not be converted to a directory location", e);
        }

    }

    /**
     * @param daysBehindToRemove
     *            the daysBehindToRemove to set
     */
    public void setDaysBehindToRemoveFrom(int daysBehindToRemove) {
        this.daysBehindToRemoveFrom = daysBehindToRemove;
    }

}
