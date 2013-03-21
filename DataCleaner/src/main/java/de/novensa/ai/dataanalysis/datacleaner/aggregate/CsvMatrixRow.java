package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import java.util.List;

/**
 * Saves the data rows in single cells.
 *
 * @author Daniel Schulz
 *
 * @param <T> The type for all cell data
 */
public class CsvMatrixRow<T> {

    private final List<T> cells;

    public CsvMatrixRow(final List<T> cells) {
        this.cells = cells;
    }

    public List<T> getCells() {
        return cells;
    }

    public int getColumnSize() {
        return this.cells.size();
    }

    public T getCell(final int cell) {
        return this.cells.get(cell);
    }
}
