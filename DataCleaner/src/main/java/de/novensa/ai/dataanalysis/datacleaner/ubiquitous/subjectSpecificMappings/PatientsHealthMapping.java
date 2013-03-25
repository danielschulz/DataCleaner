package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.HealthState;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

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
        patientToDiagnosis.put("CHERRY",        HealthState.diagnosedPD);
        patientToDiagnosis.put("CROCUS",        HealthState.diagnosedPD);
        patientToDiagnosis.put("DAFODIL",       HealthState.healthy);
        patientToDiagnosis.put("DAISY",         HealthState.diagnosedPD);
        patientToDiagnosis.put("FLOX",          HealthState.diagnosedPD);
        patientToDiagnosis.put("IRIS",          HealthState.diagnosedPD);
        patientToDiagnosis.put("LILLY",         HealthState.healthy);
        patientToDiagnosis.put("MAPLE",         HealthState.diagnosedPD);
        patientToDiagnosis.put("ORANGE",        HealthState.healthy);
        patientToDiagnosis.put("ORCHID",        HealthState.diagnosedPD);
        patientToDiagnosis.put("PEONY",         HealthState.diagnosedPD);
        patientToDiagnosis.put("ROSE",          HealthState.healthy);
        patientToDiagnosis.put("SUNFLOWER",     HealthState.healthy);
        patientToDiagnosis.put("SWEETPEA",      HealthState.healthy);
        patientToDiagnosis.put("TESTCLIQ",      HealthState.predictedPD);
        patientToDiagnosis.put("VIOLET",        HealthState.diagnosedPD);
    }

    public final HealthState getHealthState(String patientId) {
        return patientToDiagnosis.get(patientId);
    }
}
