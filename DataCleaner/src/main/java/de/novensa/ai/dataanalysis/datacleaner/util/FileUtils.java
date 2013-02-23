package de.novensa.ai.dataanalysis.datacleaner.util;

/**
 * Helps handling of files and file streams.
 *
 * @author Daniel Schulz
 */
public class FileUtils {

    public static String getFileNameWithUnixPrefix(String prefix, String fileName) {

        if (null != prefix) {

            if (prefix.startsWith("./")) {
                prefix = getFileNameWithUnixPrefix("", prefix);
            }

            if (0 < prefix.length() && fileName.startsWith(prefix)) {
                return fileName;

            } else if (fileName.startsWith("./" + prefix)) {
                return fileName.substring(2);

            } else {
                if (0 < prefix.length() && 0 < fileName.length()) {
                    return prefix + "/" + fileName;
                } else {
                    return "";
                }
            }
        } else {
            return fileName;
        }
    }
}
