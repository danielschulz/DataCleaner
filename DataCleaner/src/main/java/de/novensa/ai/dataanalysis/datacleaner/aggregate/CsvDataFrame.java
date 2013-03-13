package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import com.googlecode.jcsv.reader.CSVReader;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.PrivateConstants;

import java.io.IOException;
import java.util.List;

/**
 * Represents the data frame for a CSV file. A data frame is what it used to be in R (programming language). You can
 * think of a data frame as a table with different typed columns.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public class CsvDataFrame {

    private final List<String> header;
    private final List<List<String>> data;

    private String patient;
    private HealthState healthState;
    private float healthStateSafeness = -1f;


    public CsvDataFrame(List<String> header, List<List<String>> data) {
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
            signature.append(headerItem).append(PrivateConstants.HEADER_SIGNATURES_DELIMITER);
        }

        return signature.substring(0, signature.length() - PrivateConstants.HEADER_SIGNATURES_DELIMITER.length());
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


    public boolean isPatientInitialized() {
        return null != this.patient;
    }
    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) throws IllegalAccessException {
        if (null != this.patient) {
            throw new IllegalAccessException(ErrorMessages.INITIALIZED_FIELD_MUST_STAY_UNCHANGED);
        }
        if (null == patient) {
            throw new IllegalArgumentException(ErrorMessages.NULL_INITIALIZATION_NOT_ALLOWED_HERE);
        }
        this.patient = patient;
    }

    public boolean isHealthStateInitialized() {
        return null != this.healthState;
    }

    public HealthState getHealthState() {
        return healthState;
    }

    public void setHealthState(HealthState healthState) throws IllegalAccessException {
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

    public void setHealthStateSafeness(float healthStateSafeness) {
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
