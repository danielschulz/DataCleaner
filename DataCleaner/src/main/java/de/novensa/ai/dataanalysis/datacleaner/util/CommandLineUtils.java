package de.novensa.ai.dataanalysis.datacleaner.util;

import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.FractionFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;
import org.apache.commons.cli.*;
import org.javatuples.Triplet;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.*;

/**
 * Does the processing of command line arguments.
 *
 * @author Daniel Schulz
 */
public class CommandLineUtils {
    public static Triplet<String, String, FractionFileFilter> parseCommandLine(String[] args) throws ParseException {

        // init parsing options
        CommandLineParser commandLineParser = new BasicParser();
        Options options = new Options();

        //noinspection AccessStaticViaInstance
        options.addOption(OptionBuilder.withLongOpt(WORKING_DIRECTORY_LONG_OPT_VALUE)
                .withDescription(WORKING_DIRECTORY_OPTION_DESCRIPTION)
                .withType(String.class)
                .hasArg()
                .withArgName(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME)
                .create());

        //noinspection AccessStaticViaInstance
        options.addOption(OptionBuilder.withLongOpt(RESULTS_DIRECTORY_LONG_OPT_VALUE)
                .withDescription(RESULTS_DIRECTORY_OPTION_DESCRIPTION)
                .withType(String.class)
                .hasArg()
                .withArgName(RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME)
                .create());

        //noinspection AccessStaticViaInstance
        options.addOption(OptionBuilder.withLongOpt(FRACTION_FILES_TO_TAKE_LONG_OPT_VALUE)
                .withDescription(FRACTION_FILES_TO_TAKE_OPTION_DESCRIPTION)
                .withType(double.class)
                .hasArg()
                .withArgName(FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME)
                .create());

        // parse options
        CommandLine commandLine = commandLineParser.parse(options, args);

        final String workingDirectory =
                commandLine.hasOption(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME) ?
                        commandLine.getOptionValue(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME) :
                        Constants.WORKING_DIRECTORY;

        final String resultsDirectory =
                commandLine.hasOption(RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME) ?
                        commandLine.getOptionValue(RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME) :
                        Constants.RESULTS_DIRECTORY;

        try {
            //noinspection ConstantConditions
            final double ratioFilesToVanish =
                    commandLine.hasOption(FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME) ?
                            Double.parseDouble(
                                    commandLine.getOptionValue(FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME)) :
                            null;

            FractionFileFilter fractionFileFilter;
            if (0 < ratioFilesToVanish && 1 >= ratioFilesToVanish) {
                fractionFileFilter = new FractionFileFilter(1.0 - ratioFilesToVanish);
            } else {
                throw new IllegalArgumentException(Constants.COMMAND_LINE_WAS_NOT_SUPPLIED_A_VALID_RATIO_VALUE);
            }

            return new Triplet<String, String, FractionFileFilter>
                    (workingDirectory, resultsDirectory, fractionFileFilter);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(COMMAND_LINE_WAS_NOT_SUPPLIED_A_VALID_RATIO_VALUE);
        }
    }
}
