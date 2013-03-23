package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings.Mappings;

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
    private static final int MEDICAL_INFO_ITEM_COUNT = 3;
    private static final Mappings MAPPINGS = new Mappings();


    public CsvMatrixRow(final T[] cells) {

        List<T> parsed = new ArrayList<T>(cells.length);
        Collections.addAll(parsed, cells);

        this.cells = parsed;
    }

    public CsvMatrixRow(final CsvMatrixRow<T> oldRow, T[] fileNameInfo) {

        List<T> parsed = new ArrayList<T>(oldRow.getColumnSize() * Constants.ESTIMATED_MEAN_CHARACTERS_PER_DATA_CELL);
        parsed.addAll(oldRow.getCells());
        List<T> medicalInfo = getMedicalInfo(fileNameInfo);
        parsed.addAll(medicalInfo);

        this.cells = parsed;
    }

    @SuppressWarnings("unchecked")
    private List<T> getMedicalInfo(T[] medicalSubjectsPseudonym) {
        List<T> medicalInfo = new ArrayList<T>(MEDICAL_INFO_ITEM_COUNT + medicalSubjectsPseudonym.length - 1);

        T patientId = medicalSubjectsPseudonym[2];
        if (MAPPINGS.hasPatientIdentificatorIgnoringSynonyms((String) patientId)) {
            patientId = ((T) MAPPINGS.getPatientIdentificatorIgnoringSynonyms((String) patientId));
        }

        medicalInfo.add(patientId);
        medicalInfo.add((T) MAPPINGS.getHealthState((String) patientId));
        medicalInfo.add((T) MAPPINGS.getPatientsSex((String) patientId));

        medicalInfo.add(medicalSubjectsPseudonym[0]);
        medicalInfo.add(medicalSubjectsPseudonym[1]);
        // leave out pseudonym -- this was inserted above because duplicate id where mapped back to unique entities
        medicalInfo.add(medicalSubjectsPseudonym[3]);
        medicalInfo.add(medicalSubjectsPseudonym[4]);

        return medicalInfo;
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
