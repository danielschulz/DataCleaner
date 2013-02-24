package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

/**
 * Put all error messages here.
 *
 * @author Daniel Schulz
 */
public class ErrorMessages {

    public final static String EXTRACTION_DELETION_STRATEGY_NOT_ALLOWED_IN_INSTANCE =
            "The ExtractionDeletionStrategy 'Default' is not allowed to be passed to the final instance! Please swap " +
                    "this on ExtractionDeletionInstance-creation by your implementation's default. In doubt please " +
                    "take EtractionDeletionStrategy.DELETE_MEDIAN_EXTRACTION_LEVEL.";

    public final static String ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_COLUMN =
            "Please make sure the index you provide is existing within in the CSV file. The index for the first " +
                    "element shall be 0 (zero) and for the last on n-1 (where n is the amount of columns/features).";

    public final static String ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_ROW =
            "Please make sure the index you provide is existing within in the CSV file rows. The index for the first " +
                    "element shall be 0 (zero) and for the last on n-1 (where n is the amount of rows/items).";
}
