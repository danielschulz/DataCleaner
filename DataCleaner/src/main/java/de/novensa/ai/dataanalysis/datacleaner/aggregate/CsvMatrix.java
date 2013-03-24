package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import com.googlecode.jcsv.reader.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves data in matrices.
 *
 * @author Daniel Schulz
 * @author Juergen Krey
 *
 * @param <T> The type of all data in cells
 */
public class CsvMatrix<T> {

    private final List<CsvMatrixRow<T>> rows;

    public CsvMatrix(final CSVReader<CsvMatrixRow<T>> reader, final T[] fileNameInfo) throws IOException {

        List<CsvMatrixRow<T>> data = reader.readAll();
        List<CsvMatrixRow<T>> list = new ArrayList<CsvMatrixRow<T>>(data.size());

        for (CsvMatrixRow<T> row : data) {
            list.add(new CsvMatrixRow<T>(row, fileNameInfo));
        }

        this.rows = list;
    }

    public CsvMatrix(final List<List<T>> data) {

        if (null != data && data.size() > 0) {
            if (data.get(0) instanceof CsvMatrixRow) {
                //noinspection unchecked
                this.rows = (List) data;
            } else {
                List<CsvMatrixRow<T>> list = new ArrayList<CsvMatrixRow<T>>(data.size());
                for (List<T> row : data) {
                    list.add(new CsvMatrixRow<T>(row));
                }

                this.rows = list;
            }
        } else {
            this.rows = new ArrayList<CsvMatrixRow<T>>(1);
        }
    }

    public List<CsvMatrixRow<T>> getRows() {
        return rows;
    }

    public int getRowSize() {
        return this.rows.size();
    }

    public CsvMatrixRow<T> getRow(final int row) {
        return this.rows.get(row);
    }
}
