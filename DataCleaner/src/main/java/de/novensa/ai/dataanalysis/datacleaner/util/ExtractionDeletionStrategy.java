package de.novensa.ai.dataanalysis.datacleaner.util;

/**
 * What to do with the extracted files.
 *
 * @author Daniel Schulz
 */
public enum ExtractionDeletionStrategy {

    DEFAULT,                            // depends on implementation's default
    DELETE_MEDIAN_EXTRACTION_LEVEL,     // when extracting abc.tar.bz2: delete abc.tar; keep abc/ and abc.tar.bz2
    DELETE_EVERYTHING,                  // when extracting abc.tar.bz2: delete abc.tar and abc/; keep abc.tar.bz2
    KEEP_EVERYTHING;                    // keep all files: abc.tar.bz2, abc.tar and abc/
}
