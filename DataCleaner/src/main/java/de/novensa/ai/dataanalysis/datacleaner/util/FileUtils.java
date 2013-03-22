package de.novensa.ai.dataanalysis.datacleaner.util;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrix;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrixRow;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Helps handling of files and file streams.
 *
 * @author Daniel Schulz
 */
public class FileUtils {


    public static List<File> writeFiles(final File resultsDirectory, final Map<String, CsvDataFrame> map)
            throws IOException {
        List<File> writtenFiles = new ArrayList<File>();

        File f;
        int i = 1;
        for (String key : map.keySet()) {
            f = writeFile(resultsDirectory, getUpcomingResultsFileName(i++), map.get(key));
            writtenFiles.add(f);
        }

        return writtenFiles;
    }


    public static File writeFile(final File resultDirectory, final String fileName, final CsvDataFrame content) throws IOException {

        final File file = new File(resultDirectory + Constants.DOUBLE_BACK_SLASH + fileName);
        //final int numberOfIterations = 1000000;
        final int numberOfIterations = 1;

        if (null != content && null != content.getData() && 1 <= content.getData().getRowSize()) {
            @SuppressWarnings("unchecked")
            final byte[] messageBytes = getDataFromCsvDataFrame(content).getBytes(Charset.forName("ISO-8859-1"));
            final long appendSize = numberOfIterations * messageBytes.length;
            final RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(raf.length());
            final FileChannel fc = raf.getChannel();
            final MappedByteBuffer mbf = fc.map(FileChannel.MapMode.READ_WRITE, fc.position(), getAtMostIntegerMax(appendSize));
            fc.close();
            for (int i = 1; i < numberOfIterations; i++) {
                mbf.put(messageBytes);
            }
            return file;
        } else {
            return null;
        }
    }

    private static long getAtMostIntegerMax(long appendSize) {
        return (long) Math.min(appendSize, Integer.MAX_VALUE);
    }

    private static <T> String getDataFromCsvDataFrame(CsvDataFrame<T> content) {
        return getDataFromCsvDataFrame(content.getData());
    }

    private static <T> String getDataFromCsvDataFrame(CsvMatrix<T> data) {
        StringBuilder res = new StringBuilder();

        int cellCount = 0;
        final int breakCount = data.getRowSize();

        for (CsvMatrixRow<T> row : data.getRows()) {
            for (T cell : row.getCells()) {
                res.append(cell);

                if (0 != cellCount++ % breakCount) {
                    res.append(Constants.HEADER_SIGNATURES_DELIMITER);
                } else {
                    cellCount = 0;
                }
            }
            res.append(Constants.LINE_BREAK);
        }

        return res.toString();
    }

    public static String getFileNameWithUnixPrefix(String prefix, String fileName) {

        if (null != prefix) {

            // pre precess prefix to "./folder/" -> "folder/"
            if (prefix.startsWith("./")) {
                prefix = getFileNameWithUnixPrefix("", prefix);
            }

            // process file path ending
            if (0 < prefix.length() && fileName.startsWith(prefix)) {
                return fileName;

            } else if (fileName.startsWith("./" + prefix)) {
                return fileName.substring(2);

            } else {
                if (0 < prefix.length() && 0 < fileName.length()) {
                    return prefix + "/" + fileName;

                } else if (0 == prefix.length() && 0 < fileName.length()) {
                    return fileName;

                } else if (0 < prefix.length() && 0 == fileName.length()) {
                    return prefix.endsWith("/") ? prefix : prefix + "/";
                }
            }
        }

        return fileName;

    }


    private static String getUpcomingResultsFileName(int count) {
        return String.format(Constants.RESULTS_FILE_NAME_PATTERN, count);
    }
}
