package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.*;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;
import org.apache.commons.cli.*;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.*;

/**
 * The process starting by reading data from FS.
 *
 * @author Daniel Schulz
 */
public class IOMain extends Context {

    private final SkyContext context;
    private static final TarBz2ArchivesFileFilter TAR_BZ_2_ARCHIVES_FILE_FILTER = new TarBz2ArchivesFileFilter();

    public IOMain(String workingDirectory) {
        this.context = new SkyContext(workingDirectory);
    }

    public static void main(String[] args) throws IOException, ParseException {
        IOMain ioMain = new IOMain(parseCommandLine(args));
        ioMain.extractArchives();
    }

    private static String parseCommandLine(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new BasicParser();
        Options options = new Options();
        //noinspection AccessStaticViaInstance
        options.addOption(OptionBuilder.withLongOpt(WORKING_DIRECTORY_LONG_OPT_VALUE)
                .withDescription(WORKING_DIRECTORY_OPTION_DESCRIPTION)
                .withType(String.class)
                .hasArg()
                .withArgName(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME)
                .create());
        CommandLine commandLine = commandLineParser.parse(options, args);

        return commandLine.hasOption(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME) ?
                commandLine.getOptionValue(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME) :
                Constants.WORKING_DIRECTORY;
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
        Map<String, Pair<String, CsvDataFrame>> signatureSensitiveMap =
                csvLoader.exploreJustExtractedFiles(extractionDeletionInstances);


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


    @Override
    public SkyContext getContext() {
        return this.context.getContext();
    }
}
