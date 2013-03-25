package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

import com.google.common.collect.UnmodifiableIterator;
import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.CsvFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.io.fileFilter.FractionFileFilter;

import java.util.Arrays;
import java.util.Iterator;

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
    public static final int BUFFER_SIZE = 4*1024*1024;     // size of buffer to load and write files with
    public static final int RUNTIME_INFO_FILE_INIT_SIZE = 1024;

    // delimiter character(s) used internally to aggregate the signature id
    public static final String HEADER_SIGNATURES_DELIMITER = ";";

    // command line interface
    final String WORKING_DIRECTORY_OPTION_DESCRIPTION = "This option specifies the folder all your data to read from " +
            "and to write in. This is the directory everything happens on file system basis. If this value isn´t " +
            "specified Java´s 'Constants.WORKING_DIRECTORY' is going to be taken. The " +
            "program arguments may be passed like <code>-workingDirectory \"~/Documents/\"</code> to the " +
            "IOMain.java -- to me this Stackoverflow-Thread was very helpful: " +
            "http://stackoverflow.com/questions/5585634/apache-commons-cli-option-type-and-default-value";
    final String WORKING_DIRECTORY_OPTION_ARGUMENT_NAME = "workingDirectory";
    final String WORKING_DIRECTORY_LONG_OPT_VALUE = WORKING_DIRECTORY_OPTION_ARGUMENT_NAME;

    final String RESULTS_DIRECTORY_OPTION_DESCRIPTION = "This option specifies the folder all your CSV result files will " +
            "be written to. This is meant to be an existing folder within the workingDirectory. If it does not exist " +
            "it will be created there. If it is not present the Constants.RESULTS_DIRECTORY from Java will be taken. " +
            "The program arguments may be passed like <code>-resultsDirectory \"results\"</code>.";
    final String RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME = "resultsDirectory";
    final String RESULTS_DIRECTORY_LONG_OPT_VALUE = RESULTS_DIRECTORY_OPTION_ARGUMENT_NAME;

    final String FRACTION_FILES_TO_TAKE_OPTION_DESCRIPTION = "This option specifies the fraction of file to choose " +
            "and process from the working directory. Choose 1.0 to take everything and zeo to leave out all " +
            "files. The FractionFileFilter therefore will only be applied for any value in (0...1) -- mathematically " +
            "except the borders zero and one.";
    final String FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME = "fractionFilesToTake";
    final String FRACTION_FILES_TO_TAKE_LONG_OPT_VALUE = FRACTION_FILES_TO_TAKE_OPTION_ARGUMENT_NAME;

    final String FILE_SEPARATOR = System.getProperty("file.separator");
    final String DOUBLE_BACK_SLASH = FILE_SEPARATOR + FILE_SEPARATOR;
    final String LINE_BREAK = System.getProperty("line.separator");

    final String RESULTS_FILE_NAME_PATTERN = "result_%s." + CsvFileFilter.DEFAULT_FILE_ENDING;
    final String UNKNOWN_VALUE_IN_RESULT = "NA";

    @SuppressWarnings("UnusedDeclaration")
    final String ERROR_FILE_NAME = "errors.txt";
    final String RUNTIME_INFO_FILE_NAME = "runtimeInfo.txt";
    public static final String RUNTIME_INFO_RESULT_TEXT =
            "process started, process ended, time difference" + LINE_BREAK + "%s, %s, %s";

    final String[] WORKING_STATE_ITEM_LIST = new String[]{
            "allocated files", "archives extracted", "read raw exploded files and mapped them",
            "packed exploded files into data frames", "results written", "cleaned up working directory"};
    final Iterator<String> WORKING_STATES = Arrays.asList(WORKING_STATE_ITEM_LIST).iterator();
    final String WORKING_STATE_TEMPLATE = "%s of %s done -- %s \t\t\t\t\t (@ %s ms to epoch)";
}
