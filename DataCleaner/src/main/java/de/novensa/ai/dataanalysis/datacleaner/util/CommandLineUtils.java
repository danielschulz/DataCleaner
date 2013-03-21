package de.novensa.ai.dataanalysis.datacleaner.util;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;
import org.apache.commons.cli.*;
import org.javatuples.Pair;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.*;

/**
 * Does the processing of command line arguments.
 *
 * @author Daniel Schulz
 */
public class CommandLineUtils {
    public static Pair<String, String> parseCommandLine(String[] args) throws ParseException {
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

        CommandLine commandLine = commandLineParser.parse(options, args);

        final String workingDirectory =
                commandLine.hasOption(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME) ?
                        commandLine.getOptionValue(WORKING_DIRECTORY_OPTION_ARGUMENT_NAME) :
                        Constants.WORKING_DIRECTORY;

        final String resultsDirectory =
                commandLine.hasOption(RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME) ?
                        commandLine.getOptionValue(RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME) :
                        Constants.RESULTS_DIRECTORY;

        return new Pair<String, String>(workingDirectory, resultsDirectory);
    }
}
