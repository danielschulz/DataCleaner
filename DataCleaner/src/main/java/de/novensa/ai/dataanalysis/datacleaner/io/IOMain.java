package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Context;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.SkyContext;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;

import java.io.IOException;
import java.util.Map;

/**
 * The process starting by reading data from FS.
 *
 * @author Daniel Schulz
 */
public class IOMain extends Context {

    private final Context context = new SkyContext();

    public static void main(String[] args) throws IOException {
        IOMain ioMain = new IOMain();
        ioMain.extractOne();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void extractOne() throws IOException {
        ExtractArchives extractor = new ExtractArchives(this.getContext());
        LoadCsvContents csvLoader = new LoadCsvContents(this.getContext());

        ExtractionDeletionInstance extractionDeletionInstance = extractor.extract(WORKING_DIRECTORY,
                ARCHIVE_FILE_IN_WORKING_DIRECTORY);
        Map<String, CsvDataFrame> fileMap = csvLoader.exploreJustExtractedFiles(extractionDeletionInstance);

        Map<String, CsvDataFrame> signatureSensitiveMap = csvLoader.makeSignatureSensitiveMap(fileMap);


        // clean minimal
        extractionDeletionInstance.clean();

        // revert everything extracted to file system
        extractionDeletionInstance.forceToCleanEverything();
    }


    @Override
    public Context getContext() {
        return this.context.getContext();
    }
}
