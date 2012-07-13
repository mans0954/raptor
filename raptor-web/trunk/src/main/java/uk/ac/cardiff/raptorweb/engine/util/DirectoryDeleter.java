package uk.ac.cardiff.raptorweb.engine.util;

import java.io.File;

public class DirectoryDeleter {

    /**
     * Deletes a directory including all its sub directories and files.
     * 
     * @param path
     */
    public static void deleteDirectory(File path) {
        if (path == null)
            return;
        if (path.exists()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) {
                    deleteDirectory(f);
                    f.delete();
                } else {
                    f.delete();
                }
            }
            path.delete();
        }
    }

}
