package de.novensa.ai.dataanalysis.datacleaner;

import de.novensa.ai.dataanalysis.datacleaner.io.ExtractArchives;
import de.novensa.ai.dataanalysis.datacleaner.io.LoadCsvContents;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws IOException {
        ExtractionDeletionInstance extractionDeletionInstance = ExtractArchives.extract();
        LoadCsvContents.exploreFilesInFolders(extractionDeletionInstance);

        // clean minimal
        extractionDeletionInstance.clean();

        // revert everything extracted to file system
        extractionDeletionInstance.forceToCleanEverything();
    }
}
