package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

import java.util.TreeMap;

/**
 * The mapping patient to it´s sex.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class PatientsSexMapping extends PatientsAgeMapping {

    private final TreeMap<String, String> patientsSex;
    private final TreeMap<String, String> patientsAmountMedicalTreatments;

    private static final String FEMALE = "f";
    private static final String MALE = "m";

    private static final String[] TREATMENTS = new String[] {"slightly", "few", "bunch", "massive"};

    public PatientsSexMapping() {

        // patient´s sex
        this.patientsSex = new TreeMap<String, String>();

        patientsSex.put("APPLE",         MALE);
        patientsSex.put("CHERRY",        FEMALE);
        patientsSex.put("CROCUS",        MALE);
        patientsSex.put("DAFODIL",       MALE);
        patientsSex.put("DAISY",         MALE);
        patientsSex.put("FLOX",          MALE);
        patientsSex.put("IRIS",          MALE);
        patientsSex.put("LILLY",         FEMALE);
        patientsSex.put("MAPLE",         MALE);
        patientsSex.put("ORANGE",        MALE);
        patientsSex.put("ORCHID",        MALE);
        patientsSex.put("PEONY",         MALE);
        patientsSex.put("ROSE",          MALE);
        patientsSex.put("SUNFLOWER",     MALE);
        patientsSex.put("SWEETPEA",      FEMALE);
        patientsSex.put("TESTCLIQ",      Constants.UNKNOWN_VALUE_IN_RESULT);
        patientsSex.put("VIOLET",        FEMALE);
        
        
        // patient´s amount of medical treatments
        this.patientsAmountMedicalTreatments = new TreeMap<String, String>();

        patientsAmountMedicalTreatments.put("APPLE",         TREATMENTS[3]);
        patientsAmountMedicalTreatments.put("CHERRY",        TREATMENTS[0]);
        patientsAmountMedicalTreatments.put("CROCUS",        TREATMENTS[1]);
        patientsAmountMedicalTreatments.put("DAFODIL",       TREATMENTS[0]);
        patientsAmountMedicalTreatments.put("DAISY",         TREATMENTS[2]);
        patientsAmountMedicalTreatments.put("FLOX",          TREATMENTS[3]);
        patientsAmountMedicalTreatments.put("IRIS",          TREATMENTS[0]);
        patientsAmountMedicalTreatments.put("LILLY",         TREATMENTS[3]);
        patientsAmountMedicalTreatments.put("MAPLE",         TREATMENTS[1]);
        patientsAmountMedicalTreatments.put("ORANGE",        TREATMENTS[2]);
        patientsAmountMedicalTreatments.put("ORCHID",        TREATMENTS[1]);
        patientsAmountMedicalTreatments.put("PEONY",         TREATMENTS[1]);
        patientsAmountMedicalTreatments.put("ROSE",          TREATMENTS[1]);
        patientsAmountMedicalTreatments.put("SUNFLOWER",     TREATMENTS[1]);
        patientsAmountMedicalTreatments.put("SWEETPEA",      TREATMENTS[2]);
        patientsAmountMedicalTreatments.put("TESTCLIQ",      Constants.UNKNOWN_VALUE_IN_RESULT);
        patientsAmountMedicalTreatments.put("VIOLET",        TREATMENTS[1]);
    }


    public final String getPatientsSex(String patientId) {
        final String pSex = patientsSex.get(patientId);
        return null != pSex ? pSex : Constants.UNKNOWN_VALUE_IN_RESULT;
    }


    public final String getPatientsAmountMedicalTreatments(String patientId) {
        final String medics = patientsAmountMedicalTreatments.get(patientId);
        return null != medics ? medics : Constants.UNKNOWN_VALUE_IN_RESULT;
    }
}
