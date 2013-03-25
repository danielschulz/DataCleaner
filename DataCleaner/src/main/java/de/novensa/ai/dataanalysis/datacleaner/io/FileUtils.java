package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrixRow;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.RuntimeInfo;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings.Mappings;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL;
import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.HEADER_SIGNATURES_DELIMITER;
import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.LINE_BREAK;
import static org.apache.commons.io.IOUtils.closeQuietly;

/**
 * Helps handling of files and file streams.
 *
 * @author Daniel Schulz
 */
public class FileUtils {

    private static final int START_COUNT_WRITE_CSV_CELLS = 0;

    public static <T> List<File> writeFiles(final File resultsDirectory, final RuntimeInfo<T> runtimeInfo)
            throws IOException {

        List<File> files = null != runtimeInfo && null != runtimeInfo.getProcessedMap() ?
                writeFiles(resultsDirectory, runtimeInfo.getProcessedMap()) : new ArrayList<File>(1);

        files.add(writeFile(resultsDirectory, Constants.RUNTIME_INFO_FILE_NAME, getRuntimeInfo(runtimeInfo)));

        return files;
    }

    private static <T> CharSequence getRuntimeInfo(RuntimeInfo<T> runtimeInfo) {
        StringBuilder res = new StringBuilder(Constants.RUNTIME_INFO_FILE_INIT_SIZE);
        if (null != runtimeInfo) {

            return runtimeInfo.getCommandLineArgs() + LINE_BREAK + LINE_BREAK +
                    String.format(Constants.RUNTIME_INFO_RESULT_TEXT,
                    runtimeInfo.getStartTime(), runtimeInfo.getEndTime(), runtimeInfo.getDiffTimes());
        }

        return res;
    }

    public static <T> List<File> writeFiles(final File resultsDirectory, final Map<String, CsvDataFrame<T>> map)
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


    public static <T> File writeFile(final File resultDirectory, final String fileName, final CsvDataFrame<T> content)
            throws IOException {


        if (null != content && null != content.getData() && 1 <= content.getData().getRowSize()) {
            // contents
            byte[] bytes = getCharSequence(content).toString().getBytes();
            return writeFile(resultDirectory, fileName, bytes);
        }

        return null;
    }


    @SuppressWarnings("UnusedDeclaration")
    public static File writeFile(final File resultDirectory, final String fileName, final CharSequence content)
            throws IOException {

        return null != content ? writeFile(resultDirectory, fileName, content.toString().getBytes()) : null;
    }


    public static File writeFile(final File resultDirectory, final String fileName, final byte[] content)
            throws IOException {

        if (null != content) {

            // file streams
            final File file = new File(resultDirectory + Constants.DOUBLE_BACK_SLASH + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ByteBuffer byteBuffer = ByteBuffer.wrap(content);
            FileChannel channel = fos.getChannel();
            channel.write(byteBuffer);

            // end streams
            closeQuietly(fos);
            closeQuietly(channel);

            return file;
        }

        return null;
    }


    private static <T> CharSequence getCharSequence(CsvDataFrame<T> content) {

        StringBuilder res = null;
        if (null != content &&
                null != content.getData() && 1 <= content.getData().getRowSize() &&
                null != content.getData().getRow(0) && 1 <= content.getData().getRow(0).getColumnSize()) {
            // init phase
            final int columnCount = content.getData().getRow(0).getColumnSize();
            final int cellBeforeLineBreak = columnCount - 1;
            int estCharsForDataCells =
                    content.getData().getRowSize() * columnCount * ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL;
            int estCharsForHeaderRowItems = content.getHeaderSignature().length();

            // correct for zero-to-positive values only
            estCharsForDataCells = 0 <= estCharsForDataCells ?
                    estCharsForDataCells : ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL * estCharsForHeaderRowItems;

            res = new StringBuilder(estCharsForDataCells + estCharsForHeaderRowItems);


            // write header
            for (String headerItem : content.getHeader()) {
                res.append(headerItem).append(HEADER_SIGNATURES_DELIMITER);
            }
            res.append(Mappings.HEADER_ENDING).append(LINE_BREAK);


            // init phase
            int i = START_COUNT_WRITE_CSV_CELLS;

            // get contents
            for (CsvMatrixRow<T> row : content.getData().getRows()) {

                for (T item : row.getCells()) {
                    // 0..(n-1)-th cell and n-th cell as well
                    if (cellBeforeLineBreak != i) {
                        res.append(getItem(item)).append(HEADER_SIGNATURES_DELIMITER);
                    } else {
                        res.append(getItem(item));
                    }

                    i++;

                    if (columnCount == i) {
                        // last line
                        i = START_COUNT_WRITE_CSV_CELLS;
                        res.append(Constants.LINE_BREAK);
                    }
                }
            }
        }

        return res;
    }

    private static <T> CharSequence getItem(T item) {
        return null != item ? item.toString() : Constants.UNKNOWN_VALUE_IN_RESULT;
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
