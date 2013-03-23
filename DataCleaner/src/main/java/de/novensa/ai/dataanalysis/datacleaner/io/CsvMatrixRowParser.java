package de.novensa.ai.dataanalysis.datacleaner.io;

import com.googlecode.jcsv.reader.CSVEntryParser;
import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvMatrixRow;

/**
 * Parses the CSV on lowest application (non-framework, non-third-party-libraries´ level).
 *
 * @author Daniel Schulz
 * @author Juergen Krey
 *
 * @param <T> The cell data type within every CsvMatrixRow (CsvMatrixRow<T>)
 */
public class CsvMatrixRowParser<T> implements CSVEntryParser {
    @Override
    public CsvMatrixRow<T> parseEntry(String... data) {
        //noinspection unchecked
        return new CsvMatrixRow<T>((T[]) data);
    }
}
