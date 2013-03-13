package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.HealthState;

import java.util.TreeMap;

/**
 * The subject´s mappings are placed here. This is the place for the mapping patient to her or his diagnosis.
 *
 * @author Daniel Schulz
 */
public abstract class PatientsHealthMapping {

    private final TreeMap<String, HealthState> patientToDiagnosis;

    protected PatientsHealthMapping() {

        this.patientToDiagnosis = new TreeMap<String, HealthState>();

        patientToDiagnosis.put("Albert",        HealthState.healthy);
        patientToDiagnosis.put("Eva",           HealthState.healthy);
        patientToDiagnosis.put("Gabriella",     HealthState.healthy);
        patientToDiagnosis.put("Marilyn",       HealthState.diagnosedPD);
        patientToDiagnosis.put("Peter",         HealthState.healthy);
        patientToDiagnosis.put("Steven",        HealthState.diagnosedPD);
    }

    protected final HealthState getHealthState(String patientId) {
        return patientToDiagnosis.get(patientId);
    }
}
