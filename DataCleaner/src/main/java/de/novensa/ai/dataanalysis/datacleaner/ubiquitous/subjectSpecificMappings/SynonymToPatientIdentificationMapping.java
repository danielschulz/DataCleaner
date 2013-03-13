package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.HealthState;

import java.util.Map;
import java.util.TreeMap;

/**
 * The subject´s mappings are placed here. This is the place for patients´ synonyms to identification mapping.
 *
 * @author Daniel Schulz
 */
public abstract class SynonymToPatientIdentificationMapping {

    private final TreeMap<String, String> specialSynonyms;

    protected SynonymToPatientIdentificationMapping() {
        this.specialSynonyms = new TreeMap<String, String>();
        specialSynonyms.put("Alberto", "Albert");
    }

    protected final String getPatientIdentificatorIgnoringSynonyms(String possiblyTheSynonym) {
        return this.specialSynonyms.get(possiblyTheSynonym);
    }
}
