package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Saves the data rows in single cells.
 *
 * @param <T> The type for all cell data
 * @author Daniel Schulz
 * @author Juergen Krey
 */
public class CsvMatrixRow<T> {

    private final List<T> cells;

    public CsvMatrixRow(final T[] cells) {

        List<T> parsed = new ArrayList<T>(cells.length);
        Collections.addAll(parsed, cells);

        this.cells = parsed;
    }

    public CsvMatrixRow(final CsvMatrixRow<T> oldRow, T[] fileNameInfo) {

        List<T> parsed = new ArrayList<T>(oldRow.getColumnSize() * Constants.ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL);
        parsed.addAll(oldRow.getCells());
        Collections.addAll(parsed, fileNameInfo);

        this.cells = parsed;
    }

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
