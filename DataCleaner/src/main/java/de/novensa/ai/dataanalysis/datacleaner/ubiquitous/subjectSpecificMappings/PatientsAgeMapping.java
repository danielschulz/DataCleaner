package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.util.TreeMap;

/**
 * The mapping patient to it´s sex.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class PatientsAgeMapping extends SynonymToPatientIdentificationMapping {

    private final TreeMap<String, String> patientsAge;
    private final TreeMap<String, String> patientsAgeDiagnosisPut;

    @SuppressWarnings("FieldCanBeLocal")
    private final String YOUNG = "1";

    @SuppressWarnings("FieldCanBeLocal")
    private final String MATURE = "2";
    
    @SuppressWarnings("FieldCanBeLocal")
    private final String ELDERLY = "3";

    private final String UNKNOWN_AGE = Constants.UNKNOWN_VALUE_IN_RESULT;

    public PatientsAgeMapping() {

        // patient´s current age
        this.patientsAge = new TreeMap<String, String>();

        patientsAge.put("APPLE",        UNKNOWN_AGE);
        patientsAge.put("CHERRY",       UNKNOWN_AGE);
        patientsAge.put("CROCUS",       UNKNOWN_AGE);
        patientsAge.put("DAFODIL",      UNKNOWN_AGE);
        patientsAge.put("DAISY",        UNKNOWN_AGE);
        patientsAge.put("FLOX",         UNKNOWN_AGE);
        patientsAge.put("IRIS",         UNKNOWN_AGE);
        patientsAge.put("LILLY",        UNKNOWN_AGE);
        patientsAge.put("MAPLE",        UNKNOWN_AGE);
        patientsAge.put("ORANGE",       UNKNOWN_AGE);
        patientsAge.put("ORCHID",       UNKNOWN_AGE);
        patientsAge.put("PEONY",        UNKNOWN_AGE);
        patientsAge.put("ROSE",         UNKNOWN_AGE);
        patientsAge.put("SUNFLOWER",    UNKNOWN_AGE);
        patientsAge.put("SWEETPEA",     UNKNOWN_AGE);
        patientsAge.put("TESTCLIQ",     UNKNOWN_AGE);
        patientsAge.put("VIOLET",       UNKNOWN_AGE);


        // diagnosis put
        this.patientsAgeDiagnosisPut = new TreeMap<String, String>();

        patientsAgeDiagnosisPut.put("APPLE",        UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("CHERRY",       UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("CROCUS",       UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("DAFODIL",      UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("DAISY",        UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("FLOX",         UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("IRIS",         UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("LILLY",        UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("MAPLE",        UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("ORANGE",       UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("ORCHID",       UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("PEONY",        UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("ROSE",         UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("SUNFLOWER",    UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("SWEETPEA",     UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("TESTCLIQ",     UNKNOWN_AGE);
        patientsAgeDiagnosisPut.put("VIOLET",       UNKNOWN_AGE);
    }


    public final String getPatientsAge(String patientId) {
        final String res = patientsAge.get(patientId);
        return null != res ? res : UNKNOWN_AGE;
    }


    public final String getPatientsAgeDiagnosisPut(String patientId) {
        final String res = patientsAgeDiagnosisPut.get(patientId);
        return null != res ? res : UNKNOWN_AGE;
    }
}
