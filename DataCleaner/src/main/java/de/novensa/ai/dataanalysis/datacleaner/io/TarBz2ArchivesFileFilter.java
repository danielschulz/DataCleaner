package de.novensa.ai.dataanalysis.datacleaner.io;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter out just the CSV files by ignoring case.
 *
 * @author Daniel Schulz
 */
public class TarBz2ArchivesFileFilter implements FileFilter {

    private static final String DEFAULT_FILE_ENDING = "bz2";

    // here with included dot because apache FilenameUtils cannot be used
    private static final String DEFAULT_FILENAME_ENDING_BEFORE_EXTENSION = ".tar";

    @Override
    public boolean accept(File file) {

        if (null != file) {

            String fileName = file.getName();
            String filenameWithoutLastExtension = FilenameUtils.removeExtension(fileName);
            return FilenameUtils.isExtension(fileName, DEFAULT_FILE_ENDING) && null != filenameWithoutLastExtension &&
                    filenameWithoutLastExtension.endsWith(DEFAULT_FILENAME_ENDING_BEFORE_EXTENSION);
        }

        return false;
    }
}
