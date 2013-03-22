package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import com.googlecode.jcsv.reader.CSVReader;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;

import java.io.IOException;
import java.util.List;

/**
 * Represents the data frame for a CSV file. A data frame is what it used to be in R (programming language). You can
 * think of a data frame as a table with different typed columns.
 *
 * @author Daniel Schulz
 * @author Juergen Krey
 *
 * @param <T> The type of data in all cells
 */
@SuppressWarnings("UnusedDeclaration")
public class CsvDataFrame<T> {

    private final List<String> header;
    private final CsvMatrix<T> data;

    private String patient;
    private boolean femalePatient;
    private HealthState healthState;
    private float healthStateSafeness = -1f;


    public CsvDataFrame(List<String> header, CsvMatrix<T> data) {
        this.header = header;
        this.data = data;
    }

    /**
     * Returns the technical header id for this data set. This is used to aggregate all data by header signature.
     * @return The string aggregated header using semicolon as delimiter character.
     */
    public String getHeaderSignature() {
        StringBuilder signature = new StringBuilder();
        for (String headerItem : this.header) {
            signature.append(headerItem).append(Constants.HEADER_SIGNATURES_DELIMITER);
        }

        return signature.substring(0, signature.length() - Constants.HEADER_SIGNATURES_DELIMITER.length());
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getHeader(final int i) {

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
    public T getDate(final int row, final int column) {

        if (0 > row && row >= this.data.getRowSize()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_ROW);
        }

        if (0 > column && column >= this.data.getRow(row).getColumnSize()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_COLUMN);
        }

        return this.data.getRow(row).getCell(column);
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<String> getHeader() {
        return header;
    }

    @SuppressWarnings("UnusedDeclaration")
    public CsvMatrix<T> getData() {
        return data;
    }

    @SuppressWarnings("UnusedDeclaration")
    public CsvMatrixRow<T> getDataItem(int row) {

        if (0 > row && row >= this.data.getRowSize()) {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_INDEX_OUT_OF_BOUNDS_ON_LOAD_ROW);
        }

        return this.data.getRow(row);
    }

    public static <T> CsvDataFrame<T> getCsvDataFrame(final CSVReader<CsvMatrixRow<T>> reader) throws IOException {

        List<String> header = reader.readHeader();
        CsvMatrix<T> data = new CsvMatrix<T>(reader);

        return new CsvDataFrame<T>(header, data);
    }


    public boolean isPatientInitialized() {
        return null != this.patient;
    }
    public String getPatient() {
        return patient;
    }

    public void setPatient(final String patient) throws IllegalAccessException {
        if (null != this.patient) {
            throw new IllegalAccessException(ErrorMessages.INITIALIZED_FIELD_MUST_STAY_UNCHANGED);
        }
        if (null == patient) {
            throw new IllegalArgumentException(ErrorMessages.NULL_INITIALIZATION_NOT_ALLOWED_HERE);
        }
        this.patient = patient;
    }

    public void setPatientsSex(final boolean female) throws IllegalAccessException {
        this.femalePatient = female;
    }

    public boolean getPatientsSex() {
        return this.femalePatient;
    }

    public boolean isHealthStateInitialized() {
        return null != this.healthState;
    }

    public HealthState getHealthState() {
        return healthState;
    }

    public void setHealthState(final HealthState healthState) throws IllegalAccessException {
        if (null != this.patient) {
            throw new IllegalAccessException(ErrorMessages.INITIALIZED_FIELD_MUST_STAY_UNCHANGED);
        }
        //noinspection ConstantConditions
        if (null == patient) {
            throw new IllegalArgumentException(ErrorMessages.NULL_INITIALIZATION_NOT_ALLOWED_HERE);
        }
        this.healthState = healthState;
    }

    public boolean doesHealthStateSafenessMakesSense() {
        return 0 >= this.healthStateSafeness && 1 <= this.healthStateSafeness;
    }

    public float getHealthStateSafeness() {
        return healthStateSafeness;
    }

    public void setHealthStateSafeness(final float healthStateSafeness) {
        this.healthStateSafeness = healthStateSafeness;
    }

    /**
     * Call this to find out whether there is still information missing on the patient´s diagnosis or if everything is
     * still initialized.
     * @return True is patient´s name, health state and health state´s safeness are proven being fine.
     */
    public boolean arePatientAndHealthStateInformationComplete() {
        return isPatientInitialized() && isHealthStateInitialized() && doesHealthStateSafenessMakesSense();
    }
}
