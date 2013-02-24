package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import com.googlecode.jcsv.reader.CSVReader;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;

import java.io.IOException;
import java.util.List;

/**
 * Represents the data frame for a CSV file. A data frame is what it used to be in R (programming language). You can
 * think of a data frame as a table with different typed columns.
 */
public class CsvDataFrame {

    private final List<String> header;
    private final List<List<String>> data;


    public CsvDataFrame(List<String> header, List<List<String>> data) {
        this.header = header;
        this.data = data;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getHeader(int i) {

        if (0 > i && i >= this.header.size()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_COLUMN);
        }

        return this.header.get(i);
    }

    /**
     * Get the data out of the CSV data frame. The row correspond to instances; the columns represent features.
     * @param row The item or instance with various features
     * @param column The specific column, date, or feature from that given item/instance
     * @return The String from that cell
     */
    @SuppressWarnings("UnusedDeclaration")
    public String getDate(int row, int column) {

        if (0 > row && row >= this.data.size()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_ROW);
        }

        if (0 > column && column >= this.data.get(row).size()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_COLUMN);
        }

        return this.data.get(row).get(column);
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<String> getHeader() {
        return header;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<List<String>> getData() {
        return data;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<String> getDataItem(int row) {

        if (0 > row && row >= this.data.size()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_ROW);
        }

        return this.data.get(row);
    }

    public static CsvDataFrame getCsvDataFrame(CSVReader<List<String>> reader) throws IOException {

        List<String> header = reader.readHeader();
        List<List<String>> data = reader.readAll();

        return new CsvDataFrame(header, data);
    }
}
