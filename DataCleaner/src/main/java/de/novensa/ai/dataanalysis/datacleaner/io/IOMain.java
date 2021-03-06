package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrix;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.RuntimeInfo;
import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.CombinedFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.FractionFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.TarBz2ArchivesFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.*;
import de.novensa.ai.dataanalysis.datacleaner.util.CommandLineUtils;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionStrategy;
import org.apache.commons.cli.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages.getFileStructureFromExplodedArchiveCannotBeCleaned;

/**
 * The process starting by reading data from FS.
 *
 * @author Daniel Schulz
 */
public class IOMain <T> extends Context {

    private final File resultsDirectory;
    private final FractionFileFilter fractionFileFilter;

    private final SkyContext context;
    private static final TarBz2ArchivesFileFilter TAR_BZ_2_ARCHIVES_FILE_FILTER = new TarBz2ArchivesFileFilter();

    private final long startTime;
    private final String[] args;

    private final Iterator<String> workingStates = Constants.WORKING_STATES;
    private int currentWorkingState = 1;
    private final int workingStateCount = WORKING_STATE_ITEM_LIST.length;


    public IOMain(Triplet<String, String, FractionFileFilter> directories, final long startTime, final String[] args) {
        if (null == directories) {
            throw new IllegalArgumentException(ErrorMessages.NULL_INITIALIZATION_NOT_ALLOWED_HERE);
        }

        this.startTime = startTime;
        this.args = args;

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
        this.fractionFileFilter = directories.getValue2();
    }


    public static void main(String[] args) throws IOException, ParseException {
        final long startTime = System.currentTimeMillis();
        IOMain ioMain = new IOMain(CommandLineUtils.parseCommandLine(args), startTime, args);
        ioMain.processWorkingDirectory();
    }

    public void processWorkingDirectory() throws IOException {

        ExtractArchives extractor = new ExtractArchives(this.getContext());
        LoadCsvContents<T> csvLoader = new LoadCsvContents<T>(this.getContext());

        // identify upcoming work
        final File wd = new File(getContext().getWorkingDir());
        // if there is no FractionFileFilter than just take TAR_BZ2-FileFilter 'natively'; else take 'em both in
        // the container file filter class
        final FileFilter fileFilter = null == this.fractionFileFilter ?
                TAR_BZ_2_ARCHIVES_FILE_FILTER :
                new CombinedFileFilter(TAR_BZ_2_ARCHIVES_FILE_FILTER, this.fractionFileFilter);
        final File[] filesToExtract = wd.listFiles(fileFilter);

        workingState();

        // process extraction
        List<ExtractionDeletionInstance> extractionDeletionInstances = new ArrayList<ExtractionDeletionInstance>();

        for (File currentArchive : filesToExtract) {
            ExtractionDeletionInstance extractionDeletionInstance = extractor.extract(
                    getContext().getWorkingDir(), currentArchive);
            extractionDeletionInstances.add(extractionDeletionInstance);

            // clean minimal
            extractionDeletionInstance.clean();
        }

        workingState();

        Map<String, HeaderSignatureSensitiveBucket<T>> signatureSensitiveMap =
                csvLoader.exploreJustExtractedFiles(extractionDeletionInstances);

        workingState();

        Map<String, CsvDataFrame<T>> processedMap = new TreeMap<String, CsvDataFrame<T>>();

        for (String key : signatureSensitiveMap.keySet()) {
            CsvDataFrame<T> csvDataFrames = compressCsvDataFrames(signatureSensitiveMap.get(key));
            processedMap.put(key, csvDataFrames);
        }

        workingState();

        // make result directories
        cleanUpDestinationDirectory(this.resultsDirectory);
        //noinspection ResultOfMethodCallIgnored
        final boolean resultsDirJustCreated = this.resultsDirectory.mkdirs();

        final List<File> folders = resultsDirJustCreated ?
                new ArrayList<File>(Arrays.asList(this.resultsDirectory)) : null;

        final long endTime = System.currentTimeMillis();
        final RuntimeInfo<T> runtimeInfo = new RuntimeInfo<T>(this.startTime, endTime, processedMap, this.args);

        final List<File> writtenFiles = FileUtils.writeFiles(this.resultsDirectory, runtimeInfo);

        workingState();


        // clean up again
        @SuppressWarnings("UnusedDeclaration")
        ExtractionDeletionInstance deletionInstance = new ExtractionDeletionInstance(
                ExtractionDeletionStrategy.KEEP_EVERYTHING, this.resultsDirectory.getCanonicalPath(),
                folders, writtenFiles, null, null);


        for (ExtractionDeletionInstance extractionDeletionInstance : extractionDeletionInstances) {

            // revert everything extracted to file system
            try {
                extractionDeletionInstance.forceToCleanEverything();
            } catch (Exception e) {
                throw new IllegalStateException(
                        getFileStructureFromExplodedArchiveCannotBeCleaned(extractionDeletionInstance.getFileFinal()));
            }
        }

        workingState();
    }

    private void cleanUpDestinationDirectory(final File resultsDirectory) {
        if (null != resultsDirectory && resultsDirectory.exists()) {
            File[] contents = resultsDirectory.listFiles();

            if (null != contents) {
                for (File file : contents) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
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


    private static String getCanonicalForm(final String dir) {

        try {
            return (new File(dir)).getCanonicalPath() + DOUBLE_BACK_SLASH;
        } catch (IOException ioe) {
            return null;
        }
    }

    public void workingState() {
        final String message = this.workingStates.next();
        System.out.println(String.format(Constants.WORKING_STATE_TEMPLATE,
                currentWorkingState++, workingStateCount, message, System.currentTimeMillis()));
    }


    @Override
    public SkyContext getContext() {
        return this.context.getContext();
    }
}
