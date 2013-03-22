package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrix;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.*;
import de.novensa.ai.dataanalysis.datacleaner.util.CommandLineUtils;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionStrategy;
import de.novensa.ai.dataanalysis.datacleaner.util.FileUtils;
import org.apache.commons.cli.*;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The process starting by reading data from FS.
 *
 * @author Daniel Schulz
 */
public class IOMain extends Context {

    private final File resultsDirectory;

    private final SkyContext context;
    private static final TarBz2ArchivesFileFilter TAR_BZ_2_ARCHIVES_FILE_FILTER = new TarBz2ArchivesFileFilter();

    public IOMain(Pair<String, String> directories) {
        if (null == directories) {
            throw new IllegalArgumentException(ErrorMessages.NULL_INITIALIZATION_NOT_ALLOWED_HERE);
        }

        final String workingDir = directories.getValue0();
        final String resultsDir = directories.getValue1();

        if (null == workingDir) {
            throw new IllegalStateException(
                    ErrorMessages.getArgumentDirectoryNotFoundInArguments(ErrorMessages.WORKING_DIRECTOY));
        }

        if (null == resultsDir) {
            throw new IllegalStateException(
                    ErrorMessages.getArgumentDirectoryNotFoundInArguments(ErrorMessages.RESULTS_DIRECTOY));
        }

        this.context = new SkyContext(workingDir);
        this.resultsDirectory = new File(getCanonicalForm(workingDir) + resultsDir);
    }

    private static String getCanonicalForm(final String dir) {

        try {
            return (new File(dir)).getCanonicalPath() + DOUBLE_BACK_SLASH;
        } catch (IOException ioe) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        IOMain ioMain = new IOMain(CommandLineUtils.parseCommandLine(args));
        ioMain.extractArchives();
    }

    public void extractArchives() throws IOException {
        ExtractArchives extractor = new ExtractArchives(this.getContext());
        LoadCsvContents csvLoader = new LoadCsvContents(this.getContext());

        final File wd = new File(getContext().getWorkingDir());
        final File[] filesToExtract = wd.listFiles(TAR_BZ_2_ARCHIVES_FILE_FILTER);
        List<ExtractionDeletionInstance> extractionDeletionInstances = new ArrayList<ExtractionDeletionInstance>();

        for (File currentArchive : filesToExtract) {
            ExtractionDeletionInstance extractionDeletionInstance = extractor.extract(
                    getContext().getWorkingDir(), currentArchive);
            extractionDeletionInstances.add(extractionDeletionInstance);

            // clean minimal
            extractionDeletionInstance.clean();
        }


        @SuppressWarnings("UnusedDeclaration")
        Map<String, HeaderSignatureSensitiveBucket> signatureSensitiveMap =
                csvLoader.exploreJustExtractedFiles(extractionDeletionInstances);
        Map<String, CsvDataFrame> processedMap = new TreeMap<String, CsvDataFrame>();

        for (String key : signatureSensitiveMap.keySet()) {
            CsvDataFrame csvDataFrames = compressCsvDataFrames(signatureSensitiveMap.get(key));
            processedMap.put(key, csvDataFrames);
        }


        // make result directories
        //noinspection ResultOfMethodCallIgnored
        final boolean resultsDirJustCreated = this.resultsDirectory.mkdirs();

        final List<File> folders = resultsDirJustCreated ?
                new ArrayList<File>(Arrays.asList(this.resultsDirectory)) : null;

        final List<File> writtenFiles = FileUtils.writeFiles(this.resultsDirectory, processedMap);


        @SuppressWarnings("UnusedDeclaration") ExtractionDeletionInstance deletionInstance = new ExtractionDeletionInstance(
                ExtractionDeletionStrategy.KEEP_EVERYTHING, this.resultsDirectory.getCanonicalPath(),
                folders, writtenFiles, null, null);


        /*
        for (String cur : signatureSensitiveMap.keySet()) {


            FileWriter fileWriter = new FileWriter(new File(cur));
            CSVWriterBuilder csvWriterBuilder = new CSVWriterBuilder(fileWriter);

            CSVWriter writer = csvWriterBuilder.build();

            writer.write(pair.get);
        }*/


        for (ExtractionDeletionInstance extractionDeletionInstance : extractionDeletionInstances) {

            // revert everything extracted to file system
            try {
                extractionDeletionInstance.forceToCleanEverything();
            } catch (Exception e) {
                throw new IllegalStateException(ErrorMessages.getFileStructureFromExplodedArchiveCannotBeCleaned(
                        extractionDeletionInstance.getFileFinal()));
            }
        }
    }

    @SuppressWarnings({"unchecked", "UnnecessaryLocalVariable"})
    private static <T> CsvDataFrame<T> compressCsvDataFrames(final HeaderSignatureSensitiveBucket bucket) {

        if (null == bucket) {
            throw new IllegalArgumentException(ErrorMessages.NULL_INITIALIZATION_NOT_ALLOWED_HERE);
        }

        final List<Pair<String, CsvDataFrame>> allBucketPairs = bucket.getPairs();
        final List<Pair<String, CsvDataFrame>> buckets = allBucketPairs;
        final List<List<T>> dataCollection;
        dataCollection = new ArrayList<List<T>>(new ArrayList(buckets.size()));

        Pair<String, CsvDataFrame> firstBucketPair = allBucketPairs.get(0);
        if (allBucketPairs.size() < 1 || null == firstBucketPair) {
            throw new IllegalStateException(ErrorMessages.LIST_ITEM_HAS_TO_BE_PRESENT_HERE);
        }

        for (Pair<String, CsvDataFrame> c : buckets) {
            dataCollection.addAll(c.getValue1().getData().getRows());
        }

        return new CsvDataFrame(firstBucketPair.getValue1().getHeader(), new CsvMatrix(dataCollection));
    }


    @Override
    public SkyContext getContext() {
        return this.context.getContext();
    }
}
