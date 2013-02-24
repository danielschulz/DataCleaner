package de.novensa.ai.dataanalysis.datacleaner.io;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter out just the CSV files by ignoring case.
 *
 * @author Daniel Schulz
 */
public class CsvFileFilter implements FileFilter {

    private static final String DEFAULT_FILE_ENDING = "csv";

    @Override
    public boolean accept(File file) {

        if (null != file) {

            String fileName = file.getName();
            return FilenameUtils.isExtension(fileName, DEFAULT_FILE_ENDING);
        }

        return false;
    }
}
