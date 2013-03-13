package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

import java.io.File;
import java.io.IOException;

/**
 * SkyContext delivers all kind of information relevant through out the application.
 *
 * @author Daniel Schulz
 */

public class SkyContext extends Context {

    private String workingDir = "./";

    @SuppressWarnings("UnusedDeclaration")
    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @SuppressWarnings("UnusedDeclaration")
    public SkyContext() {
    }

    @SuppressWarnings({"UnusedDeclaration", "ResultOfMethodCallIgnored"})
    public SkyContext(String workingDir) {

        if (null != workingDir) {
            final File workingDirFile = new File(workingDir);
            String workingDirString;

            if(!workingDirFile.exists()) {
                workingDirFile.mkdirs();
            }

            try {
                workingDirString = workingDirFile.getCanonicalPath();
            } catch (IOException e) {
                throw new IllegalArgumentException(
                        ErrorMessages.getWorkingDirectoryCannotBeFound(workingDirFile.toString()));
            }

            setWorkingDir(workingDirString);
        }
    }

    @Override
    public SkyContext getContext() {
        return this;
    }
}
