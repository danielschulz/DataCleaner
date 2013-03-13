package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

/**
 * Put any constants here.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public interface Constants {

    // semantic, work place constants
    public final static String WORKING_DIRECTORY = "~/Documents/";      // the folder all your data to read and write are in
    public final static String ARCHIVE_FILE_IN_WORKING_DIRECTORY = "archive.tar.bz2";   // the first archive to extract information from


    // technical constants
    public static final int BUFFER_SIZE = 2048;     // size of buffer to load and write files with
    public static final String HEADER_SIGNATURES_DELIMITER = ";";  // delimiter character(s) used internally to aggregate the signature id
    String WORKING_DIRECTORY_OPTION_DESCRIPTION = "This option specifies the folder all your data to read from " +
            "and to write in. This is the directory everything happens on file system basis. If this value isn´t " +
            "specified Java´s 'Constants.WORKING_DIRECTORY' or 'PrivateConstants.WORKING.DIRECTORY' is taken. The " +
            "program arguments may be passed like <code>-workingDirectory \"~/Documents/\"</code> to the " +
            "IOMain.java -- to me this Stackoverflow-Thread was very helpful: " +
            "http://stackoverflow.com/questions/5585634/apache-commons-cli-option-type-and-default-value";
    String WORKING_DIRECTORY_OPTION_ARGUMENT_NAME = "workingDirectory";
    String WORKING_DIRECTORY_LONG_OPT_VALUE = WORKING_DIRECTORY_OPTION_ARGUMENT_NAME;
}
