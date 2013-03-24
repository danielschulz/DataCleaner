package de.novensa.ai.dataanalysis.datacleaner.util;

import de.novensa.ai.dataanalysis.datacleaner.io.FileUtils;
import junit.framework.TestCase;

public class FileUtilsTest extends TestCase {

    public static void test() {

        assertEquals("",
                FileUtils.getFileNameWithUnixPrefix("", ""));
        assertEquals("abc.xyz",
                FileUtils.getFileNameWithUnixPrefix("", "abc.xyz"));
        assertEquals("abc/",
                FileUtils.getFileNameWithUnixPrefix("abc", ""));
        assertEquals("abc/",
                FileUtils.getFileNameWithUnixPrefix("abc/", ""));

        assertEquals("folder/abc.tar",
                FileUtils.getFileNameWithUnixPrefix("folder", "abc.tar"));
        assertEquals("folder/abc.tar.bz2",
                FileUtils.getFileNameWithUnixPrefix("folder", "abc.tar.bz2"));

        assertEquals("folder/abc.tar.bz2",
                FileUtils.getFileNameWithUnixPrefix("./folder", "abc.tar.bz2"));
        assertEquals("folder.subfolder/abc.tar.bz2",
                FileUtils.getFileNameWithUnixPrefix("folder.subfolder", "abc.tar.bz2"));

        assertEquals("abc.tar.bz2",
                FileUtils.getFileNameWithUnixPrefix(null, "abc.tar.bz2"));
    }
}
