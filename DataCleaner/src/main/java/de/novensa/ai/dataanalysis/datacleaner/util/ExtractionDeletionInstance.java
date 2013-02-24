package de.novensa.ai.dataanalysis.datacleaner.util;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * The instance for later deletion of extracted items.
 *
 * @author Daniel Schulz
 */
public class ExtractionDeletionInstance {

    ExtractionDeletionStrategy extractionDeletionStrategy;

    private final String workingDirectory;
    private List<File> folders;
    private List<File> files;
    private final File fileMedian;
    private final File fileFinal;

    public ExtractionDeletionInstance(ExtractionDeletionStrategy extractionDeletionStrategy,
                                      String workingDirectory,
                                      List<File> folders,
                                      List<File> files,
                                      File fileMedian,
                                      File fileFinal) {

        // throw exception if ExtractionDeletionStrategy is not valid
        if (null == extractionDeletionStrategy ||
                ExtractionDeletionStrategy.DEFAULT.equals(extractionDeletionStrategy)) {

            throw new IllegalArgumentException(ErrorMessages.EXTRACTION_DELETION_STRATEGY_NOT_ALLOWED_IN_INSTANCE);
        }

        this.workingDirectory = workingDirectory;
        this.folders = folders;
        this.files = files;

        this.extractionDeletionStrategy = extractionDeletionStrategy;
        this.fileMedian = fileMedian;
        this.fileFinal = fileFinal;
    }


    public void clean() {
        // clean files
        if (ExtractionDeletionStrategy.DELETE_MEDIAN_EXTRACTION_LEVEL.equals(extractionDeletionStrategy) ||
                ExtractionDeletionStrategy.DELETE_EVERYTHING.equals(extractionDeletionStrategy)) {

            if (fileMedian.exists() && fileMedian.canWrite()) {
                //noinspection ResultOfMethodCallIgnored
                fileMedian.delete();
            }
        }

        if (ExtractionDeletionStrategy.DELETE_EVERYTHING.equals(extractionDeletionStrategy)) {

            delete(fileFinal);

            for (File f : files) {
                delete(f);
            }

            Collections.reverse(folders);
            for (File f : folders) {
                delete(f);
            }
        }
    }

    private void delete(File f) {

        if (f.exists() && f.canWrite()) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }
    }

    public void forceToCleanEverything() {
        this.extractionDeletionStrategy = ExtractionDeletionStrategy.DELETE_EVERYTHING;
        clean();
    }


    @SuppressWarnings("UnusedDeclaration")
    public ExtractionDeletionStrategy getExtractionDeletionStrategy() {
        return extractionDeletionStrategy;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setExtractionDeletionStrategy(ExtractionDeletionStrategy extractionDeletionStrategy) {
        this.extractionDeletionStrategy = extractionDeletionStrategy;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    @SuppressWarnings("UnusedDeclaration")
    public File getFileMedian() {
        return fileMedian;
    }

    @SuppressWarnings("UnusedDeclaration")
    public File getFileFinal() {
        return fileFinal;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<File> getFolders() {
        return folders;
    }

    public List<File> getFiles() {
        return files;
    }
}
