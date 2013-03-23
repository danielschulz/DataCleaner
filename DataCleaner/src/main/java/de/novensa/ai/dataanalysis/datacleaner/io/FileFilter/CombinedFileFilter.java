package de.novensa.ai.dataanalysis.datacleaner.io.fileFilter;

import java.io.File;
import java.io.FileFilter;

/**
 * A combined file filter provides the possibility to filter out all files but the intersect of all file filters
 * given. That way the second and all following file filters do not have to be hand coded or iterated over.
 *
 * @author Daniel Schulz
 */
public class CombinedFileFilter implements FileFilter {

    private final FileFilter[] fileFilters;

    public CombinedFileFilter(FileFilter... fileFilters) {
        this.fileFilters = fileFilters;
    }


    @Override
    public boolean accept(File pathName) {
        boolean res = true;

        // no early break needed -- to small count of file filters expected so overhead would most possibly rule
        // the negative performance impact
        for (FileFilter filter : this.fileFilters) {
            res &= filter.accept(pathName);
        }

        return res;
    }
}
