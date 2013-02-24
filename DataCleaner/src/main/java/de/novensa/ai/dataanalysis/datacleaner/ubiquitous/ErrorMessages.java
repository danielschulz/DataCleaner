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
}
