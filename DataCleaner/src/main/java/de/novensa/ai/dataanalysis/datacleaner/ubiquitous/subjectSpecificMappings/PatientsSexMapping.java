package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.HealthState;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.util.TreeMap;

/**
 * The mapping patient to it´s sex.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class PatientsSexMapping extends SynonymToPatientIdentificationMapping {

    private final TreeMap<String, String> patientsSex;

    @SuppressWarnings("FieldCanBeLocal")
    private final String FEMALE = "f";

    @SuppressWarnings("FieldCanBeLocal")
    private final String MALE = "m";

    private final String UNKNOWN_SEX = Constants.UNKNOWN_VALUE_IN_RESULT;

    public PatientsSexMapping() {

        this.patientsSex = new TreeMap<String, String>();

        patientsSex.put("APPLE",         UNKNOWN_SEX);
        patientsSex.put("CHERRY",        UNKNOWN_SEX);
        patientsSex.put("CROCUS",        UNKNOWN_SEX);
        patientsSex.put("DAFODIL",       UNKNOWN_SEX);
        patientsSex.put("DAISY",         UNKNOWN_SEX);
        patientsSex.put("FLOX",          UNKNOWN_SEX);
        patientsSex.put("IRIS",          UNKNOWN_SEX);
        patientsSex.put("LILLY",         UNKNOWN_SEX);
        patientsSex.put("MAPLE",         UNKNOWN_SEX);
        patientsSex.put("ORANGE",        UNKNOWN_SEX);
        patientsSex.put("ORCHID",        UNKNOWN_SEX);
        patientsSex.put("PEONY",         UNKNOWN_SEX);
        patientsSex.put("ROSE",          UNKNOWN_SEX);
        patientsSex.put("SUNFLOWER",     UNKNOWN_SEX);
        patientsSex.put("SWEETPEA",      UNKNOWN_SEX);
        patientsSex.put("TESTCLIQ",      UNKNOWN_SEX);
        patientsSex.put("VIOLET",        UNKNOWN_SEX);
    }


    public final String getPatientsSex(String patientId) {
        return patientsSex.get(patientId);
    }
}
