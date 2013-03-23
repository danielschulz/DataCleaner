package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.CsvFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.FractionFileFilter;

/**
 * Put any constants here.
 *
 * @author Daniel Schulz
 */
public interface Constants {

    // semantic, work place constants
    public final static String WORKING_DIRECTORY = "~/Documents/";      // the folder all your data to read and write are in
    public final static String RESULTS_DIRECTORY = "results";           // the folder all your data to read and write are in


    // semi-semantic, semi-technical constants
    public static final short ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL = (short) 6;
    public static final FractionFileFilter FRACTION_FILE_FILTER_TAKES_ANY = new FractionFileFilter(1);

    // technical constants
    public static final int BUFFER_SIZE = 2048;     // size of buffer to load and write files with
    // delimiter character(s) used internally to aggregate the signature id
    public static final String HEADER_SIGNATURES_DELIMITER = ";";

    // command line interface
    String WORKING_DIRECTORY_OPTION_DESCRIPTION = "This option specifies the folder all your data to read from " +
            "and to write in. This is the directory everything happens on file system basis. If this value isn´t " +
            "specified Java´s 'Constants.WORKING_DIRECTORY' is going to be taken. The " +
            "program arguments may be passed like <code>-workingDirectory \"~/Documents/\"</code> to the " +
            "IOMain.java -- to me this Stackoverflow-Thread was very helpful: " +
            "http://stackoverflow.com/questions/5585634/apache-commons-cli-option-type-and-default-value";
    String WORKING_DIRECTORY_OPTION_ARGUMENT_NAME = "workingDirectory";
    String WORKING_DIRECTORY_LONG_OPT_VALUE = WORKING_DIRECTORY_OPTION_ARGUMENT_NAME;

    String RESULTS_DIRECTORY_OPTION_DESCRIPTION = "This option specifies the folder all your CSV result files will " +
            "be written to. This is meant to be an existing folder within the workingDirectory. If it does not exist " +
            "it will be created there. If it is not present the Constants.RESULTS_DIRECTORY from Java will be taken. " +
            "The program arguments may be passed like <code>-resultsDirectory \"results\"</code>.";
    String RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME = "resultsDirectory";
    String RESULTS_DIRECTORY_LONG_OPT_VALUE = RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME;

    String FRACTION_FILES_TO_TAKE_OPTION_DESCRIPTION = "This option specifies the fraction of file to choose " +
            "and process from the working directory. Choose 1.0 to take everything and zeo to leave out all " +
            "files. The FractionFileFilter therefore will only be applied for any value in (0...1] -- mathematically.";
    String FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME = "fractionFilesToTake";
    String FRACTION_FILES_TO_TAKE_LONG_OPT_VALUE = FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME;

    String DOUBLE_BACK_SLASH = "\\";
    String LINE_BREAK = System.getProperty("line.separator");

    String RESULTS_FILE_NAME_PATTERN = "result_%s." + CsvFileFilter.DEFAULT_FILE_ENDING;
    String COMMAND_LINE_WAS_NOT_SUPPLIED_A_VALID_RATIO_VALUE = "The provided value for ratio for files to take is " +
            "not a valid ratio. Please provide a value greater than zero and at most one: (0...1] .";
}
