package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import com.googlecode.jcsv.reader.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves data in matrices.
 *
 * @author Daniel Schulz
 *
 * @param <T> The type of all datas in cells
 */
public class CsvMatrix<T> {

    private final List<CsvMatrixRow<T>> rows;

    public CsvMatrix(CSVReader reader) throws IOException {

        /*
        > throws ClassCastExceptions for reasons unknown: signature have to be
        public CsvMatrix(List<CsvMatrixRow<T>> data)
        then.

        List<CsvMatrixRow<T>> list = new ArrayList<CsvMatrixRow<T>>(data.size());
        for (CsvMatrixRow<T> rows : data) {
            list.add(rows);
        }
        */

        List data = reader.readAll();

        List<CsvMatrixRow<T>> list = new ArrayList<CsvMatrixRow<T>>(data.size());

        for (T[] row : (List<T[]>) data) {
            list.add(new CsvMatrixRow<T>(row));
        }

        this.rows = list;
    }

    public CsvMatrix(List<List<T>> data) {

        if (null != data && data.size() > 0) {
            if (data.get(0) instanceof CsvMatrixRow) {
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
