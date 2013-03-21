package de.novensa.ai.dataanalysis.datacleaner.aggregate;

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

    public CsvMatrix(List<CsvMatrixRow<T>> data) {

        List<CsvMatrixRow<T>> list = new ArrayList<CsvMatrixRow<T>>(data.size());
        for (CsvMatrixRow<T> rows : data) {
            list.add(rows);
        }

        this.rows = list;
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
