package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.HealthState;

import java.util.TreeMap;

/**
 * The subject´s mappings are placed here. This is the place for the mapping patient to her or his diagnosis.
 *
 * @author Daniel Schulz
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class PatientsHealthMapping extends PatientsSexMapping {

    private final TreeMap<String, HealthState> patientToDiagnosis;

    public PatientsHealthMapping() {

        this.patientToDiagnosis = new TreeMap<String, HealthState>();

        patientToDiagnosis.put("APPLE",         HealthState.healthy);
        patientToDiagnosis.put("CHERRY",        HealthState.healthy);
        patientToDiagnosis.put("CROCUS",        HealthState.healthy);
        patientToDiagnosis.put("DAFODIL",       HealthState.healthy);
        patientToDiagnosis.put("DAISY",         HealthState.healthy);
        patientToDiagnosis.put("FLOX",          HealthState.healthy);
        patientToDiagnosis.put("IRIS",          HealthState.healthy);
        patientToDiagnosis.put("LILLY",         HealthState.healthy);
        patientToDiagnosis.put("MAPLE",         HealthState.healthy);
        patientToDiagnosis.put("ORANGE",        HealthState.healthy);
        patientToDiagnosis.put("ORCHID",        HealthState.healthy);
        patientToDiagnosis.put("PEONY",         HealthState.healthy);
        patientToDiagnosis.put("ROSE",          HealthState.healthy);
        patientToDiagnosis.put("SUNFLOWER",     HealthState.healthy);
        patientToDiagnosis.put("SWEETPEA",      HealthState.healthy);
        patientToDiagnosis.put("TESTCLIQ",      HealthState.healthy);
        patientToDiagnosis.put("VIOLET",        HealthState.healthy);
    }

    public final HealthState getHealthState(String patientId) {
        return patientToDiagnosis.get(patientId);
    }
}
