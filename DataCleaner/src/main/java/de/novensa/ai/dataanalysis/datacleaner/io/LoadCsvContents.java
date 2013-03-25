package de.novensa.ai.dataanalysis.datacleaner.io;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrixRow;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Context;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Get the contents of all CSV files.
 *
 * @author Daniel Schulz
 * @author Juergen Krey
 */
public class LoadCsvContents<T> extends Context {

    private final CsvMatrixRowParser<T> DEFAULT_CSV_ENTRY_PARSER = new CsvMatrixRowParser<T>();
    private final Context context;

    public LoadCsvContents(Context context) {
        this.context = context;
    }

    /**
     * Read files from your ExtractionDeletionInstance. The result will map the file's location to it's content.
     * @param extractionDeletionInstances The source to find the files
     * @return A Map from the file location to it's content
     */
    public Map<String, HeaderSignatureSensitiveBucket<T>> exploreJustExtractedFiles(
            List<ExtractionDeletionInstance> extractionDeletionInstances) throws IOException {

        CSVReaderBuilder<CsvMatrixRow<T>> builder;
        Map<String, CsvDataFrame<T>> resultMap = new TreeMap<String, CsvDataFrame<T>>();

        for (ExtractionDeletionInstance instance : extractionDeletionInstances) {
            final String workingDirectoryPath = instance.getWorkingDirectory();
            final int workingDirectoryLength = workingDirectoryPath.length();

            for (File file : instance.getFiles()) {

                if (file.exists() && file.canRead()) {
                    FileReader fileReaderForBuilder = new FileReader(file);
                    builder = new CSVReaderBuilder<CsvMatrixRow<T>>(fileReaderForBuilder);

                    builder.strategy(CSVStrategy.UK_DEFAULT);

                    //noinspection unchecked
                    builder.entryParser(DEFAULT_CSV_ENTRY_PARSER);
                    CSVReader<CsvMatrixRow<T>> reader = builder.build();

                    //noinspection unchecked
                    String key = surefireRelativePathPasting(
                            file.getCanonicalPath(), workingDirectoryPath, workingDirectoryLength);
                    //noinspection unchecked
                    T[] fileNameInfo = null;
                    String[] keyStrings = key.split("\\\\");
                    if (2 <= keyStrings.length) {
                        String[] strings = keyStrings[2].split("\\.");
                        if (1 <= strings.length) {
                            fileNameInfo = (T[]) strings[0].split("_");
                        }
                    }

                    //noinspection unchecked
                    CsvDataFrame<T> csvDataFrame = CsvDataFrame.getCsvDataFrame(reader, fileNameInfo);


                    if(!resultMap.containsKey(key)) {
                        resultMap.put(key, csvDataFrame);
                    } else {
                        throw new IllegalStateException(ErrorMessages.getDuplicatedFileIdentification(key));
                    }

                    fileReaderForBuilder.close();
                }
            }
        }

        // make signature sensitive
        return makeSignatureSensitiveMap(resultMap);
    }

    @SuppressWarnings("UnusedDeclaration")
    private static String surefireRelativePathPasting(String file,
                                                      String workingDirectoryPath,
                                                      final int workingDirectoryLength) {

        if (null == file || null == workingDirectoryPath || workingDirectoryLength < 0 ||
                workingDirectoryLength >= file.length() || !file.startsWith(workingDirectoryPath)) {

            // something is odd here
            throw new IllegalArgumentException(ErrorMessages.ODD_INSTANCE_STATE_IN_SUBROUTINE);
        }

        return file.substring(workingDirectoryLength);
    }


    @Override
    public Context getContext() {
        return this.context.getContext();
    }

    /**
     * Data coming in mapped by the data´s file name within the working directory and being mapped by their header.
     * That way we aggregate all information according to the content rather than by source. The final mapping on
     * outcome will be by technically header to all CSVs. This way we can aggregate the in-bucket-files later on.
     * @param fileMap The file-names mapping from the extraction process
     * @return The mapping by unique header
     */
    public Map<String, HeaderSignatureSensitiveBucket<T>> makeSignatureSensitiveMap(
            Map<String, CsvDataFrame<T>> fileMap) {

        Map<String, HeaderSignatureSensitiveBucket<T>> signatureSensitiveMap =
                new TreeMap<String, HeaderSignatureSensitiveBucket<T>>();

        String header;
        for (String path : fileMap.keySet()) {
            CsvDataFrame<T> csvDataFrame = fileMap.get(path);
            header = csvDataFrame.getHeaderSignature();

            if (signatureSensitiveMap.containsKey(header)) {
                //noinspection unchecked
                signatureSensitiveMap.get(header).addPair(path, csvDataFrame);
            } else {
                //noinspection unchecked
                signatureSensitiveMap.put(header, new HeaderSignatureSensitiveBucket<T>(path, csvDataFrame));
            }
        }

        return signatureSensitiveMap;
    }
}
