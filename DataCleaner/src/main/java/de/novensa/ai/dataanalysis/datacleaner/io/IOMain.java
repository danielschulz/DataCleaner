package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Context;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.SkyContext;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The process starting by reading data from FS.
 *
 * @author Daniel Schulz
 */
public class IOMain extends Context {

    private final Context context = new SkyContext();
    private static final TarBz2ArchivesFileFilter TAR_BZ_2_ARCHIVES_FILE_FILTER = new TarBz2ArchivesFileFilter();

    public static void main(String[] args) throws IOException {
        IOMain ioMain = new IOMain();
        ioMain.extractOne();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void extractOne() throws IOException {
        ExtractArchives extractor = new ExtractArchives(this.getContext());
        LoadCsvContents csvLoader = new LoadCsvContents(this.getContext());

        final File wd = new File(WORKING_DIRECTORY);
        final File[] filesToExtract = wd.listFiles(TAR_BZ_2_ARCHIVES_FILE_FILTER);
        List<ExtractionDeletionInstance> extractionDeletionInstances = new ArrayList<ExtractionDeletionInstance>();

        for (File currentArchive : filesToExtract) {
            ExtractionDeletionInstance extractionDeletionInstance = extractor.extract(WORKING_DIRECTORY, currentArchive);
            Map<String, Pair<String, CsvDataFrame>> signatureSensitiveMap =
                    csvLoader.exploreJustExtractedFiles(extractionDeletionInstance);

            extractionDeletionInstances.add(extractionDeletionInstance);

            // clean minimal
            extractionDeletionInstance.clean();
        }


        for (ExtractionDeletionInstance extractionDeletionInstance : extractionDeletionInstances) {
            // revert everything extracted to file system
            try {
                extractionDeletionInstance.forceToCleanEverything();
            } catch (Exception e) {
                System.exit(1);
            }
        }
    }


    @Override
    public Context getContext() {
        return this.context.getContext();
    }
}
