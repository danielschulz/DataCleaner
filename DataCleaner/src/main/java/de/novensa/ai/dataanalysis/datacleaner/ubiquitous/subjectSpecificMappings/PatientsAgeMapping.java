package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.util.TreeMap;

import static de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants.UNKNOWN_VALUE_IN_RESULT;

/**
 * The mapping patient to it´s sex.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class PatientsAgeMapping extends SynonymToPatientIdentificationMapping {

    private final TreeMap<String, String> patientsAge;
    private final TreeMap<String, String> patientsAgeDiagnosisPut;


    public PatientsAgeMapping() {

        // patient´s current age
        this.patientsAge = new TreeMap<String, String>();

        patientsAge.put("APPLE",        "77");
        patientsAge.put("CHERRY",       "55");
        patientsAge.put("CROCUS",       "46");
        patientsAge.put("DAFODIL",      "42");
        patientsAge.put("DAISY",        "54");
        patientsAge.put("FLOX",         "57");
        patientsAge.put("IRIS",         "65");
        patientsAge.put("LILLY",        "53");
        patientsAge.put("MAPLE",        "55");
        patientsAge.put("ORANGE",       "57");
        patientsAge.put("ORCHID",       "69");
        patientsAge.put("PEONY",        "80");
        patientsAge.put("ROSE",         "55");
        patientsAge.put("SUNFLOWER",    "67");
        patientsAge.put("SWEETPEA",     "77");
        patientsAge.put("TESTCLIQ",     UNKNOWN_VALUE_IN_RESULT);
        patientsAge.put("VIOLET",       "55");


        // diagnosis put
        this.patientsAgeDiagnosisPut = new TreeMap<String, String>();

        patientsAgeDiagnosisPut.put("APPLE",        UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("CHERRY",       "51");
        patientsAgeDiagnosisPut.put("CROCUS",       "41");
        patientsAgeDiagnosisPut.put("DAFODIL",      UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("DAISY",        "52");
        patientsAgeDiagnosisPut.put("FLOX",         "47");
        patientsAgeDiagnosisPut.put("IRIS",         "45");
        patientsAgeDiagnosisPut.put("LILLY",        UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("MAPLE",        "46");
        patientsAgeDiagnosisPut.put("ORANGE",       UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("ORCHID",       "65");
        patientsAgeDiagnosisPut.put("PEONY",        "67");
        patientsAgeDiagnosisPut.put("ROSE",         UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("SUNFLOWER",    UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("SWEETPEA",     UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("TESTCLIQ",     UNKNOWN_VALUE_IN_RESULT);
        patientsAgeDiagnosisPut.put("VIOLET",       UNKNOWN_VALUE_IN_RESULT);
    }


    public final String getPatientsAge(String patientId) {
        final String res = patientsAge.get(patientId);
        return null != res ? res : UNKNOWN_VALUE_IN_RESULT;
    }


    public final String getPatientsAgeDiagnosisPut(String patientId) {
        final String res = patientsAgeDiagnosisPut.get(patientId);
        return null != res ? res : UNKNOWN_VALUE_IN_RESULT;
    }
}
