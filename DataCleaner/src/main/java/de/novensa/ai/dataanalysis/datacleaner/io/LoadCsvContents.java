package de.novensa.ai.dataanalysis.datacleaner.io;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Context;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Get the contents of all CSV files.
 *
 * @author Daniel Schulz
 */
public class LoadCsvContents extends Context {

    private final static DefaultCSVEntryParser DEFAULT_CSV_ENTRY_PARSER = new DefaultCSVEntryParser();
    private final Context context;

    public LoadCsvContents(Context context) {
        this.context = context;
    }

    /**
     * Read files from your ExtractionDeletionInstance. The result will map the file's location to it's content.
     * @param extractionDeletionInstance The source to find the files
     * @return A Map from the file location to it's content
     */
    public Map<String, Pair<String, CsvDataFrame>> exploreJustExtractedFiles(
            ExtractionDeletionInstance extractionDeletionInstance) throws IOException {

        final String workingDirectoryPath = extractionDeletionInstance.getWorkingDirectory();
        final int workingDirectoryLength = workingDirectoryPath.length();

        CSVReaderBuilder builder;
        Map<String, CsvDataFrame> resultMap = new HashMap<String, CsvDataFrame>();
        for (File file : extractionDeletionInstance.getFiles()) {

            if (file.exists() && file.canRead()) {
                builder = new CSVReaderBuilder(new FileReader(file));

                builder.strategy(CSVStrategy.UK_DEFAULT);

                //noinspection unchecked
                builder.entryParser(DEFAULT_CSV_ENTRY_PARSER);
                CSVReader reader = builder.build();

                //noinspection unchecked
                resultMap.put(surefireRelativePathPasting(file.getPath(), workingDirectoryPath, workingDirectoryLength),
                        CsvDataFrame.getCsvDataFrame(reader));
            }
        }

        return makeSignatureSensitiveMap(resultMap);
    }

    private static String surefireRelativePathPasting(String file,
                                                      String workingDirectoryPath,
                                                      int workingDirectoryLength) {

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
    public Map<String, Pair<String, CsvDataFrame>> makeSignatureSensitiveMap(Map<String, CsvDataFrame> fileMap) {
        Map<String, Pair<String, CsvDataFrame>> signatureSensitiveMap = new HashMap<String, Pair<String, CsvDataFrame>>();

        String header;
        for (String path : fileMap.keySet()) {
            CsvDataFrame csvDataFrame = fileMap.get(path);
            header = csvDataFrame.getHeaderSignature();
            signatureSensitiveMap.put(header, new Pair<String, CsvDataFrame>(path, csvDataFrame));
        }

        return signatureSensitiveMap;
    }
}
