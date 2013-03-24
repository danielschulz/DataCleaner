package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrixRow;
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

        final File file = new File(resultDirectory + Constants.DOUBLE_BACK_SLASH + fileName);

        if (null != content && null != content.getData() && 1 <= content.getData().getRowSize()) {
            // contents
            byte[] bytes = getCharSequence(content).toString().getBytes();

            // file streams
            FileOutputStream fos = new FileOutputStream(file);
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            FileChannel channel = fos.getChannel();
            channel.write(byteBuffer);

            // end streams
            closeQuietly(fos);
            closeQuietly(channel);
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
                        res.append(item).append(HEADER_SIGNATURES_DELIMITER);
                    } else {
                        res.append(item);
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
