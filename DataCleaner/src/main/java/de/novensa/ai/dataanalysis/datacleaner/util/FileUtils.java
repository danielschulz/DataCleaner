package de.novensa.ai.dataanalysis.datacleaner.util;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrixRow;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL;
import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.HEADER_SIGNATURES_DELIMITER;

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
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] bytes = getCharSequence(content).toString().getBytes();
            bos.write(bytes);
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

    /*
    private static <T> StringBuilder getSemanticEndRowInformation(CsvDataFrame<T> content) {
        StringBuilder res = new StringBuilder();

        res.append(content.getHealthState().ordinal()).append(Constants.HEADER_SIGNATURES_DELIMITER)
                .append(content.getPatient()).append(Constants.HEADER_SIGNATURES_DELIMITER)
                .append(content.getPatientsSex())
                // technical line break for next row / instance
                .append(Constants.LINE_BREAK);

        return res;
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
    }*/

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
