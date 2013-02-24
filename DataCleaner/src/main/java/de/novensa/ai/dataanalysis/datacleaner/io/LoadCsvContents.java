package de.novensa.ai.dataanalysis.datacleaner.io;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;

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
public class LoadCsvContents {

    private final static DefaultCSVEntryParser DEFAULT_CSV_ENTRY_PARSER = new DefaultCSVEntryParser();

    /**
     * Read files from your ExtractionDeletionInstance. The result will map the file's location to it's content.
     * @param extractionDeletionInstance The source to find the files
     * @return A Map from the file location to it's content
     */
    public static <R extends String> Map<File, CsvDataFrame> exploreFilesInFolders(
            ExtractionDeletionInstance extractionDeletionInstance) throws IOException {

        CSVReaderBuilder builder;
        Map<File, CsvDataFrame> resultMap = new HashMap<File, CsvDataFrame>();
        for (File file : extractionDeletionInstance.getFiles()) {

            if (file.exists() && file.canRead()) {
                builder = new CSVReaderBuilder(new FileReader(file));

                builder.strategy(CSVStrategy.UK_DEFAULT);
                builder.entryParser(DEFAULT_CSV_ENTRY_PARSER);
                CSVReader reader = builder.build();

                resultMap.put(file, CsvDataFrame.getCsvDataFrame(reader));
            }
        }

        return resultMap;
    }


}
