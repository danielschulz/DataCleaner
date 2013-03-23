package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

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

    public PatientsSexMapping() {

        this.patientsSex = new TreeMap<String, String>();

        patientsSex.put("APPLE",        MALE);
        patientsSex.put("Albert",       MALE);
        patientsSex.put("Eva",          FEMALE);
        patientsSex.put("Gabriella",    FEMALE);
        patientsSex.put("Marilyn",      FEMALE);
        patientsSex.put("Peter",        MALE);
        patientsSex.put("Steven",       MALE);
    }


    public final String getPatientsSex(String patientId) {
        return patientsSex.get(patientId);
    }
}
