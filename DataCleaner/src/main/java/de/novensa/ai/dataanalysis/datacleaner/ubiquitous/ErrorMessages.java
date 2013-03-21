package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

import java.io.File;

/**
 * Put all error messages here.
 *
 * @author Daniel Schulz
 */
public class ErrorMessages {

    // directory type strings
    public final static String WORKING_DIRECTOY = "working";
    public final static String RESULTS_DIRECTOY = "resutls";

    // PUBLIC CONSTANTS TO USE RIGHT AWAY
    public static final String NULL_VALUES_NOT_ALLOWED_HERE = "It is not senseful to work with null values at this " +
            "point. The data is needed for further prcessing and therefore have to be know.";

    private static final String ARGUMENT_DIRECTORY_NOT_FOUND_IN_ARGUMENTS = "The program argument to define the " +
            "%s directory for the application could not be found. Please start the program with an %s " +
            Constants.WORKING_DIRECTORY_OPTION_ARGUMENT_NAME + " to identify the directory to find the source files.";

    public static final String EXTRACTION_DELETION_STRATEGY_NOT_ALLOWED_IN_INSTANCE =
            "The ExtractionDeletionStrategy 'Default' is not allowed to be passed to the final instance! Please swap " +
                    "this on ExtractionDeletionInstance-creation by your implementation's default. In doubt please " +
                    "take ExtractionDeletionStrategy.DELETE_MEDIAN_EXTRACTION_LEVEL.";

    public static final String ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_COLUMN =
            "Please make sure the index you provide is existing within in the CSV file. The index for the first " +
                    "element shall be 0 (zero) and for the last on n-1 (where n is the amount of columns/features).";

    public static final String ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_ROW =
            "Please make sure the index you provide is existing within in the CSV file rows. The index for the first " +
                    "element shall be 0 (zero) and for the last on n-1 (where n is the amount of rows/items).";

    public static final String ODD_INSTANCE_STATE_IN_SUBROUTINE = "Something is not correct in the subroutine. One " +
            "or more fields clearly have wrong value(s). Please consult the documentation of the called method.";

    public static final String INITIALIZED_FIELD_MUST_STAY_UNCHANGED = "The field you're trying to change was just " +
            "initialized. These kind of information can only be set once and have to stay the same through out the " +
            "process.";

    public static final String NULL_INITIALIZATION_NOT_ALLOWED_HERE = "Please initialized this field with a value " +
            "that is more information than null. This field may be initialization-once only and therefore need a gain " +
            "of information in order to help through out the process.";

    // PRIVATE CONSTANTS TO USE VIA METHODS BENEATH
    private static final String FILENAME_INVALID = "The specified file name does not have either a valid extension or " +
            "it does not exist within this directory. Please take a look at your working directory '%s' and the inner " +
            "file: '%s'. This might fix the problem.";

    private static final String FILE_STRUCTURE_FROM_EXPLODED_ARCHIVE_CANNOT_BE_CLEANED = "The file structure '%s' " +
            "from your extracted archive cannot be cleaned. This is most likely due to a file handle on this " +
            "file or folder.";

    private static final String WORKING_DIRECTORY_CANNOT_BE_FOUND = "The specified working directory '%s' cannot be " +
            "found. Please make sure it exists or can be created.";

    private static final String DUPLICATED_FILE_IDENTIFICATION = "The file identification '%s' is not taken twice " +
            "but must be unique.";

    public static final String LIST_ITEM_HAS_TO_BE_PRESENT_HERE = "The list item one (no. zero) has to be present " +
            "at this time and cannot be null either.";

    // METHODS
    public static String getFilenameInvalid(String workingDirectory, File filename) {
        return String.format(FILENAME_INVALID, workingDirectory, filename.getName());
    }

    public static String getFileStructureFromExplodedArchiveCannotBeCleaned(File filename) {
        return String.format(FILE_STRUCTURE_FROM_EXPLODED_ARCHIVE_CANNOT_BE_CLEANED, filename);
    }

    public static String getWorkingDirectoryCannotBeFound(String workingDirectory) {
        return String.format(WORKING_DIRECTORY_CANNOT_BE_FOUND, workingDirectory);
    }

    public static String getDuplicatedFileIdentification(String fileIdentification) {
        return String.format(DUPLICATED_FILE_IDENTIFICATION, fileIdentification);
    }

    public static String getArgumentDirectoryNotFoundInArguments(final String directoryType) {
        return String.format(ARGUMENT_DIRECTORY_NOT_FOUND_IN_ARGUMENTS, directoryType);
    }
}
