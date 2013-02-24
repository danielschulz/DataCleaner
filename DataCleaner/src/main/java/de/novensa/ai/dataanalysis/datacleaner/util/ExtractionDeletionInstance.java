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
    List<File> folders;
    List<File> files;
    final File fileMedian;
    final File fileFinal;

    public ExtractionDeletionInstance(ExtractionDeletionStrategy extractionDeletionStrategy,
                                      List<File> folders,
                                      List<File> files,
                                      File fileMedian,
                                      File fileFinal) {

        // throw exception if ExtractionDeletionStrategy is not valid
        if (null == extractionDeletionStrategy ||
                ExtractionDeletionStrategy.DEFAULT.equals(extractionDeletionStrategy)) {

            throw new IllegalArgumentException(ErrorMessages.EXTRACTION_DELETION_STRATEGY_NOT_ALLOWED_IN_INSTANCE);
        }

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
            f.delete();
        }
    }

    public void forceToCleanEverything() {
        this.extractionDeletionStrategy = ExtractionDeletionStrategy.DELETE_EVERYTHING;
        clean();
    }


    public ExtractionDeletionStrategy getExtractionDeletionStrategy() {
        return extractionDeletionStrategy;
    }

    public File getFileMedian() {
        return fileMedian;
    }

    public File getFileFinal() {
        return fileFinal;
    }

    public List<File> getFolders() {
        return folders;
    }

    public List<File> getFiles() {
        return files;
    }
}
