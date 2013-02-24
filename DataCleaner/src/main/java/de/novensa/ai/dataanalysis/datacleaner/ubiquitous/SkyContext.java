package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

import java.io.File;

/**
 * SkyContext delivers all kind of information relevant through out the application.
 *
 * @author Daniel Schulz
 */

public class SkyContext extends Context {

    private File workingDir = new File("./");

    public File getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(File workingDir) {
        this.workingDir = workingDir;
    }

    public SkyContext() {
    }

    public SkyContext(String workingDir) {

        if (null != workingDir) {
            setWorkingDir(new File(workingDir));
        }
    }

    @Override
    public SkyContext getContext() {
        return this;
    }
}
