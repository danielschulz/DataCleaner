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
}
